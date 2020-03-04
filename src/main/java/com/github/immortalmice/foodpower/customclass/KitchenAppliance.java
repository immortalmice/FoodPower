package com.github.immortalmice.foodpower.customclass;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import com.github.immortalmice.foodpower.baseclass.BlockBase;

public class KitchenAppliance extends BlockBase{
	private VoxelShape blockAABB;

	public KitchenAppliance(String name, AxisAlignedBB blockAABBIn){
		super(name, Block.Properties.create(Material.IRON)
			.harvestLevel(2)
			.harvestTool(ToolType.PICKAXE)
			.sound(SoundType.METAL)
			.hardnessAndResistance(1.5f)
			.func_226896_b_());

		this.blockAABB = VoxelShapes.create(blockAABBIn);
	}
	
	/* How dare you use stone pickaxe break this holy things */
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    	return this.blockAABB;
    }
}