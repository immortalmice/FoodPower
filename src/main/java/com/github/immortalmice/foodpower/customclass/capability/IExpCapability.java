package com.github.immortalmice.foodpower.customclass.capability;

import java.util.Map;

import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;

public interface IExpCapability{

	int getPatternExpLevel(CookingPattern patternNameIn);
	Map<CookingPattern, Integer> getAllPatternExpLevel();

	void setPatternExpLevel(CookingPattern patternName, int level);
}