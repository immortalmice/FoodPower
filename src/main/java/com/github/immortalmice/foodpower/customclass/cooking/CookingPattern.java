package com.github.immortalmice.foodpower.customclass.cooking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.cooking.CookingStep;
import com.github.immortalmice.foodpower.customclass.CookedFood;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class CookingPattern{
	private List<CookingStep> steps;
	private CookedFood result;

	public CookingPattern(CookedFood resultIn, CookingStep stepsIn[]){
		this.result = resultIn;
		this.steps = new ArrayList<CookingStep>(Arrays.asList(stepsIn));

		CookingPatterns.list.add(this);
	}

}