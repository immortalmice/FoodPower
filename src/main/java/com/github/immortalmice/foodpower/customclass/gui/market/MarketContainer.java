package com.github.immortalmice.foodpower.customclass.gui.market;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.lists.Trees;

public class MarketContainer extends ModContainer{
	private SlotItemHandler emeraldSlot;
	public MarketContainer(EntityPlayer player){
		super(player);
		items = new ItemStackHandler(2);
		this.addSlotToContainer(emeraldSlot = new SlotItemHandler(items, 0, 89, 20){
			@Override
			public boolean isItemValid(ItemStack stack){
				return stack != null && stack.getItem() == Items.EMERALD && super.isItemValid(stack);
			}
			@Override
			public void onSlotChanged(){
				ItemStack stack = this.getStack();
				if(!stack.isEmpty()){
					MarketContainer.this.items.setStackInSlot(1, new ItemStack(Trees.ORANGE));
				}else{
					MarketContainer.this.items.setStackInSlot(1, ItemStack.EMPTY);
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
}