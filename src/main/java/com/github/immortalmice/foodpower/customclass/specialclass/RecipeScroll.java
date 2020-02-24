package com.github.immortalmice.foodpower.customclass.specialclass;

import java.util.List;
import java.util.Random;
import java.lang.Math;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.GUIs;
import com.github.immortalmice.foodpower.lists.other.OtherItems;

public class RecipeScroll extends ItemBase{
	public RecipeScroll(){
		super("recipe_scroll");

		this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);

		OtherItems.list.add(this);
	}

	/* create a ItemStack and set NBTTags with given information */
	public static ItemStack create(CookingPattern patternIn, List<ItemStack> listIn, String nameIn){
		ItemStack result = new ItemStack(OtherItems.RECIPE_SCROLL);

		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setString("pattern", patternIn.getName());
		nbt.setString("displayName", nameIn);

		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i <= listIn.size()-1; i ++){
			NBTTagCompound element = new NBTTagCompound();
			element.setString("name", ((Ingredient)listIn.get(i).getItem()).getName());
			element.setInteger("level", listIn.get(i).getCount());
			tagList.appendTag(element);
		}
		nbt.setTag("ingredients", tagList);

		result.setTagCompound(nbt);
		return result;
	}

    public static void initStack(ItemStack stack, Random rand){
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();

        if(nbt.hasKey("rarity")) return;

        int rarity = rand.nextInt(4);
        stack.setItemDamage(rarity);
        nbt.setInteger("rarity", rarity);

        nbt.setDouble("random", rand.nextFloat() * 0.2 + 0.9);
        nbt.setInteger("output_amount", 1);

        if(nbt.hasKey("ingredients")){
            NBTTagList list = (NBTTagList)nbt.getTag("ingredients");
            for(int i = 0; i <= list.tagCount()-1; i ++){
                NBTTagCompound element = (NBTTagCompound)list.get(i);
                int amount = RecipeScroll.calcuAmount(element.getString("name"), nbt.getInteger("output_amount"), element.getInteger("level"), nbt.getDouble("random"));
                element.setInteger("amount", amount);
            }
        }
    }

    private static int calcuAmount(String ingredientName, int outputAmount, int level, Double rand){
        Ingredient ingrdient = Ingredients.getIngredientByName(ingredientName);
        return (int) Math.ceil(ingrdient.getBaseAmount() * outputAmount * Math.pow(2, level - 1) * rand);
    }

	/* Add information about pattern and ingrdient to tooltip */
	@SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    	super.addInformation(stack, worldIn, tooltip, flagIn);

    	String patternStr = I18n.format("general.cooking_pattern.name") + ":";
    	NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
    	if(nbt.hasKey("pattern")){
    		patternStr += I18n.format("pattern." + nbt.getString("pattern") + ".name");
    	}else{
    		patternStr += I18n.format("general.none.name");
    	}
    	tooltip.add(patternStr);

    	if(nbt.hasKey("ingredients")){
    		tooltip.add(I18n.format("general.ingredients.name"));
    		NBTTagList list = (NBTTagList)nbt.getTag("ingredients");
    		for(int i = 0; i <= list.tagCount()-1; i ++){
    			NBTTagCompound element = list.getCompoundTagAt(i);
    			String ingredientStr = I18n.format("item." + element.getString("name") + ".name");
    			ingredientStr += " [" + I18n.format("general.level.name") + element.getInteger("level") + "]";
                if(element.hasKey("amount"))
                    ingredientStr += " (" + element.getInteger("amount") + ")";
    			tooltip.add("  " + ingredientStr);
    		}
    	}
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack){
    	if(stack.hasTagCompound() 
    		&& stack.getTagCompound().hasKey("displayName") 
    		&& !stack.getTagCompound().getString("displayName").isEmpty()){

    		return stack.getTagCompound().getString("displayName");
    	}
    	return I18n.format("general.unknown_recipe.name");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        /* Only Open Gui in client side */
        if(worldIn.isRemote){
            BlockPos pos = playerIn.getPosition();
            playerIn.openGui(FoodPower.instance, GUIs.RECIPE_SCROLL.getId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}