package com.github.immortalmice.foodpower.customclass.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.customclass.gui.GuiData;
import com.github.immortalmice.foodpower.lists.GUIs;

public class ModContainer extends Container{
	private GuiData data;
	private ItemStackHandler items;
	public ModContainer(int idIn, EntityPlayer player){
		super();
		data = GUIs.list.get(idIn);

		/** GUI Slots */
		items = new ItemStackHandler(data.getSlotCount());
		for(int i = 0; i <= data.getSlotCount()-1; i ++){
			this.addSlotToContainer(new SlotItemHandler(items, i, data.getSlotPos().get(i)[0], data.getSlotPos().get(i)[1]));
		}

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

	public GuiData getData(){
		return data;
	}
}