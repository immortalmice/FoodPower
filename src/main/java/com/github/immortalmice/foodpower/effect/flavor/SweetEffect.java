package com.github.immortalmice.foodpower.effect.flavor;

import com.github.immortalmice.foodpower.effect.FlavorEffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.ServerPlayerEntity;

public class SweetEffect extends FlavorEffect {
	public SweetEffect() {
		super();
		this.addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "3b12a111-d368-462f-a290-40120be0c089", 1.0, AttributeModifier.Operation.MULTIPLY_BASE);
	}
	
	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
		if(amplifier >= 5 && entityLivingBaseIn instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) entityLivingBaseIn;
			player.abilities.allowFlying = true;
			player.abilities.setFlySpeed(0.2f);
			player.sendPlayerAbilities();
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		if(amplifier == 5) {
			return duration % 20 == 0;
		}
		return false;
	}
	
	@Override
	public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
		return modifier.getAmount() * (double)(amplifier * 0.5 + 1);
	}
	
	@Override
	public void onRemoveEffect(Entity entityLivingBaseIn, int amplifier) {
		if(amplifier >= 5 && entityLivingBaseIn instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) entityLivingBaseIn;
			player.abilities.allowFlying = false;
			player.abilities.setFlySpeed(0.05f);
			player.sendPlayerAbilities();
		}
	}
}
