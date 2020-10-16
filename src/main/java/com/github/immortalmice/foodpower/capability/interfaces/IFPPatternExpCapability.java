package com.github.immortalmice.foodpower.capability.interfaces;

import java.util.Map;

import com.github.immortalmice.foodpower.cooking.CookingPattern;

/* Capability about FoodPower's Pattern Exp */
public interface IFPPatternExpCapability{

	int getExpLevel(CookingPattern pattern);
	Map<CookingPattern, Integer> getAllExpLevel();

	void setExpLevel(CookingPattern pattern, int level);

	/* You can override this to make your own conversion between level and value. Ex. 20 value for 1 level */
	/* Return how many value actually add, can pass in negative value, and return might be negative */
	int addExp(CookingPattern pattern, int value);
}