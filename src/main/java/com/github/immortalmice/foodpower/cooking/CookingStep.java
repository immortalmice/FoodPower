package com.github.immortalmice.foodpower.cooking;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.food.CookedFood;
import com.github.immortalmice.foodpower.specialclass.KitchenAppliance;

/* Use equipment, fill with ingredients and get result */
public class CookingStep{
	private final KitchenAppliance equipment;
	private final List<ICookingElement> elements = new ArrayList<ICookingElement>();
	private final CookedFood result;

	public CookingStep(KitchenAppliance equipmentIn, CookedFood resultIn, ICookingElement elementsIn[]){
		this.equipment = equipmentIn;
		this.result = resultIn;
		this.addElementAll(elementsIn);
	}

	private void addElement(ICookingElement elementIn){
		this.elements.add(elementIn);
	}
	private void addElementAll(ICookingElement elementsIn[]){
		for(int i = 0; i <= elementsIn.length-1; i ++){
			this.addElement(elementsIn[i]);
		}
	}
	public List<ICookingElement> getElements(){
		return new ArrayList<>(this.elements);
	}

	public KitchenAppliance getEquipment(){
		return this.equipment;
	}
	public CookedFood getResult(){
		return this.result;
	}
}