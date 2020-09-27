package com.github.immortalmice.foodpower.customclass.container.classes.kitchenappliance;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe.ItemStackRequest;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe.StepRequest;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.KitchenApplianceTileEntity;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.KitchenApplianceTileEntity.KitchenApplanceItemHandler;
import com.github.immortalmice.foodpower.customclass.util.SlotPosProvider.KitchenApplianceSlotPos;
import com.github.immortalmice.foodpower.customclass.util.SlotPosProvider.Position2D;
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
			
			this.addSlot(new SlotItemHandler(this.itemHandler, 0, 45, 119){
				@Override
				public void onSlotChanged(){
					KitchenApplianceContainer.this.updateSlot();
				}
			});
			this.addSlot(new SlotItemHandler(this.itemHandler, 1, 175, 64));

			this.updateSlot();
		}
	}

	/* Dynamic ingredient slots */
	private void updateSlot(){
		while(this.inventorySlots.size() > 38){
			this.inventorySlots.remove(38);
		}

		List<ItemStackRequest> itemRequests = this.getCurrentItemRequests();
		List<Position2D> pos = KitchenApplianceSlotPos.provide(itemRequests.size());

		for(int i = 0; i <= itemRequests.size()-1; i ++){
			this.addSlot(new KitchenApplianceSlot(this.itemHandler, i + 2, pos.get(i).x, pos.get(i).y, itemRequests.get(i)));
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

	@Nullable
	public KitchenApplanceItemHandler getItemHandler(){
		return this.itemHandler;
	}

	public List<ItemStackRequest> getCurrentItemRequests(){
		StepRequest stepRequest = this.itemHandler != null ? this.itemHandler.getCurrentStepRequest() : null;
		if(stepRequest != null){
			return stepRequest.getRequires();
		}
		return new ArrayList<>();
	}
}