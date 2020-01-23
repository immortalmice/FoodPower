package com.github.immortalmice.foodpower.customclass.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ModContainer extends Container{
	protected ItemStackHandler items;
	public ModContainer(EntityPlayer player){
		super();

		/** Player Inventory Slots */
		for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i){
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 109));
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
}