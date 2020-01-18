package com.github.immortalmice.foodpower.customclass;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class Ingredient extends ItemFoodBase{
	public Ingredient(String name, int amount, float saturation){
		super(name, amount, saturation, false);

        /** Add to ingredient list, and regist it later */
        Ingredients.list.add(this);
	}
}