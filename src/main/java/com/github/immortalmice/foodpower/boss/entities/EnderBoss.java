package com.github.immortalmice.foodpower.boss.entities;

import com.github.immortalmice.foodpower.baseclass.BossBase;

import net.minecraft.entity.EntityType;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;

public class EnderBoss extends BossBase {
	public EnderBoss(EntityType<? extends EnderBoss> type, World worldIn) {
		super(type, worldIn, Color.PURPLE);
	}
}
