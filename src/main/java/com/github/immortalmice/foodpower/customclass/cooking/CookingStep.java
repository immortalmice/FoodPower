package com.github.immortalmice.foodpower.customclass.cooking;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;

/* Use equipment, fill with ingredients and get result */
public class CookingStep{
	private KitchenAppliance equipment;
	private List<ItemFoodBase> ingredients = new ArrayList<ItemFoodBase>();
	private CookedFood result;

	public CookingStep(KitchenAppliance equipmentIn, CookedFood resultIn, ItemFoodBase ingredientsIn[]){
		this.equipment = equipmentIn;
		this.result = resultIn;
		this.addIngredientAll(ingredientsIn);
	}

	private void addIngredient(ItemFoodBase ingredientIn){
		this.ingredients.add(ingredientIn);
	}
	private void addIngredientAll(ItemFoodBase ingredientsIn[]){
		for(int i = 0; i <= ingredientsIn.length-1; i ++){
			this.addIngredient(ingredientsIn[i]);
		}
	}
	public List<ItemFoodBase> getIngredients(){
		return this.ingredients;
	}

	public KitchenAppliance getEquipment(){
		return this.equipment;
	}
	public CookedFood getResult(){
		return this.result;
	}
}