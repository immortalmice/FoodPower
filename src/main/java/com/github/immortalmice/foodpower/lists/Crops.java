package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.crop.CropSeed;
import com.github.immortalmice.foodpower.customclass.crop.CropBlock;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class Crops{
	public static final DeferredRegister<Item> ITEM_REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);
	public static final DeferredRegister<Block> BLOCK_REGISTER = new DeferredRegister<Block>(ForgeRegistries.BLOCKS, FoodPower.MODID);

	public static final List<CropSeed> seedList = new ArrayList<CropSeed>();
	public static final List<CropBlock> blockList = new ArrayList<CropBlock>();

	public static final CropSeed MINT = new CropSeed("mint", Ingredients.MINT);
	public static final CropSeed TOMAMTO = new CropSeed("tomato", Ingredients.TOMATO);
	public static final CropSeed RICE = new CropSeed("rice", Ingredients.RICE);
	public static final CropSeed CHILI = new CropSeed("chili", Ingredients.CHILI);
	public static final CropSeed SPINACH = new CropSeed("spinach", Ingredients.SPINACH);
	public static final CropSeed CABBAGE = new CropSeed("cabbage", Ingredients.CABBAGE);
	public static final CropSeed CORN = new CropSeed("corn", Ingredients.CORN);

	public static CropSeed getSeed(String nameIn){
		for(int i = 0; i <= Crops.seedList.size()-1; i ++){
			if(Crops.seedList.get(i).getFPName() == nameIn){
				return Crops.seedList.get(i);
			}
		}
		return Crops.MINT;
	}
}