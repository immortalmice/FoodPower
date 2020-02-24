package com.github.immortalmice.foodpower.customclass.food;

import com.github.immortalmice.foodpower.lists.FoodTypes;

public class FoodType{
	private String name;

	public FoodType(String nameIn){
		this.name = nameIn;

		FoodTypes.list.add(this);
	}

	public boolean isEqual(FoodType ftIn){
		if(ftIn.getName() == this.name){
			return true;
		}
		return false;
	}

	public String getName(){
		return name;
	}
}