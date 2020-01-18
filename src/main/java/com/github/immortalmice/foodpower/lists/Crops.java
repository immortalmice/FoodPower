package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.github.immortalmice.foodpower.customclass.crop.CropSeed;
import com.github.immortalmice.foodpower.customclass.crop.CropBlock;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class Crops{
	public static final List<CropSeed> seedList = new ArrayList<CropSeed>();
	public static final List<CropBlock> blockList = new ArrayList<CropBlock>();

	public static final CropSeed MINT = new CropSeed("mint", Ingredients.MINT);
	public static final CropSeed TOMAMTO = new CropSeed("tomato", Ingredients.TOMATO);
	public static final CropSeed RICE = new CropSeed("rice", Ingredients.RICE);
	public static final CropSeed CHILI = new CropSeed("chili", Ingredients.CHILI);
	public static final CropSeed SPINACH = new CropSeed("spinach", Ingredients.SPINACH);
	public static final CropSeed CABBAGE = new CropSeed("cabbage", Ingredients.CABBAGE);
	public static final CropSeed CORN = new CropSeed("corn", Ingredients.CORN);

	public static void registSeeds(){
		for(int i = 0; i <= seedList.size()-1; i ++){
			MinecraftForge.addGrassSeed(new ItemStack(seedList.get(i)), 10);
		}
	}
}