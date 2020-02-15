package com.github.immortalmice.foodpower.customclass.specialclass;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.other.OtherItems;

public class RecipeScroll extends ItemBase{
	public RecipeScroll(){
		super("recipe_scroll");

		this.setMaxStackSize(1);

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
			element.setInteger("count", listIn.get(i).getCount());
			tagList.appendTag(element);
		}
		nbt.setTag("ingredients", tagList);

		result.setTagCompound(nbt);
		return result;
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
    			ingredientStr += "(" + I18n.format("general.level.name") + element.getInteger("count") + ")";
    			tooltip.add("  " + ingredientStr);
    		}
    	}
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
    	if(stack.hasTagCompound() 
    		&& stack.getTagCompound().hasKey("displayName") 
    		&& !stack.getTagCompound().getString("displayName").isEmpty()){

    		return stack.getTagCompound().getString("displayName");
    	}
    	return I18n.format("general.unknown_recipe.name");
    }
}