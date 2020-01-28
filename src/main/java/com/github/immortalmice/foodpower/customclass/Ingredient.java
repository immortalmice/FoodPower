package com.github.immortalmice.foodpower.customclass;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFood;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class Ingredient extends ItemFoodBase{
	/** For Mod Ingredients */
	public Ingredient(String name, int amount, float saturation){
		super(name, amount, saturation, false);

        /** Add to ingredient list, and regist it later */
        Ingredients.list.add(this);
	}
	/** For Vanilla Ingredient is Food */
	public Ingredient(String nameIn, ItemFood itemIn){
		super(nameIn, itemIn.getHealAmount(ItemStack.EMPTY), itemIn.getSaturationModifier(ItemStack.EMPTY), false);

		/** Add to ingredient list, and regist it later */
		Ingredients.vanillaList.add(this);
	}
	/** For Vanilla Ingredient not Food */
	public Ingredient(String nameIn, Item itemIn){
		super(nameIn, 0, 0.0f, false);

		Ingredients.vanillaList.add(this);
	}
	/** For CookedFoods */
	public Ingredient(String nameIn){
		super(nameIn, 2, 0.4f, false);
	}
	/** For Empty */
	public Ingredient(){
		super("empty", 0, 0.0f, false);
	}
}