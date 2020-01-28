package com.github.immortalmice.foodpower.customclass;

import com.github.immortalmice.foodpower.customclass.Ingredient;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class CookedFood extends Ingredient{
	public CookedFood(String name){
		super(name);
		
		/** Add to ingredient list, and regist it later */
		Ingredients.cookedFoodList.add(this);
	}
}