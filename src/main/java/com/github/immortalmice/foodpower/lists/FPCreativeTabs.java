package com.github.immortalmice.foodpower.lists;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.lists.Ingredients.Items;
import com.github.immortalmice.foodpower.lists.KitchenAppliances.Blocks;

/* All Food Power's creativetabs list below.*/
public class FPCreativeTabs{
	public static final ItemGroup ITEM_TAB = (new ItemGroup("foodpower.items"){
		@Override
		public ItemStack createIcon(){
			return new ItemStack(Items.BUTTER);
		}
	});
	public static final ItemGroup BLOCK_TAB = (new ItemGroup("foodpower.blocks"){
		@Override
		public ItemStack createIcon(){
			return new ItemStack(Blocks.OVEN.asItem());
		}
	});
}