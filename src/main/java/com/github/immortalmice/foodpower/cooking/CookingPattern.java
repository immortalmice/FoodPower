package com.github.immortalmice.foodpower.cooking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.immortalmice.foodpower.food.Meal;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.specialclass.KitchenAppliance;

/* Pattern means a collection of CookingStep in "cake", "salad", "fried rice"....etc */
public class CookingPattern{
	private final List<CookingStep> steps;
	private final Meal result;
	private final String name;
	private final List<ICookingElement> elementList = new ArrayList<ICookingElement>();

	public CookingPattern(String nameIn, Meal resultIn, CookingStep stepsIn[]){
		
		this.name = nameIn;
		this.result = resultIn;
		this.steps = Arrays.asList(stepsIn);

		this.init();

		CookingPatterns.list.add(this);
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

	public List<ICookingElement> getRootElements(){
		List<ICookingElement> element = new ArrayList<ICookingElement>();
		for(int i = 0; i <= this.elementList.size()-1; i ++){
			if(this.elementList.get(i).isType(ICookingElement.ElementType.INGREDIENT)
				|| this.elementList.get(i).isType(ICookingElement.ElementType.FOOD_TYPE)){

				element.add(this.elementList.get(i));
			}
		}
		return element;
	}

	public List<CookingStep> getSteps(KitchenAppliance kitchenAppliance){
		List<CookingStep> matchSteps = new ArrayList<CookingStep>();
		for(int i = 0; i <= this.steps.size()-1; i ++){
			if(this.steps.get(i).getEquipment() == kitchenAppliance){
				matchSteps.add(this.steps.get(i));
			}
		}
		return matchSteps;
	}

	public List<CookingStep> getSteps(){
		return new ArrayList<>(this.steps);
	}

	public Meal getResult(){
		return this.result;
	}
}