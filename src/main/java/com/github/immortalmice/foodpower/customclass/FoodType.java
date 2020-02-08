package com.github.immortalmice.foodpower.customclass;

import com.github.immortalmice.foodpower.lists.FoodTypes;

public class FoodType{
	private int index;
	private String name;

	public FoodType(String nameIn){
		this.name = nameIn;
		this.index = FoodTypes.list.size();

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