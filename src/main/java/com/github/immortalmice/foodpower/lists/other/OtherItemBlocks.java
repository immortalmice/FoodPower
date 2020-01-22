package com.github.immortalmice.foodpower.lists.other;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.lists.GUIs;
import com.github.immortalmice.foodpower.baseclass.ItemBlockBase;

/** All the other itemblocks in mod need to be registed will list below */
public class OtherItemBlocks{
	public static final List<Block> list = new ArrayList<Block>();

	public static final ItemBlockBase MARKET_BLOCK = (new ItemBlockBase("market_block", Material.ROCK){
		@Override
		public boolean onBlockActivated(World worldIn, BlockPos blockPosIn, 
			IBlockState blockStateIn, EntityPlayer playerIn, EnumHand hand, EnumFacing sideIn, 
			float hitX, float hitY, float hitZ){
			if(!playerIn.isSneaking()){
				if(!worldIn.isRemote){
					playerIn.openGui(FoodPower.instance, GUIs.MARKET.id, worldIn, blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ());
				}
				return true;
			}
			return false;
		}
	});
}