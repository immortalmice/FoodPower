package com.github.immortalmice.foodpower.customclass.specialclass;

import com.github.immortalmice.foodpower.lists.OtherEntitys;
import com.github.immortalmice.foodpower.lists.OtherItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PapayaSeedEntity extends ProjectileItemEntity{
	private int level = 1;
	private static float[] baseDamage = {6.0f, 10.0f, 16.0f};

	public PapayaSeedEntity(EntityType<? extends PapayaSeedEntity> type, World worldIn){
		super(type, worldIn);
	}

	public PapayaSeedEntity(World worldIn, LivingEntity throwerIn){
		super(OtherEntitys.EntityTypes.PAPAYA_SEED, throwerIn, worldIn);
	}

	protected final void fixLevel(){
		if(this.level <= 0){
			this.level = 1;
		}else if(level >= 4){
			this.level = 3;
		}
	}

	public void setIngredientLevel(int levelIn){
		this.level = levelIn;
		this.fixLevel();
	}

	@Override
	protected Item getDefaultItem(){
		return OtherItems.Items.PAPAYA_SEED;
	}

	@Override
	public IPacket<?> createSpawnPacket(){
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void onImpact(RayTraceResult result){
		if(!this.world.isRemote){
			if(result.getType() == RayTraceResult.Type.ENTITY){
				Entity target = ((EntityRayTraceResult) result).getEntity();

				target.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower())
					, PapayaSeedEntity.baseDamage[this.level - 1]);
			}
			this.remove();
		}
	}
} 