package com.github.immortalmice.foodpower.boss.entities;

import com.github.immortalmice.foodpower.baseclass.BossBase;
import com.github.immortalmice.foodpower.entity.VenomEntity;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;

public class SourBoss extends BossBase implements IRangedAttackMob {
	public SourBoss(EntityType<? extends SourBoss> type, World worldIn) {
		super(type, worldIn, BossInfo.Color.GREEN);
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
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(4, new RangedAttackGoal(this, 1.0d, 20, 20));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, PlayerEntity.class, 6.0f, 1.0d, 1.2d));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
	}

	@Override
	public EntitySize getSize(Pose poseIn) {
		return EntitySize.fixed(1.5f, 1.0f);
	}

	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		VenomEntity venom = new VenomEntity(this);
		double d0 = target.getPosX() - this.getPosX();
		double d1 = target.getPosYHeight(0.3333333333333333D) - venom.getPosY();
		double d2 = target.getPosZ() - this.getPosZ();
		double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
		venom.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
		this.world.addEntity(venom);
	}
}
