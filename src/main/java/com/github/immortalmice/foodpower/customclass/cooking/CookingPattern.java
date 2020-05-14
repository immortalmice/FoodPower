package com.github.immortalmice.foodpower.customclass.cooking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.Ingredients;

/* Pattern means a collection of CookingStep in "cake", "salad", "fried rice"....etc */
public class CookingPattern{
	private List<CookingStep> steps;
	private CookedFood result;
	private String name;
	private List<Ingredient> ingredientList = new ArrayList<Ingredient>();

	public CookingPattern(String nameIn, CookedFood resultIn, CookingStep stepsIn[]){
		
		this.name = nameIn;
		this.result = resultIn;
		this.steps = new ArrayList<CookingStep>(Arrays.asList(stepsIn));

		this.init();

		CookingPatterns.list.add(this);
	}

	public CookingPattern(){
		this.name = "EMPTY PATTERN";
	}

	public String getName(){
		return this.name;
	}
	public boolean isNotEmpty(){
		return this.name != "EMPTY PATTERN";
	}
	
	/* Filter ingrients need to display on recipe table or not */
	private void init(){
		for(int i = 0; i <= steps.size()-1; i ++){
			List<ItemFoodBase> list = steps.get(i).getIngredients();
			for(ItemFoodBase itemFood : list){
				if(itemFood instanceof Ingredient){
					this.ingredientList.add((Ingredient)itemFood);
				}
			}
		}
		return;
	}

	public List<Ingredient> getIngredients(){
		return ingredientList;
	}

	public List<ItemFoodBase> getAllPossibleIngredients(){
		List<ItemFoodBase> returnList = new ArrayList<ItemFoodBase>();
		for(ItemFoodBase patternIngredient : this.getIngredients()){
			if(patternIngredient instanceof Ingredient && ((Ingredient)patternIngredient).isEmpty()){
				returnList.addAll(Ingredients.getIngredientsByType(((Ingredient)patternIngredient).getFoodType()));
			}else{
				returnList.add(patternIngredient);
			}
		}
		return returnList;
	}

	public CookedFood getResult(){
		return this.result;
	}
}