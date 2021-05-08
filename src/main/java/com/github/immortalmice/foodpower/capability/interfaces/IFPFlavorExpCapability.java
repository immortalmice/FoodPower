package com.github.immortalmice.foodpower.capability.interfaces;

import java.util.Map;

import com.github.immortalmice.foodpower.types.FlavorType;

/* Capability about FoodPower's Flavor Exp */
public interface IFPFlavorExpCapability{

	int getExpLevel(FlavorType flavor);
	Map<FlavorType, Integer> getAllExpLevel();

	void setExpLevel(FlavorType flavor, int level);

	/* You can override this to make your own conversion between level and value. Ex. 20 value for 1 level */
	/* Return how many value actually add, can pass in negative value, and return might be negative */
	int addExp(FlavorType flavor, int value);
	
	// markDirty to mark this exp is compatible with buff on player.
	void markDirty(boolean dirty);
	boolean isDirty();
}