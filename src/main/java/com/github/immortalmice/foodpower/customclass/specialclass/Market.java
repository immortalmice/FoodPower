package com.github.immortalmice.foodpower.customclass.specialclass;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

import com.github.immortalmice.foodpower.baseclass.BlockBase;
import com.github.immortalmice.foodpower.lists.other.OtherItemBlocks;

public class Market extends BlockBase{
	public Market(){
		super("market", Material.ROCK);

		OtherItemBlocks.list.add(this);
	}
	/* Open the GUI */
	@Override
	public ActionResultType func_225533_a_(BlockState stateIn, World worldIn
		, BlockPos posIn, PlayerEntity playerIn
		, Hand handIn, BlockRayTraceResult resultIn){
		
		if(!playerIn.func_225608_bj_()){
			if(!worldIn.isRemote){
				playerIn.openContainer(stateIn.getContainer(worldIn, posIn));
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public boolean hasTileEntity(BlockState state){
		return true;
	}

	@Nullable
	@Override
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos){

	}
}