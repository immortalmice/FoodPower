package com.github.immortalmice.foodpower.customclass.effect;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import com.github.immortalmice.foodpower.baseclass.EffectBase;

public class FoodEffect extends EffectBase{
	private static final String POSTFIX = "_power";
	private String ingredientName, fpName;
	public FoodEffect(String ingredientNameIn, int liquidColorIn){
		super(ingredientNameIn + FoodEffect.POSTFIX, EffectType.BENEFICIAL, liquidColorIn);

		this.ingredientName = ingredientNameIn;
		this.fpName = FoodEffect.getFPNameByIngredientName(this.ingredientName);
	}

	public EffectInstance getEffectInstance(int durationIn, int amplifierIn){
		return new EffectInstance(this, durationIn, amplifierIn, false, false);
	}

	public String getIngredientName(){
		return this.ingredientName;
	}

	public String getFPName(){
		return this.fpName;
	}

	public static String getFPNameByIngredientName(String str){
		return str + FoodEffect.POSTFIX;
	}
}