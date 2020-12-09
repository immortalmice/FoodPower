package com.github.immortalmice.foodpower.boss.entities;

import com.github.immortalmice.foodpower.baseclass.BossBase;

import net.minecraft.entity.EntityType;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;

public class SaltyBoss extends BossBase {
	public SaltyBoss(EntityType<? extends SaltyBoss> type, World worldIn) {
		super(type, worldIn, Color.BLUE);
	}
}
