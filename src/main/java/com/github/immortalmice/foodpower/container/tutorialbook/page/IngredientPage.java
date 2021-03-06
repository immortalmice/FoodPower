package com.github.immortalmice.foodpower.container.tutorialbook.page;

import com.github.immortalmice.foodpower.food.Ingredient;

public class IngredientPage extends AbstractLayoutPage {
	private final Ingredient ingredient;
	
	public IngredientPage(Ingredient ingredientIn) {
		super(ingredientIn.asItem().getTranslationKey());
		
		this.ingredient = ingredientIn;
	}
}
