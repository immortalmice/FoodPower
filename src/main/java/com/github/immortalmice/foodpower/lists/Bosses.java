package com.github.immortalmice.foodpower.lists;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.boss.entities.SourBoss;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class Bosses {
	@ObjectHolder(FoodPower.MODID)
	public static class EntityTypes {
		public static final EntityType<SourBoss> SOUR_BOSS = null;
	}
	
	public static DeferredRegister<EntityType<?>> getEntityTypeRegister() {
		return BossesRegistry.ENTITY_TYPE_REGISTER;
	}
}

class BossesRegistry {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = new DeferredRegister<EntityType<?>>(ForgeRegistries.ENTITIES, FoodPower.MODID);
	
	public static final RegistryObject<EntityType<?>> OBJ_SOUR_BOSS = BossesRegistry.register("sour_boss", EntityType.Builder.<SourBoss>create(SourBoss::new, EntityClassification.MONSTER));
	
	private static RegistryObject<EntityType<?>> register(String name, EntityType.Builder<?> builder){
		return BossesRegistry.ENTITY_TYPE_REGISTER.register(name, () -> builder.build(name));
	}
}
