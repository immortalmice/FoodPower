package com.github.immortalmice.foodpower.customclass.container.classes.kitchenappliance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.github.immortalmice.foodpower.lists.OtherItems.Items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
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

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player){
		if(slotId >= 38 && this.itemHandler != null && clickTypeIn != ClickType.QUICK_MOVE){
			
			// Left Click default
			ItemStack insertStack = player.inventory.getItemStack().copy();
			int extractAmount = 64;
			
			// Right Click 
			if(Container.getDragEvent(dragType) == 1 && clickTypeIn != ClickType.QUICK_CRAFT){
				insertStack.setCount(1);
				extractAmount = 1;
			}
			
			ItemStack result = player.inventory.getItemStack().copy();
			// Insertion when mouse holding a stack
			if(!player.inventory.getItemStack().isEmpty()){
				int realInsertAmount = insertStack.getCount() - this.itemHandler.insertItem(slotId - 36, insertStack, false).getCount();
				int resultAmount = player.inventory.getItemStack().getCount() - realInsertAmount;
				result.setCount(resultAmount);
				
			// Extraction when mouse not holding a stack
			}else{
				result = this.itemHandler.extractItem(slotId - 36, extractAmount, false);
			}
			player.inventory.setItemStack(result);
			return result;
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int from){
		Slot fromSlot = this.inventorySlots.get(from);
		if(from < 36 && fromSlot.getHasStack()){
			// Move scroll into KitchenAppliance
			if(fromSlot.getStack().getItem() == Items.RECIPE_SCROLL && !this.getScrollSlot().getHasStack()){
				this.getScrollSlot().putStack(fromSlot.getStack());
				fromSlot.putStack(ItemStack.EMPTY);
			// Insert ingredient into KitchenAppliance
			}else{
				List<KitchenApplianceSlot> slots = this.inventorySlots.stream().filter(slot -> slot instanceof KitchenApplianceSlot).map(slot -> (KitchenApplianceSlot) slot).collect(Collectors.toList());
				for(int i = 0; i <= slots.size()-1; i ++){
					KitchenApplianceSlot slot = slots.get(i);
					if(slot.getRequest().isMatched(fromSlot.getStack())){
						ItemStack remainStack = slot.getItemHandler().insertItem(slot.getSlotIndex(), fromSlot.getStack(), false);
						fromSlot.putStack(remainStack);
						break;
					}
				}
			}
		}else if(playerIn.inventory.getFirstEmptyStack() != -1){
			int emptySlot = playerIn.inventory.getFirstEmptyStack();
			ItemStack extractStack = ItemStack.EMPTY;
			if(fromSlot instanceof KitchenApplianceSlot){
				extractStack = this.itemHandler.extractItem(fromSlot.getSlotIndex(), 64, false);
			}else if(from == 36){
				extractStack = this.itemHandler.extractItem(0, 1, false);
			}
			playerIn.inventory.setInventorySlotContents(emptySlot, extractStack);
		}
		this.updateSlot();
		return ItemStack.EMPTY;
	}

	public List<ItemStackRequest> getCurrentItemRequests(){
		StepRequest stepRequest = this.itemHandler != null ? this.itemHandler.getCurrentStepRequest() : null;
		if(stepRequest != null){
			return stepRequest.getRequires();
		}
		return new ArrayList<>();
	}

	protected Slot getScrollSlot(){
		return this.inventorySlots.get(36);
	}
}