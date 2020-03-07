package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

public class BlockBase extends HorizontalBlock{
	private String name;

	public BlockBase(String nameIn, Block.Properties propertiesIn){
		super(propertiesIn);

		this.name = nameIn;
		this.setDefaultState(this.stateContainer.getBaseState().with(HorizontalBlock.HORIZONTAL_FACING, Direction.NORTH));
	}

	public BlockBase(String nameIn, Material material){
		this(nameIn, Block.Properties.create(material).func_226896_b_());
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HorizontalBlock.HORIZONTAL_FACING);
	}

	public String getFPName(){
		return this.name;
	}
}