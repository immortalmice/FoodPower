package com.github.immortalmice.foodpower.boss.entities;

import com.github.immortalmice.foodpower.baseclass.BossBase;

import net.minecraft.entity.EntityType;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;

public class NetherBoss extends BossBase {
	public NetherBoss(EntityType<? extends NetherBoss> type, World worldIn) {
		super(type, worldIn, Color.RED);
	}
}
