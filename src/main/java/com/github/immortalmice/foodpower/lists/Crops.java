package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.crop.CropSeed;
import com.github.immortalmice.foodpower.customclass.crop.CropBlock;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class Crops{
	public static final List<CropSeed> seedList = new ArrayList<CropSeed>();
	public static final List<CropBlock> blockList = new ArrayList<CropBlock>();

	public static final CropSeed MINT_SEED = new CropSeed("mint", Ingredients.MINT);
	public static final CropSeed TOMAMTO_SEED = new CropSeed("tomato", Ingredients.TOMATO);
	public static final CropSeed RICE_SEED = new CropSeed("rice", Ingredients.RICE);
	public static final CropSeed CHILI_SEED = new CropSeed("chili", Ingredients.CHILI);
	public static final CropSeed VEGETABLE_SEED = new CropSeed("vegetable", Ingredients.VEGETABLE);
	public static final CropSeed CORN_SEED = new CropSeed("corn", Ingredients.CORN);
}