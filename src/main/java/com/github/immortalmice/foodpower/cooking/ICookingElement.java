package com.github.immortalmice.foodpower.cooking;

import com.github.immortalmice.foodpower.food.Ingredient;
import com.github.immortalmice.foodpower.types.FoodType;

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