package com.github.immortalmice.foodpower.customclass.tree;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.github.immortalmice.foodpower.customclass.tree.TreeLeave;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class TreeSaplingBush extends BlockBush implements IGrowable{
	public TreeSaplingBush(String nameIn, Item dropItemIn){
		this.setTranslationKey(nameIn.concat("_sapling"));
        this.setRegistryName(nameIn.concat("_sapling"));

        this.setCreativeTab(FPCreativeTabs.ITEM_TAB);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);

        /** Add to sapling bush list, and regist it later */
        Trees.saplingBushList.add(this);

        /** Construct tree leave block */
        new TreeLeave(nameIn, this, dropItemIn);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient){
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state){
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state){
		return;
	}
}