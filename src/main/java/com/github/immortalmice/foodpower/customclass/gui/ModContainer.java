package com.github.immortalmice.foodpower.customclass.gui;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.lists.GUIs;

public class ModContainer extends Container{
	protected EntityPlayer player;
	/** Offset is used in setting slot position */
	public ModContainer(EntityPlayer playerIn, int[] offset){
		super();
		this.player = playerIn;
		/** Player Inventory Slots */
		if(offset[0] == -1 && offset[1] == -1)
			return;
		int slotSize = 18;
		for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(this.player.inventory, j + i * 9 + 9, offset[0] + j * 18, offset[1] + i * slotSize));
            }
        }
        for (int i = 0; i < 9; ++i){
            this.addSlotToContainer(new Slot(this.player.inventory, i, offset[0] + i * 18, offset[1] + 3 * slotSize + 6));
        }
	}
	public ModContainer(EntityPlayer playerIn, World worldIn, BlockPos posIn){
		this(playerIn, new int[]{-1, -1});
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