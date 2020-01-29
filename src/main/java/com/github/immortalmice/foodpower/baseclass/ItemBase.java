package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.item.Item;

import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class ItemBase extends Item{
	public ItemBase(String name){
		super();
		
		this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(FPCreativeTabs.ITEM_TAB);
	}
}