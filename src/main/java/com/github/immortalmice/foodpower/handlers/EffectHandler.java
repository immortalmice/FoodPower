package com.github.immortalmice.foodpower.handlers;

import java.util.List;

import com.github.immortalmice.foodpower.customclass.effect.FoodEffect;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.Effects;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class EffectHandler{
	public static void setup(){
		List<Ingredient> list = Ingredients.getIngredientList();
		if(list.isEmpty()){
			System.out.println("[Warning] Ingredient list is empty!");
			return;
		}
		for(Ingredient ingredient : list){
			FoodEffect effect = Effects.getFoodEffectByIngredient(ingredient);

			if(effect != null){
				ingredient.setEffect(effect);
			}else{
				System.out.println("[Warning] Ingredient: " + ingredient.getFPName() + " can't find correspond Effect!");
			}
		}
	}
}