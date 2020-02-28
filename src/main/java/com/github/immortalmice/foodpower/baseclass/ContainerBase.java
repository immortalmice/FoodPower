package com.github.immortalmice.foodpower.baseclass;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.lists.Containers;

public class ContainerBase extends Container{
	/* For Client Call */
	public ContainerBase(int windowId, PlayerInventory inv){
		this(Containers.BASE.getContainerType(), windowId, new int[]{8, 51}, inv);
	}
	/* Offset is used in setting slot position */
	public ContainerBase(@Nullable ContainerType<?> type, int id, int[] offset, PlayerInventory inventoryIn){
		super(type, id);
		/* Player Inventory Slots */
		if(offset[0] == -1 && offset[1] == -1)
			return;
		int slotSize = 18;
		for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlot(new Slot(inventoryIn, j + i * 9 + 9, offset[0] + j * 18, offset[1] + i * slotSize));
            }
        }
        for (int i = 0; i < 9; ++i){
            this.addSlot(new Slot(inventoryIn, i, offset[0] + i * 18, offset[1] + 3 * slotSize + 6));
        }
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn){
		return true;
	}
	@Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index){
        return null;
    }

    /* Will be override in child class to spicify screen to regist */
    @OnlyIn(Dist.CLIENT)
    public static void registScreen(ContainerType<? extends ContainerBase> containerTypeIn){
    	ScreenManager.registerFactory(containerTypeIn, ScreenBase::new);
    }
}