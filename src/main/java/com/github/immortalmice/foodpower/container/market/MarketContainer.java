package com.github.immortalmice.foodpower.container.market;

import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.inventory.container.Slot;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.Crops;

public class MarketContainer extends ContainerBase{

	protected ItemStackHandler items;
	protected SlotItemHandler emeraldSlot;

	private static final int TREE_LIST_SIZE = Trees.saplingBushList.size();
	private static final int CROP_LIST_SIZE = Crops.itemList.size();
	private static final int FULL_LIST_SIZE = MarketContainer.TREE_LIST_SIZE + MarketContainer.CROP_LIST_SIZE;

	private final int windowId;
	/* index means current page in container */
	private int index = 0;

	public MarketContainer(int windowIdIn, PlayerInventory playerInventory, PacketBuffer extraData){
		this(windowIdIn, playerInventory);
	}

	public MarketContainer(int windowIdIn, PlayerInventory playerInventory){
		super(Containers.ContainerTypes.MARKET, windowIdIn, new int[]{45, 100}, playerInventory);

		this.windowId = windowIdIn;
		items = new ItemStackHandler(2);
		this.addSlot(emeraldSlot = new SlotItemHandler(items, 0, 143, 36){
			/* Can trade with nether star? Hey, I only want EMERALD */
			@Override
			public boolean isItemValid(ItemStack stack){
				return stack != null 
					&& stack.getItem() == Items.EMERALD 
					&& super.isItemValid(stack);
			}
			@Override
			public void onSlotChanged(){
				MarketContainer.this.refreshGood();
			}
		});
		this.addSlot(new SlotItemHandler(items, 1, 189, 36){
			@Override
			public boolean isItemValid(ItemStack stack){
				return false;
			}
			/* One Emerald, One Sapling */
			@Override
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack){
				ItemStack emeraldItemStack = MarketContainer.this.items.getStackInSlot(0);
				emeraldItemStack.setCount(emeraldItemStack.getCount() - 1);
				MarketContainer.this.refreshGood();
				return stack;
			}
		});

		this.trackInt(new IntReferenceHolder(){
			@Override
			public int get(){
				return MarketContainer.this.index;
			}
			@Override
			public void set(int value){
				MarketContainer.this.index = value;
				MarketContainer.this.refreshGood();
			}
		});
	}

	/* No this override, market will be conscienceless, you can try :) */
	@Override
	public void onContainerClosed(PlayerEntity playerIn){
		super.onContainerClosed(playerIn);
		if (playerIn.isServerWorld()){
			if(playerIn.inventory.getFirstEmptyStack() != -1){
				playerIn.inventory.addItemStackToInventory(items.getStackInSlot(0));
			}else{
				playerIn.dropItem(items.getStackInSlot(0), false);
			}
		}
	}
	/* Handle shift click */
	@Override
	public ItemStack transferStackInSlot(PlayerEntity entityPlayer, int fromSlot){
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(fromSlot);

		if(slot != null && slot.getHasStack() && !slot.getStack().isEmpty()){
			ItemStack current = slot.getStack();
			previous = current.copy();
			// Custom behavior
			if(fromSlot < 36){
				// From Player Inventory to Container Inventory
				if(!this.mergeItemStack(current, 36, 38, true))
				    return ItemStack.EMPTY;
			}else if(fromSlot == 37){
				Slot emerald = this.inventorySlots.get(37);
				if(emerald != null && slot.getHasStack()){
					int amount = emerald.getStack().getCount();
					current.setCount(amount);
					if(!this.mergeItemStack(current, 0, 36, false))
						return ItemStack.EMPTY;
				}
			}else{
				// From Container Inventory to Player Inventory
				if(!this.mergeItemStack(current, 0, 36, false))
				    return ItemStack.EMPTY;
			}
			// Custom behavior
			if (current.isEmpty() && current.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (current.getCount() == previous.getCount())
				return ItemStack.EMPTY;
			slot.onTake(entityPlayer, current);
		}
		return previous;
	}
	/* Increase and cycle index */
	public void increaseIndex(){
		this.index++;
		if(this.index > MarketContainer.FULL_LIST_SIZE - 1){
			this.index -= MarketContainer.FULL_LIST_SIZE;
		}
		this.refreshGood();
	}
	/* Decrease and cycle index */
	public void decreaseIndex(){
		this.index--;
		if(this.index < 0){
			this.index += MarketContainer.FULL_LIST_SIZE;
		}
		this.refreshGood();
	}

	/* Get Index Form NBT, And Return The Item */
	public Item getItem(){
		if(this.index >= 0 && this.index <= MarketContainer.TREE_LIST_SIZE -1){
			return Trees.saplingBushList.get(this.index).asItem();

		}else if(this.index >= MarketContainer.TREE_LIST_SIZE 
			&& this.index <= MarketContainer.FULL_LIST_SIZE -1){

			return Crops.itemList.get(this.index - MarketContainer.TREE_LIST_SIZE);
		}else{
			return Items.AIR;
		}
	}
	/* No Emerald, No Sapling, Of Course No Seed Too */
	public void refreshGood(){
		ItemStack stack = emeraldSlot.getStack();
		if(!stack.isEmpty()){
			MarketContainer.this.items.setStackInSlot(1, new ItemStack(getItem()));
		}else{
			MarketContainer.this.items.setStackInSlot(1, ItemStack.EMPTY);
		}
	}

	public int getWindowId(){
		return this.windowId;
	}
}