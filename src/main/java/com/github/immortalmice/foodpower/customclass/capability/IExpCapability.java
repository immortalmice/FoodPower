package com.github.immortalmice.foodpower.customclass.capability;

import java.util.Map;

public interface IExpCapability{
	public String getFullPatternExpToString();
	public int getPatternExpInt(String patternNameIn);
	public Map<String, Integer> getPatternExp();
	public void setPatternExp(String patternName, int value);
}