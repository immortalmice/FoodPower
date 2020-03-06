package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;

import com.github.immortalmice.foodpower.customclass.specialclass.Market;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeTable;

/* All the other blocks in mod need to be registed will list below */
public class OtherBlocks{
	public static final List<Block> list = new ArrayList<Block>();

	public static final Market MARKET_BLOCK = new Market();
	public static final RecipeTable RECIPE_TABLE = new RecipeTable();
}