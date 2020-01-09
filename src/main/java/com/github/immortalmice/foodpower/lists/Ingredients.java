package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.Ingredient;

/** All the ingredient need to be registed will list below. */
public class Ingredients{
	public static final List<Ingredient> list = new ArrayList<Ingredient>();

	public static final Ingredient BUTTER = new Ingredient("butter", 1, 0.1f);
}