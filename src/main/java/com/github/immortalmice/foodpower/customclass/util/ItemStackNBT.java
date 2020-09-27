package com.github.immortalmice.foodpower.customclass.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ItemStackNBT{
	public static final String INT_COUNT_KEY = "int_count";

	public static ItemStack read(CompoundNBT nbt){
		ItemStack stack = ItemStack.read(nbt);
		stack.setCount(nbt.getInt(ItemStackNBT.INT_COUNT_KEY));
		return stack;
	}

	public static CompoundNBT write(ItemStack stack){
		CompoundNBT nbt = stack.write(new CompoundNBT());
		nbt.putInt(ItemStackNBT.INT_COUNT_KEY, stack.getCount());
		return nbt;
	}
}