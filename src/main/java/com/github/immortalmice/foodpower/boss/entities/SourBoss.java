package com.github.immortalmice.foodpower.boss.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SourBoss extends MobEntity {
	public SourBoss(EntityType<? extends SourBoss> type, World worldIn) {
		super(type, worldIn);
		
		this.registerGoals();
	}
	
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		
		this.goalSelector.addGoal(0, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	}
}
