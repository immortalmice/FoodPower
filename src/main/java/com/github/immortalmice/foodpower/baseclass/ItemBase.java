package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.item.Item;

import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class ItemBase extends Item{
	private String name;

	public ItemBase(String nameIn, Item.Properties propertiesIn){
		super(propertiesIn.group(FPCreativeTabs.ITEM_TAB));

		this.name = nameIn;
	}
	public ItemBase(String nameIn){
		this(nameIn, new Item.Properties().group(FPCreativeTabs.ITEM_TAB));
	}

	public String getFPName(){
		return this.name;
	}
}