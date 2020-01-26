package com.github.immortalmice.foodpower.customclass.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.lists.GUIs;

public class ModContainer extends Container{
	protected ItemStackHandler items;
	protected EntityPlayer player;
	public ModContainer(EntityPlayer playerIn){
		super();
		this.player = playerIn;
		/** Player Inventory Slots */
		for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(this.player.inventory, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i){
            this.addSlotToContainer(new Slot(this.player.inventory, i, 8 + i * 18, 109));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn){
		return true;
	}
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        return null;
    }
    @Override
	public void onContainerClosed(EntityPlayer playerIn){
		super.onContainerClosed(playerIn);
		GUIs.containerLoadedList.remove(this);
	}
	public void onContainerClosed(){
		this.onContainerClosed(this.player);
	}
	public EntityPlayer getPlayer(){
		return this.player;
	}
}