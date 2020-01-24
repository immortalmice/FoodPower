package com.github.immortalmice.foodpower.customclass.gui.market;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.MarketTileEntity;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.Crops;

public class MarketContainer extends ModContainer{
	protected World world; 
	protected BlockPos pos;
	protected SlotItemHandler emeraldSlot;
	public MarketContainer(EntityPlayer playerIn, World worldIn, BlockPos posIn){
		super(playerIn);

		world = worldIn;
		pos = posIn;

		items = new ItemStackHandler(2);
		this.addSlotToContainer(emeraldSlot = new SlotItemHandler(items, 0, 89, 20){
			@Override
			public boolean isItemValid(ItemStack stack){
				return stack != null && stack.getItem() == Items.EMERALD && super.isItemValid(stack);
			}
			@Override
			public void onSlotChanged(){
				ItemStack stack = this.getStack();
				MarketContainer.this.items.setStackInSlot(1, ItemStack.EMPTY);
				if(!stack.isEmpty()){
					MarketContainer.this.items.setStackInSlot(1, new ItemStack(getItem()));
				}
			}
		});
		this.addSlotToContainer(new SlotItemHandler(items, 1, 137, 20){
			@Override
			public boolean isItemValid(ItemStack stack){
				return false;
			}
			@Override
			public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack){
				ItemStack thisItemStack = MarketContainer.this.items.getStackInSlot(0);
				thisItemStack.setCount(thisItemStack.getCount() - 1);
				emeraldSlot.onSlotChanged();
				return stack;
			}
		});
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn){
		super.onContainerClosed(playerIn);
		if (playerIn.isServerWorld()){
			if(playerIn.inventory.getFirstEmptyStack() != -1){
				playerIn.inventory.addItemStackToInventory(items.getStackInSlot(0));
			}else{
				playerIn.dropItem(items.getStackInSlot(0), false);
			}
		}
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int fromSlot){
		ItemStack previous = null;
		System.out.println(fromSlot);
		System.out.println(inventorySlots.size());
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()){
			ItemStack current = slot.getStack();
			previous = current.copy();
			// Custom behaviour //
			if (fromSlot < 36){
				// From TE Inventory to Player Inventory
				if (!this.mergeItemStack(current, 36, 38, true))
				    return null;
			}else if(fromSlot == 37){
				Slot emerald = this.inventorySlots.get(37);
				if(emerald != null && slot.getHasStack()){
					int amount = emerald.getStack().getCount();
					current.setCount(amount);
					if(!this.mergeItemStack(current, 0, 36, false))
						return null;
				}
			}else{
					// From Player Inventory to TE Inventory
				if (!this.mergeItemStack(current, 0, 36, false))
				    return null;
			}
			// Custom behaviour //
			if (current.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (current.getCount() == previous.getCount())
				return null;
			slot.onTake(entityPlayer, current);
		}
		return previous;
	}
/*
	@SideOnly(Side.SERVER)
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(IContainerListener i : this.listeners){
			i.sendWindowProperty(this, 0, this.index);
		}
	}
	@SideOnly(Side.CLIENT)
	@Override
    public void updateProgressBar(int id, int data){
    	super.updateProgressBar(id, data);
    	switch(id){
    		case 0:
    			this.index = data;
    	}
    }
*/
	public Item getItem(){
		TileEntity tile = this.world.getTileEntity(pos);
		int index = 0;
		if(tile instanceof MarketTileEntity){
			index = ((MarketTileEntity)tile).getIndex();
		}

		int treeSize = Trees.saplingBushList.size();
		int cropSize = Crops.seedList.size();

		if(index >= 0 && index <= treeSize -1){
			return Item.getItemFromBlock(Trees.saplingBushList.get(index));
		}else if(index >= treeSize && index <= treeSize + cropSize -1){
			return Crops.seedList.get(index - treeSize);
		}else{
			return Items.AIR;
		}
	}
}