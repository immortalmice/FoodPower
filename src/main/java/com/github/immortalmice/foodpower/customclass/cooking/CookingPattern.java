package com.github.immortalmice.foodpower.customclass.cooking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.Ingredients;

/* Pattern means a collection of CookingStep in "cake", "salad", "fried rice"....etc */
public class CookingPattern{
	private List<CookingStep> steps;
	private Meal result;
	private String name;
	private List<ICookingElement> elementList = new ArrayList<ICookingElement>();

	public CookingPattern(String nameIn, Meal resultIn, CookingStep stepsIn[]){
		
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
			List<ICookingElement> list = steps.get(i).getElements();
			for(ICookingElement element : list){
				this.elementList.add(element);
			}
		}
		return;
	}

	public List<ICookingElement> getElements(){
		List<ICookingElement> element = new ArrayList<ICookingElement>();
		for(int i = 0; i <= this.elementList.size()-1; i ++){
			if(this.elementList.get(i).isType(ICookingElement.ElementType.INGREDIENT)
				|| this.elementList.get(i).isType(ICookingElement.ElementType.FOOD_TYPE)){

				element.add(this.elementList.get(i));
			}
		}
		return element;
	}

	public List<ICookingElement> getAllPossibleIngredients(){
		List<ICookingElement> returnList = new ArrayList<ICookingElement>();
		for(ICookingElement patternElement : this.getElements()){
			if(patternElement instanceof FoodType){
				returnList.addAll(Ingredients.getIngredientsByType((FoodType)patternElement));
			}else if(patternElement instanceof Ingredient){
				returnList.add(patternElement);
			}
		}
		return returnList;
	}

	public Meal getResult(){
		return this.result;
	}
}