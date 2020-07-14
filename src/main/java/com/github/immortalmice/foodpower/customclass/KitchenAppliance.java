package com.github.immortalmice.foodpower.customclass;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import com.github.immortalmice.foodpower.baseclass.BlockBase;
import com.github.immortalmice.foodpower.customclass.container.classes.kitchenappliance.KitchenApplianceContainer;

public class KitchenAppliance extends BlockBase{
	private VoxelShape blockShape;

	public KitchenAppliance(AxisAlignedBB blockAABBIn){
		super(Block.Properties.create(Material.IRON)
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

    @Override
	public ActionResultType onBlockActivated(BlockState stateIn, World worldIn
		, BlockPos posIn, PlayerEntity playerIn
		, Hand handIn, BlockRayTraceResult resultIn){
		
		if(worldIn.isRemote) return ActionResultType.SUCCESS;

		if(!playerIn.isSneaking()){
			NetworkHooks.openGui((ServerPlayerEntity)playerIn, new INamedContainerProvider(){
				@Override
				public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player){
					return new KitchenApplianceContainer(windowId, playerInventory, KitchenAppliance.this);
				}
				@Override
				public ITextComponent getDisplayName(){
					return new TranslationTextComponent(KitchenAppliance.this.getTranslationKey());
				}
			}, extraData -> {
				extraData.writeResourceLocation(KitchenAppliance.this.getRegistryName());
			});
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
}