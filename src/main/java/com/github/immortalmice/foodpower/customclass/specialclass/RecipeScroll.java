package com.github.immortalmice.foodpower.customclass.specialclass;

import java.util.List;
import java.util.Random;
import java.lang.Math;
import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.customclass.container.classes.recipescroll.RecipeScrollContainer;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;

public class RecipeScroll extends ItemBase{
	public RecipeScroll(){
		super("recipe_scroll", new Item.Properties().maxStackSize(1));
	}

	/* create a ItemStack and set NBTTags with given information */
	public static ItemStack create(CookingPattern patternIn, List<ItemStack> listIn, String nameIn){
		ItemStack result = new ItemStack(Items.RECIPE_SCROLL);

		CompoundNBT nbt = new CompoundNBT();

		nbt.putString("pattern", patternIn.getName());
		nbt.putString("displayName", nameIn);

		ListNBT tagList = new ListNBT();
		for(int i = 0; i <= listIn.size()-1; i ++){
			CompoundNBT element = new CompoundNBT();
			element.putString("name", ((Ingredient)listIn.get(i).getItem()).getFPName());
			element.putInt("level", listIn.get(i).getCount());
			tagList.add(element);
		}
		nbt.put("ingredients", tagList);

		result.setTag(nbt);
		return result;
	}

    /* Set rarity and calculate ingredient amount in need */
    public static void initStack(ItemStack stack){
        Random rand = new Random();
        CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();

        if(nbt.contains("rarity")) return;

        int rarity = rand.nextInt(4);
        nbt.putInt("rarity", rarity);

        /* Give a random float 0.9~1.1 to randomize ingredient amount in need */
        nbt.putDouble("random", rand.nextFloat() * 0.2 + 0.9);
        /* How many meals player want to cook? */
        nbt.putInt("output_amount", 1);

        if(nbt.contains("ingredients")){
            ListNBT list = (ListNBT)nbt.get("ingredients");
            for(int i = 0; i <= list.size()-1; i ++){
                CompoundNBT element = (CompoundNBT)list.get(i);
                int amount = RecipeScroll.calcuAmount(element.getString("name"), nbt.getInt("output_amount"), element.getInt("level"), nbt.getDouble("random"));
                element.putInt("amount", amount);
            }
        }
    }

    /* Calculate amount with single ingredient, Lv.2 amount * 2 and Lv.3 amount * 4 */
    private static int calcuAmount(String ingredientName, int outputAmount, int level, Double rand){
        Ingredient ingrdient = Ingredients.getIngredientByName(ingredientName);
        return (int) Math.ceil(ingrdient.getBaseAmount() * outputAmount * Math.pow(2, level - 1) * rand);
    }

    private static void calcuAllAmount(ItemStack scroll){
        RecipeScroll.calcuAllAmount(scroll.hasTag() ? scroll.getTag() : new CompoundNBT());
    }

    /* Calculate amount with each ingredient */
    public static void calcuAllAmount(CompoundNBT nbt){
        int ouputAmount = nbt.contains("output_amount") ? nbt.getInt("output_amount") : 0;

        if(nbt.contains("ingredients")){
            ListNBT list = (ListNBT)nbt.get("ingredients");
            for(int i = 0; i <= list.size()-1; i ++){
                CompoundNBT element = (CompoundNBT)list.get(i);
                int amount = RecipeScroll.calcuAmount(element.getString("name"), ouputAmount, element.getInt("level"), nbt.getDouble("random"));
                element.putInt("amount", amount);
            }
        }
    }

    /* dif may be negative */
    public static int addOutputAmount(ItemStack scroll, int dif){
        CompoundNBT nbt = scroll.hasTag() ? scroll.getTag() : new CompoundNBT();
        int amount = (nbt.contains("output_amount") ? nbt.getInt("output_amount") : 0) + dif;
        if(amount <= 0) amount += 64;
        if(amount > 64) amount -= 64;

        nbt.putInt("output_amount", amount);
        RecipeScroll.calcuAllAmount(scroll);

        return nbt.getInt("output_amount");
    }

	/* Add information about pattern and ingrdient to tooltip */
    @OnlyIn(Dist.CLIENT)
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    	super.addInformation(stack, worldIn, tooltip, flagIn);

    	String patternStr = I18n.format("general.foodpower.cooking_pattern") + ":";
    	CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();
    	if(nbt.contains("pattern")){
    		patternStr += I18n.format("pattern.foodpower." + nbt.getString("pattern"));
    	}else{
    		patternStr += I18n.format("general.foodpower.none");
    	}
    	tooltip.add(new StringTextComponent(patternStr));

    	if(nbt.contains("ingredients")){
    		tooltip.add(new TranslationTextComponent("general.foodpower.ingredients"));
    		ListNBT list = (ListNBT)nbt.get("ingredients");
    		for(int i = 0; i <= list.size()-1; i ++){
    			CompoundNBT element = (CompoundNBT) list.get(i);
    			String ingredientStr = I18n.format("item.foodpower." + element.getString("name"));
    			ingredientStr += " [" + I18n.format("general.foodpower.level") + element.getInt("level") + "]";
                if(element.contains("amount"))
                    ingredientStr += " (" + element.getInt("amount") + ")";
    			tooltip.add(new StringTextComponent("  " + ingredientStr));
    		}
    	}
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ITextComponent getDisplayName(ItemStack stack){
    	if(stack.hasTag() 
    		&& stack.getTag().contains("displayName") 
    		&& !stack.getTag().getString("displayName").isEmpty()){

    		return new StringTextComponent(stack.getTag().getString("displayName"));
    	}
    	return new TranslationTextComponent("general.foodpower.unknown_recipe");
    }

    /* Open gui on right click, transfer nbt tag to construct container and screen */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn){
        ItemStack stack = playerIn.getHeldEquipment().iterator().next();
        CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();

        /* ActionResultType.SUCCESS */
        if(worldIn.isRemote) return ActionResult.func_226248_a_(stack);
        NetworkHooks.openGui((ServerPlayerEntity)playerIn, new INamedContainerProvider(){
            @Override
            public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player){
                return new RecipeScrollContainer(windowId, playerInventory, nbt);
            }
            @Override
            public ITextComponent getDisplayName(){
                return new StringTextComponent("");
            }
        }, extraData -> {
            extraData.writeCompoundTag(nbt);
        });
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}