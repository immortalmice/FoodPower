package com.github.immortalmice.foodpower.customclass.capability.interfaces;

import java.util.Map;

import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;

public interface IFPPatternExpCapability{

	int getPatternExpLevel(CookingPattern pattern);
	Map<CookingPattern, Integer> getAllPatternExpLevel();

	void setPatternExpLevel(CookingPattern pattern, int level);

	/* You can override this to make your own conversion between level and value. Ex. 20 value for 1 level */
	/* Return how many value actually add, can pass in negative value, and return might be negative */
	int addPatternExp(CookingPattern pattern, int value);
}