package com.github.immortalmice.foodpower.boss.entities;

import com.github.immortalmice.foodpower.baseclass.BossBase;

import net.minecraft.entity.EntityType;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;

public class SweetBoss extends BossBase {
	public SweetBoss(EntityType<? extends SweetBoss> type, World worldIn) {
		super(type, worldIn, Color.PINK);
	}
}
