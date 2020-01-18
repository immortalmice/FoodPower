package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.github.immortalmice.foodpower.baseclass.ItemBlockBase;

/** All the other items in mod need to be registed will list below */
public class OtherItemBlocks{
	public static final List<Block> list = new ArrayList<Block>();

	public static final ItemBlockBase MARKET_BLOCK = new ItemBlockBase("market_block", Material.ROCK, true);
}