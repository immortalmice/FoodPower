package com.github.immortalmice.foodpower.effect;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class FoodEffect extends Effect{

	public FoodEffect(int liquidColorIn){
		super(EffectType.BENEFICIAL, liquidColorIn);
	}
	
	public FoodEffect(int liquidColorIn, IAttribute attributeIn, int amountIn, Operation operationIn){
		this(liquidColorIn);
		
		this.addAttributesModifier(attributeIn, UUID.randomUUID().toString(), amountIn, operationIn);
	}
}