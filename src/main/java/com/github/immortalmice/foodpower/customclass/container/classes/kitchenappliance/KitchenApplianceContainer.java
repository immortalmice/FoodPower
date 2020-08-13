package com.github.immortalmice.foodpower.customclass.container.classes.kitchenappliance;

import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.KitchenApplianceTileEntity;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.KitchenApplianceTileEntity.KitchenApplanceItemHandler;
import com.github.immortalmice.foodpower.lists.Containers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.SlotItemHandler;

public class KitchenApplianceContainer extends ContainerBase{
	private KitchenApplianceTileEntity tileEntity;
	private KitchenApplanceItemHandler itemHandler;

	public KitchenApplianceContainer(int windowId, PlayerInventory playerInventory, PacketBuffer extraData){
		this(windowId, playerInventory, extraData.readBlockPos());
	}

	public KitchenApplianceContainer(int windowId, PlayerInventory playerInventory, BlockPos posIn){
		super(Containers.ContainerTypes.KITCHEN_APPLIANCE, windowId, new int[]{45, 145}, playerInventory);

		TileEntity tile = playerInventory.player.world.getTileEntity(posIn);
		if(tile instanceof KitchenApplianceTileEntity){
			this.tileEntity = (KitchenApplianceTileEntity) tile;
			this.itemHandler = this.tileEntity.getItemHandler();
			
			this.addSlot(new SlotItemHandler(this.itemHandler, 0, 45, 119));
			this.addSlot(new SlotItemHandler(this.itemHandler, 1, 175, 64));
		}
	}

	@Nullable
	public KitchenApplianceTileEntity getTileEntity(){
		return this.tileEntity;
	}

	@Nullable
	public KitchenAppliance getBlock(){
		if(this.tileEntity != null){
			return this.tileEntity.getBlock();
		}
		return null;
	}
}