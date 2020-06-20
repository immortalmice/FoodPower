package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.item.Item;
import net.minecraft.item.Food;

import com.github.immortalmice.foodpower.baseclass.ItemBase;

public class ItemFoodBase extends ItemBase{
	public ItemFoodBase(String name, int hunger, float saturation, boolean isInCreativeTab){
		/* Can wolf eat my sweetie? NO WAY!*/
		super(name, new Item.Properties()
			.food(new Food.Builder().saturation(saturation).hunger(hunger).build()), isInCreativeTab);
	}

	public ItemFoodBase(String name, int hunger, float saturation){
		this(name, hunger, saturation, true);
	}
}