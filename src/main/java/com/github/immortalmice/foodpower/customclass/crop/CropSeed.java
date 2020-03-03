package com.github.immortalmice.foodpower.customclass.crop;

import net.minecraft.item.Item;
import net.minecraft.item.BlockNamedItem;

import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.crop.CropBlock;
import com.github.immortalmice.foodpower.customclass.crop.CropSeed;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;
import com.github.immortalmice.foodpower.lists.Crops;

public class CropSeed extends BlockNamedItem{
	private String name;
	public CropSeed(String nameIn, Ingredient cropIn){
		super(new CropBlock(nameIn, cropIn)
			, new Item.Properties().group(FPCreativeTabs.ITEM_TAB));

		this.name = nameIn.concat("_seed");

        this.setRegistryName(name.concat("_seed"));

        Crops.seedList.add(this);
	}

	@Override
	public String getTranslationKey(){
		return this.name;
	}

	public String getFPName(){
		return this.name;
	}
}