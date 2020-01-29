package com.github.immortalmice.foodpower.customclass.specialclass;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.tileentity.TileEntity;
import com.github.immortalmice.foodpower.baseclass.BlockRotatableBase;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.lists.GUIs;
import com.github.immortalmice.foodpower.lists.TileEntitys;
import com.github.immortalmice.foodpower.lists.other.OtherItemBlocks;

public class RecipeTable extends BlockRotatableBase{
	public RecipeTable(){
		super("recipe_table", Material.ROCK);

		OtherItemBlocks.list.add(this);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos blockPosIn, 
		IBlockState blockStateIn, EntityPlayer playerIn, EnumHand hand, EnumFacing sideIn, 
		float hitX, float hitY, float hitZ){
		if(!playerIn.isSneaking()){
			if(!worldIn.isRemote){
				playerIn.openGui(FoodPower.instance, GUIs.RECIPE_TABLE.getId(), worldIn, blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ());
			}
			return true;
		}
		return false;
	}
	@Override
	public boolean hasTileEntity(IBlockState state){
		return true;
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state){
		return TileEntitys.RECIPE_TABLE.getTileEntity();
	}
}
