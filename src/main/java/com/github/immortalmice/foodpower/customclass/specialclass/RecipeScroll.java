package com.github.immortalmice.foodpower.customclass.specialclass;

import java.util.List;
import java.lang.Math;
import javax.annotation.Nullable;

import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.customclass.client.TooltipUtil;
import com.github.immortalmice.foodpower.customclass.container.classes.recipescroll.RecipeScrollContainer;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;

public class RecipeScroll extends ItemBase{
    private static final IItemPropertyGetter RARITY_GETTER = (stack, world, entity) -> {
        CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();

        if(!nbt.contains("rarity")) return 0.0f; // Rarity WOOD
        switch(nbt.getInt("rarity")){
            case 0:
                return 0.0f; // Rarity WOOD
            case 1:
                return 0.1f; // Rarity IRON
            case 2:
                return 0.2f; // Rarity GOLD
            case 3:
                return 0.3f; // Rarity DIAMOND
            default:
                return 0.0f; // Rarity WOOD
        }
    };
    private static final float[] RARITY_DISCOUNT = {1.0f, 0.8f, 0.6f, 0.4f};
	public RecipeScroll(){
		super("recipe_scroll", new Item.Properties().maxStackSize(1));

        this.addPropertyOverride(new ResourceLocation(FoodPower.MODID, "scroll_property"), RecipeScroll.RARITY_GETTER);
	}

	/* create a ItemStack and set NBTTags with given information */
	public static ItemStack create(CookingPattern patternIn, List<ItemStack> listIn, String nameIn){
		ItemStack result = new ItemStack(Items.RECIPE_SCROLL);

		CompoundNBT nbt = new CompoundNBT();

		nbt.putString("pattern", patternIn.getName());
		nbt.putString("displayName", nameIn);

		ListNBT tagList = new ListNBT();
		for(int i = 0; i <= listIn.size()-1; i ++){
            if(listIn.get(i).isEmpty()) return ItemStack.EMPTY;
			CompoundNBT element = new CompoundNBT();
            Ingredient ingrdient = Ingredients.getIngredientByItem(listIn.get(i).getItem());
            if(ingrdient.isEmpty()) continue;
			element.putString("name", ingrdient.getFPName());
			element.putInt("level", listIn.get(i).getCount());
			tagList.add(element);
		}
		nbt.put("ingredients", tagList);

		result.setTag(nbt);
		return result;
	}

    /* Set rarity and calculate ingredient amount in need */
    public static void initStack(ItemStack stack, int rarity, float rand){
        CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();

        nbt.putInt("rarity", rarity);
        nbt.putDouble("random", rand);
        /* How many meals player want to cook? */
        nbt.putInt("output_amount", 1);

        RecipeScroll.calcuAllAmount(nbt);
    }

    /* Calculate amount with single ingredient, Lv.1 amount*1, Lv.2 amount*2 and Lv.3 amount*4 */
    private static int calcuAmount(String ingredientName, int outputAmount, int level, Double rand, int rarity){
        Ingredient ingrdient = Ingredients.getIngredientByName(ingredientName);
        return (int) Math.ceil(ingrdient.getBaseAmount() * outputAmount * Math.pow(2, level - 1) * rand * RecipeScroll.RARITY_DISCOUNT[rarity]);
    }

    private static void calcuAllAmount(ItemStack scroll){
        RecipeScroll.calcuAllAmount(scroll.hasTag() ? scroll.getTag() : new CompoundNBT());
    }

    /* Calculate amount with each ingredient */
    public static void calcuAllAmount(CompoundNBT nbt){
        int ouputAmount = 1;
        int rarity = 0;
        double random = 1.1;

        if(!nbt.contains("output_amount") || !nbt.contains("rarity") || !nbt.contains("random")){
            nbt.putInt("output_amount", ouputAmount);
            nbt.putInt("rarity", rarity);
            nbt.putDouble("random", random);
        }

        ouputAmount = nbt.getInt("output_amount");
        rarity = nbt.getInt("rarity");
        random = nbt.getDouble("random");

        if(nbt.contains("ingredients")){
            ListNBT list = (ListNBT)nbt.get("ingredients");
            for(int i = 0; i <= list.size()-1; i ++){
                CompoundNBT element = (CompoundNBT)list.get(i);
                int amount = RecipeScroll.calcuAmount(element.getString("name")
                    , ouputAmount
                    , element.getInt("level")
                    , random
                    , rarity);
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
        TooltipUtil tooltipHelper = new TooltipUtil(tooltip);

    	String patternStr = TooltipUtil.translate("general.foodpower.cooking_pattern") + " : ";
    	CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();
    	if(nbt.contains("pattern")){
    		patternStr += TooltipUtil.translate("pattern.foodpower." + nbt.getString("pattern"));
    	}else{
    		patternStr += TooltipUtil.translate("general.foodpower.none");
    	}
    	tooltipHelper.add(patternStr);
        if(nbt.contains("output_amount")){
            tooltipHelper.addTranslate("message.foodpower.output_amount", nbt.getInt("output_amount"));
        }

        /* Whether player press down shift key or not */
        boolean moreInfo = Screen.hasShiftDown();
        if(!moreInfo){
            tooltipHelper.newBlankRow();
            tooltipHelper.addTranslate("message.foodpower.tooltip_more_info"
                , (new Style()).setItalic(true).setColor(TextFormatting.GRAY));
            return;
        }

    	if(nbt.contains("ingredients")){
            tooltipHelper.newBlankRow();
    		tooltipHelper.addTranslate("general.foodpower.ingredients");
    		ListNBT list = (ListNBT)nbt.get("ingredients");
    		for(int i = 0; i <= list.size()-1; i ++){
    			CompoundNBT element = (CompoundNBT) list.get(i);
    			String ingredientStr = TooltipUtil.translate("item.foodpower." + element.getString("name"));
    			ingredientStr += " [" + TooltipUtil.translate("general.foodpower.level") + element.getInt("level") + "]";
                if(element.contains("amount"))
                    ingredientStr += " (" + element.getInt("amount") + ")";
    			tooltipHelper.addWithLeftSpace(ingredientStr);
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
        if(worldIn.isRemote) return ActionResult.resultSuccess(stack);
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