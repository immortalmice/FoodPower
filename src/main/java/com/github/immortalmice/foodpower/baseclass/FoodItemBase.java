package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.item.ItemFood;

public class FoodItemBase extends ItemFood{
	public FoodItemBase(int amount, float saturation){
		super(amount, saturation, false);
	}
}