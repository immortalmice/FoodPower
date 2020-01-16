package com.github.immortalmice.foodpower.customclass.tree;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.github.immortalmice.foodpower.customclass.tree.TreeSaplingBush;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class TreeLeave extends BlockLeaves{
	private TreeSaplingBush sapling;
	private Item dropItem;
	public TreeLeave(String nameIn, TreeSaplingBush saplingIn, Item dropItemIn){
		this.setTranslationKey(nameIn.concat("_leave"));
        this.setRegistryName(nameIn.concat("_leave"));

        this.setCreativeTab(FPCreativeTabs.BLOCK_TAB);

		this.sapling = saplingIn;
		this.dropItem = dropItemIn;

		Trees.leaveList.add(this);
	}

	@Override
	public BlockPlanks.EnumType getWoodType(int meta){
		return BlockPlanks.EnumType.OAK;
	}

	@Override
	public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune){
		ArrayList<ItemStack> al = new ArrayList<ItemStack>();
		al.add(new ItemStack(this));
		return al;
	}
}