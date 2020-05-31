package com.github.immortalmice.foodpower.baseclass;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockBase extends HorizontalBlock{
	private String name;

	public BlockBase(String nameIn, Block.Properties propertiesIn){
		super(propertiesIn);

		this.name = nameIn;
		this.setDefaultState(this.stateContainer.getBaseState().with(HorizontalBlock.HORIZONTAL_FACING, Direction.NORTH));
	}

	public BlockBase(String nameIn, Material material){
		this(nameIn, Block.Properties.create(material).notSolid());
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HorizontalBlock.HORIZONTAL_FACING);
	}

	@Override
	@SuppressWarnings("deprecation")
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder){
		ResourceLocation resourcelocation = this.getLootTable();
		LootTableManager manager = builder.getWorld().getServer().getLootTableManager();
		if(manager.getLootTableFromLocation(resourcelocation) != LootTable.EMPTY_LOOT_TABLE){
			return super.getDrops(state, builder);
		}
		return Arrays.asList(new ItemStack(ForgeRegistries.ITEMS.getValue(this.getRegistryName())));
	}

	public String getFPName(){
		return this.name;
	}
}