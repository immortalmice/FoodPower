package com.github.immortalmice.foodpower.customclass.food;

import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class Meal extends CookedFood{
	/* For a empty Meal */
	public Meal(String nameIn){
		super(nameIn);

		Ingredients.mealFoodList.add(this);
	}
}