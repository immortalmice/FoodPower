package com.github.immortalmice.foodpower.customclass.tree;

import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.init.Blocks;

import com.github.immortalmice.foodpower.customclass.tree.TreeLeave;

public class TreeGenerator extends WorldGenTrees{
	public TreeGenerator(TreeLeave treeLeave){
		super(true, 4, Blocks.LOG.getDefaultState(), treeLeave.getDefaultState(), false);
	}
}