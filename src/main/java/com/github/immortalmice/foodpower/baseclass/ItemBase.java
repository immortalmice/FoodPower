package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.item.Item;

import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class ItemBase extends Item{
	public ItemBase(Item.Properties propertiesIn){
		super(propertiesIn);
	}

	public ItemBase(Item.Properties propertiesIn, boolean isInCreativeTab){
		this(propertiesIn.group(isInCreativeTab ? FPCreativeTabs.ITEM_TAB : null));
	}

	public ItemBase(){
		this(new Item.Properties().group(FPCreativeTabs.ITEM_TAB));
	}

}