package com.github.immortalmice.foodpower.customclass.cooking;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;

/* Use equipment, fill with ingredients and get result */
public class CookingStep{
	private KitchenAppliance equipment;
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	private Ingredient result;

	public CookingStep(KitchenAppliance equipmentIn, Ingredient resultIn, Ingredient ingredientsIn[]){
		this.equipment = equipmentIn;
		this.result = resultIn;
		this.addIngredientAll(ingredientsIn);
	}

	private void addIngredient(Ingredient ingredientIn){
		this.ingredients.add(ingredientIn);
	}
	private void addIngredientAll(Ingredient ingredientsIn[]){
		for(int i = 0; i <= ingredientsIn.length-1; i ++){
			this.addIngredient(ingredientsIn[i]);
		}
	}
	public List<Ingredient> getIngredients(){
		return this.ingredients;
	}

	public KitchenAppliance getEquipment(){
		return this.equipment;
	}
	public Ingredient getResult(){
		return this.result;
	}
}