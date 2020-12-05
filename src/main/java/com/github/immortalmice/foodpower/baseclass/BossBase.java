package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;

public class BossBase extends MobEntity {
	private final ServerBossInfo bossInfo;
	
	public BossBase(EntityType<? extends MobEntity> type, World worldIn, BossInfo.Color bossBarColorIn) {
		super(type, worldIn);
		
		this.bossInfo = new ServerBossInfo(this.getDisplayName(), bossBarColorIn, BossInfo.Overlay.NOTCHED_6);
	}
	
	@Override
	public void addTrackingPlayer(ServerPlayerEntity player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}
	
	@Override
	public void removeTrackingPlayer(ServerPlayerEntity player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}
}
