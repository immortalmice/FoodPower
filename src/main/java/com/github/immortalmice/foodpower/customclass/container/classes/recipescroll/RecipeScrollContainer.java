package com.github.immortalmice.foodpower.customclass.container.classes.recipescroll;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntReferenceHolder;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.lists.Containers;

public class RecipeScrollContainer extends ContainerBase{
	private final CompoundNBT nbt;

	public RecipeScrollContainer(int windowIdIn, PlayerInventory playerInventory, PacketBuffer extraData){
		this(windowIdIn, playerInventory, extraData.readCompoundTag());
	}
	public RecipeScrollContainer(int windowIdIn, PlayerInventory playerInventory, CompoundNBT nbtIn){
		super(Containers.ContainerTypes.RECIPE_SCROLL, windowIdIn, new int[]{-1, -1}, playerInventory);

		this.nbt = nbtIn;

		/* nbt output_amount be dirty when server end change it  */
		this.trackInt(new IntReferenceHolder(){
			@Override
			public int get(){
				return RecipeScrollContainer.this.getAmount();
			}
			@Override
			public void set(int value){
				RecipeScrollContainer.this.setAmount(value);
			}
		});
	}

	public CompoundNBT getScrollTag(){
		return this.nbt;
	}

	public String getScrollName(){
		return nbt.contains("displayName") && !nbt.getString("displayName").isEmpty()
			? nbt.getString("displayName")
			: I18n.format("general.foodpower.unknown_recipe");
	}

	public int getAmount(){
		return nbt.contains("output_amount") ? nbt.getInt("output_amount") : 1;
	}

	public void setAmount(int amountIn){
		this.nbt.putInt("output_amount", amountIn);
		RecipeScroll.calcuAllAmount(this.nbt);
	}

	public int getWindowId(){
		return this.windowId;
	}
}