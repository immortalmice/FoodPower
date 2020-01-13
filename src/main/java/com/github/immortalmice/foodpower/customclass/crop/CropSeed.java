package com.github.immortalmice.foodpower.customclass.crop;

import net.minecraft.item.ItemSeeds;
import net.minecraft.init.Blocks;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.customclass.crop.CropBlock;
import com.github.immortalmice.foodpower.customclass.crop.CropSeed;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;
import com.github.immortalmice.foodpower.lists.Crops;

public class CropSeed extends ItemSeeds{
	public CropSeed(String name, ItemFoodBase cropIn){
		super(new CropBlock(name, cropIn), Blocks.FARMLAND);

		this.setTranslationKey(name.concat("_seed"));
        this.setRegistryName(name.concat("_seed"));

        this.setCreativeTab(FPCreativeTabs.ingredientAndSeedTab);

        Crops.seedList.add(this);
	}
}