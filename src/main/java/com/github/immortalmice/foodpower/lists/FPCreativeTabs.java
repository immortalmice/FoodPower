package com.github.immortalmice.foodpower.lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.lists.Ingredients;

/** All Food Power's creativetabs list below.*/
public class FPCreativeTabs{
	public static final CreativeTabs ingredientTab = (new CreativeTabs("ingredients"){
		@Override
		public ItemStack createIcon(){
			return new ItemStack(Ingredients.BUTTER);
		}
	});
	public static final CreativeTabs kitchenApplianceTab = (new CreativeTabs("kitchenAppliance"){
		@Override
		public ItemStack createIcon(){
			return ItemStack.EMPTY;
		}
	});
}