package com.github.immortalmice.foodpower.customclass.specialclass;

import com.github.immortalmice.foodpower.lists.OtherEntitys;
import com.github.immortalmice.foodpower.lists.OtherItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PapayaSeedEntity extends ProjectileItemEntity{
	public PapayaSeedEntity(EntityType<? extends PapayaSeedEntity> type, World worldIn){
		super(type, worldIn);
	}

	public PapayaSeedEntity(World worldIn, double x, double y, double z){
		super(OtherEntitys.EntityTypes.PAPAYA_SEED, x, y, z, worldIn);
	}

	@Override
	protected Item getDefaultItem() {
		return OtherItems.Items.PAPAYA_SEED;
	}

	@Override
	public IPacket<?> createSpawnPacket(){
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		
	}
} 