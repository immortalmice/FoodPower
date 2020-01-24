package com.github.immortalmice.foodpower.customclass.gui.market;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.inventory.IContainerListener;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.Crops;

public class MarketContainer extends ModContainer{
	protected int index;
	protected SlotItemHandler emeraldSlot;
	public MarketContainer(EntityPlayer player){
		super(player);

		index = 0;
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
				thisItemStack.setCount(thisItemStack.getCount() - stack.getCount());
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
		int treeSize = Trees.saplingBushList.size();
		int cropSize = Crops.seedList.size();
		if(index < 0){
			index += treeSize + cropSize;
		}else if(index > treeSize + cropSize - 1){
			index -= treeSize + cropSize;
		}
		if(index >= 0 && index <= treeSize -1){
			return Item.getItemFromBlock(Trees.saplingBushList.get(index));
		}else if(index >= treeSize && index <= treeSize + cropSize -1){
			return Crops.seedList.get(index - treeSize);
		}else{
			return Items.AIR;
		}
	}
}