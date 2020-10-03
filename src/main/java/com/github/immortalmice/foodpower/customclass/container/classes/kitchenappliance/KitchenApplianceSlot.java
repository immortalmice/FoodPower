package com.github.immortalmice.foodpower.customclass.container.classes.kitchenappliance;

import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe.ItemStackRequest;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.KitchenApplianceTileEntity.KitchenApplanceItemHandler;

import net.minecraftforge.items.SlotItemHandler;

/* Custom Slot, Design for Slots in Kitchen Appliance */
public class KitchenApplianceSlot extends SlotItemHandler{
	private final ItemStackRequest request;

	public KitchenApplianceSlot(KitchenApplanceItemHandler itemHandler, int index, int xPosition, int yPosition){
		this(itemHandler, index, xPosition, yPosition, ItemStackRequest.EMPTY);
	}

	public KitchenApplianceSlot(KitchenApplanceItemHandler itemHandler, int index, int xPosition, int yPosition, ItemStackRequest requestIn){
		super(itemHandler, index, xPosition, yPosition);
		this.request = requestIn;
	}

	public boolean isSatisfied(){
		return this.request.isSatisfied(((KitchenApplanceItemHandler)this.getItemHandler()).getRealStack(this.getSlotIndex()));
	}

	public ItemStackRequest getRequest(){
		return request;
	}
}