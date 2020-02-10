package com.github.immortalmice.foodpower.customclass.cooking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.immortalmice.foodpower.customclass.cooking.CookingStep;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

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

	public String getName(){
		return this.name;
	}
	/* Filter ingrients need to display on recipe table or not */
	public void init(){
		for(int i = 0; i <= steps.size()-1; i ++){
			ingredientList.addAll(steps.get(i).getIngredients().stream()
				.filter((Ingredient a) -> !(a instanceof CookedFood))
				.collect(Collectors.toList()));
		}
		return;
	}

	public List<Ingredient> getIngredients(){
		return ingredientList;
	}
}