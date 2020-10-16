package com.github.immortalmice.foodpower.effect;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

// TODO Is this needed?
public class FoodEffect extends Effect{

	public FoodEffect(int liquidColorIn){
		super(EffectType.BENEFICIAL, liquidColorIn);
	}
}