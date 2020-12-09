package com.github.immortalmice.foodpower.boss.entities;

import com.github.immortalmice.foodpower.baseclass.BossBase;

import net.minecraft.entity.EntityType;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;

public class BitterBoss extends BossBase {
	public BitterBoss(EntityType<? extends BitterBoss> type, World worldIn) {
		super(type, worldIn, Color.YELLOW);
	}
}
