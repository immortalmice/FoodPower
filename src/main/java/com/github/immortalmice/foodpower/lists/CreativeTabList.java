package com.github.immortalmice.foodpower.lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabList{
	public static final CreativeTabs ingredientTab = (new CreativeTabs("ingredients"){
		@Override
		public ItemStack createIcon(){
			return ItemStack.EMPTY;
		}
	});
}