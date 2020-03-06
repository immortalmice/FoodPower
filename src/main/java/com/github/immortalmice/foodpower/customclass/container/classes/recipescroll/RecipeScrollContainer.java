package com.github.immortalmice.foodpower.customclass.container.classes.recipescroll;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.lists.Containers;

public class RecipeScrollContainer extends ContainerBase{
	private final CompoundNBT nbt;

	public RecipeScrollContainer(int windowIdIn, PlayerInventory playerInventory, PacketBuffer extraData){
		this(windowIdIn, playerInventory, extraData.readCompoundTag());
	}
	public RecipeScrollContainer(int windowIdIn, PlayerInventory playerInventory, CompoundNBT nbtIn){
		super(Containers.ContainerTypes.RECIPE_SCROLL, windowIdIn, new int[]{-1, -1}, playerInventory);

		this.nbt = nbtIn;
	}

	public CompoundNBT getScrollTag(){
		return this.nbt;
	}
}