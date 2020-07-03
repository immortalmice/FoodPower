package com.github.immortalmice.foodpower.customclass.cooking;

import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;

public interface ICookingElement{
	enum ElementType{
		INGREDIENT,
		COOKED_FOOD,
		FOOD_TYPE
	}

	ElementType getElementType();

	default boolean isMatch(Ingredient ingredientIn){
		if(this instanceof Ingredient){
			return ingredientIn.isEqual((Ingredient) this);
		}else if(this instanceof FoodType){
			return ingredientIn.isType((FoodType) this);
		}
		return false;
	}

	default boolean isType(ElementType type){
		return this.getElementType() == type;
	}
}