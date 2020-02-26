package com.github.immortalmice.foodpower.customclass.food;

import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class Meal extends CookedFood{
	public Meal(String nameIn){
		super(nameIn);

		Ingredients.mealFoodList.add(this);
	}
}