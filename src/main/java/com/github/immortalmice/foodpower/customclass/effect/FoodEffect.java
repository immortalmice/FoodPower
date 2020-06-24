package com.github.immortalmice.foodpower.customclass.effect;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class FoodEffect extends Effect{

	public FoodEffect(int liquidColorIn){
		super(EffectType.BENEFICIAL, liquidColorIn);
	}
}