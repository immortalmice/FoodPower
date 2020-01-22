package com.github.immortalmice.foodpower.customclass.gui.market;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;

public class MarketContainer extends ModContainer{
	private ItemStackHandler items = new ItemStackHandler(2);
	public MarketContainer(EntityPlayer player){
		super(player);

		this.addSlotToContainer(new SlotItemHandler(items, 0, 89, 20));
		this.addSlotToContainer(new SlotItemHandler(items, 1, 137, 20));
	}
}