package com.github.immortalmice.foodpower.entity;

import com.github.immortalmice.foodpower.lists.OtherEntitys;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class VenomEntity extends ProjectileItemEntity {
	public VenomEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public VenomEntity(LivingEntity throwerIn) {
		super(OtherEntitys.EntityTypes.VENOM, throwerIn, throwerIn.world);
	}

	@Override
	protected Item getDefaultItem() {
		return Items.VENOM;
	}

	@Override
	public IPacket<?> createSpawnPacket(){
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if(!this.world.isRemote){
			if(result.getType() == RayTraceResult.Type.ENTITY){
				Entity target = ((EntityRayTraceResult) result).getEntity();

				target.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5.0f);
				if(target instanceof LivingEntity) {
					if(((LivingEntity) target).isPotionActive(Effects.BLINDNESS) && this.getThrower() != null) {
						this.getThrower().heal(5.0f);
					}
					((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 60));
				}
			}
			this.remove();
		}
	}
}
