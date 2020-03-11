package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EffectBase extends Effect{
	private String fpName;
	public EffectBase(String nameIn, EffectType typeIn, int liquidColorIn){
		super(typeIn, liquidColorIn);

		this.fpName = nameIn;
	}

	public String getFPName(){
		return this.fpName;
	}
}