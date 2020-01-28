package com.github.immortalmice.foodpower.customclass.cooking;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.Ingredient;

public class CookingStep{
	private KitchenAppliance equipment;
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	private Ingredient result;

	public CookingStep(KitchenAppliance equipmentIn, Ingredient resultIn, Ingredient ingredientsIn[]){
		this.equipment = equipmentIn;
		this.result = resultIn;
		this.AddIngredientAll(ingredientsIn);
	}

	private void AddIngredient(Ingredient ingredientIn){
		this.ingredients.add(ingredientIn);
	}
	private void AddIngredientAll(Ingredient ingredientsIn[]){
		for(int i = 0; i <= ingredientsIn.length-1; i ++){
			this.AddIngredient(ingredientsIn[i]);
		}
	}
}