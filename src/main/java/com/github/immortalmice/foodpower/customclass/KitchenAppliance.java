package com.github.immortalmice.foodpower.customclass;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import com.github.immortalmice.foodpower.baseclass.BlockBase;

public class KitchenAppliance extends BlockBase{
	private VoxelShape blockShape;

	public KitchenAppliance(String name, AxisAlignedBB blockAABBIn){
		super(name, Block.Properties.create(Material.IRON)
			.harvestLevel(2)
			.harvestTool(ToolType.PICKAXE)
			.sound(SoundType.METAL)
			.hardnessAndResistance(1.5f)
			.notSolid());

		this.blockShape = Block.makeCuboidShape(blockAABBIn.minX, blockAABBIn.minY, blockAABBIn.minZ, blockAABBIn.maxX, blockAABBIn.maxY, blockAABBIn.maxZ);
	}

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    	return this.blockShape;
    }
}