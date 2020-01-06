package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.Ingredient;

public class IngredientList{
	public static final List<Ingredient> list = new ArrayList<Ingredient>();

	public static final Ingredient butter = new Ingredient("butter", 5, 0.3f);
}