package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.item.Item;
import net.minecraft.item.Food;

import com.github.immortalmice.foodpower.baseclass.ItemBase;

public class ItemFoodBase extends ItemBase{
	public ItemFoodBase(int hunger, float saturation, boolean isInCreativeTab){
		/* Can wolf eat my sweetie? NO WAY!*/
		super(new Item.Properties()
			.food(new Food.Builder().saturation(saturation).hunger(hunger).build()), isInCreativeTab);
	}

	public ItemFoodBase(int hunger, float saturation){
		this(hunger, saturation, true);
	}
}