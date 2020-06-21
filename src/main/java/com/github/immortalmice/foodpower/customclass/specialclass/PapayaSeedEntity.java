package com.github.immortalmice.foodpower.customclass.specialclass;

import com.github.immortalmice.foodpower.lists.OtherItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class PapayaSeedEntity extends ProjectileItemEntity{
	public PapayaSeedEntity(EntityType<? extends PapayaSeedEntity> type, World worldIn){
		super(type, worldIn);
	}

	@Override
	protected Item getDefaultItem() {
		return OtherItems.Items.PAPAYA_SEED;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		
	}
} 