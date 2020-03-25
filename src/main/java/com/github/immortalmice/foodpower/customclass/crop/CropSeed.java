package com.github.immortalmice.foodpower.customclass.crop;

import net.minecraft.item.Item;
import net.minecraft.item.BlockNamedItem;

import com.github.immortalmice.foodpower.customclass.crop.CropSeed;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class CropSeed extends BlockNamedItem{
	private String name;
	public CropSeed(String nameIn, CropBlock blockIn){
		super(blockIn, new Item.Properties().group(FPCreativeTabs.ITEM_TAB));

		this.name = nameIn;
	}

	public String getFPName(){
		return this.name;
	}
}