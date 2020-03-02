package com.github.immortalmice.foodpower.customclass.container.classes.recipescroll;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.lists.Containers;

public class RecipeScrollContainer extends ContainerBase{
	
	private static final ContainerType<RecipeScrollContainer> TYPE = Containers.RECIPE_SCROLL.getContainerType();
	private final CompoundNBT nbt;

	public RecipeScrollContainer(int windowIdIn, PlayerInventory playerInventory, PacketBuffer extraData){
		this(windowIdIn, playerInventory, extraData.readCompoundTag());
	}
	public RecipeScrollContainer(int windowIdIn, PlayerInventory playerInventory, CompoundNBT nbtIn){
		super(RecipeScrollContainer.TYPE, windowIdIn, new int[]{-1, -1}, playerInventory);

		this.nbt = nbtIn;
	}

	public CompoundNBT getScrollTag(){
		return this.nbt;
	}
}