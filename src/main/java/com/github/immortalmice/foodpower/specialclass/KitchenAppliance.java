package com.github.immortalmice.foodpower.specialclass;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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

import java.util.List;

import com.github.immortalmice.foodpower.baseclass.BlockBase;
import com.github.immortalmice.foodpower.container.kitchenappliance.KitchenApplianceContainer;
import com.github.immortalmice.foodpower.tileentity.KitchenApplianceTileEntity;
import com.github.immortalmice.foodpower.tileentity.KitchenApplianceTileEntity.KitchenApplanceItemHandler;

public class KitchenAppliance extends BlockBase{
	private final VoxelShape blockShape;
	private final boolean isElectrical;

	public KitchenAppliance(AxisAlignedBB blockAABBIn){
		this(blockAABBIn, true);
	}

	public KitchenAppliance(AxisAlignedBB blockAABBIn, boolean isElectricalIn){
		super(Block.Properties.create(Material.IRON)
			.harvestLevel(2)
			.harvestTool(ToolType.PICKAXE)
			.sound(SoundType.METAL)
			.hardnessAndResistance(1.5f)
			.notSolid());

		this.blockShape = Block.makeCuboidShape(blockAABBIn.minX, blockAABBIn.minY, blockAABBIn.minZ, blockAABBIn.maxX, blockAABBIn.maxY, blockAABBIn.maxZ);
		this.isElectrical = isElectricalIn;
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
					return new KitchenApplianceContainer(windowId, playerInventory, posIn);
				}
				@Override
				public ITextComponent getDisplayName(){
					return new TranslationTextComponent(KitchenAppliance.this.getTranslationKey());
				}
			}, extraData -> {
				extraData.writeBlockPos(posIn);
			});
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player){
		super.onBlockHarvested(world, pos, state, player);
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof KitchenApplianceTileEntity){
			KitchenApplanceItemHandler itemHandler = ((KitchenApplianceTileEntity) tileEntity).getItemHandler();

			List<ItemStack> list = itemHandler.getItems();
			list.forEach((stack) -> {
				world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack));
			});
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state){
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world){
		return new KitchenApplianceTileEntity(this);
	}
	
	public boolean isElectrical(){
		return this.isElectrical;
	}
}