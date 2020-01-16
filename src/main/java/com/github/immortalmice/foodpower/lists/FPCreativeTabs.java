package com.github.immortalmice.foodpower.lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;

/** All Food Power's creativetabs list below.*/
public class FPCreativeTabs{
	public static final CreativeTabs ITEM_TAB = (new CreativeTabs("item"){
		@Override
		public ItemStack createIcon(){
			return new ItemStack(Ingredients.BUTTER);
		}
	});
	public static final CreativeTabs BLOCK_TAB = (new CreativeTabs("block"){
		@Override
		public ItemStack createIcon(){
			return new ItemStack(Item.getItemFromBlock(KitchenAppliances.OVEN));
		}
	});
}