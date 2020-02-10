package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.food.FoodType;

public class FoodTypes{
	public static final List<FoodType> list = new ArrayList<FoodType>();

	public static final FoodType NONE = new FoodType("none");

	public static final FoodType FRUIT = new FoodType("fruit");
	public static final FoodType MEAT = new FoodType("meat");
	public static final FoodType VEGETABLE = new FoodType("vegetable");
	public static final FoodType SWEET = new FoodType("sweet");
	public static final FoodType SEASONING = new FoodType("seasoning");
}