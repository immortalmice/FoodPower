package com.github.immortalmice.foodpower.customclass;

import com.github.immortalmice.foodpower.baseclass.FoodItemBase;
import com.github.immortalmice.foodpower.lists.CreativeTabList;
import com.github.immortalmice.foodpower.lists.IngredientList;

public class Ingredient extends FoodItemBase{
	public Ingredient(String name, int amount, float saturation){
		super(amount, saturation);

		this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(CreativeTabList.ingredientTab);

        IngredientList.list.add(this);
	}
}