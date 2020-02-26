package com.github.immortalmice.foodpower.lists;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;

/* All Food Power's creativetabs list below.*/
public class FPCreativeTabs{
	public static final ItemGroup ITEM_TAB = (new ItemGroup("items"){
		@Override
		public ItemStack createIcon(){
			return new ItemStack(Ingredients.BUTTER);
		}
	});
	public static final ItemGroup BLOCK_TAB = (new ItemGroup("blocks"){
		@Override
		public ItemStack createIcon(){
			return new ItemStack(KitchenAppliances.OVEN.asItem());
		}
	});
}