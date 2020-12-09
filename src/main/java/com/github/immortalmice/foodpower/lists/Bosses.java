package com.github.immortalmice.foodpower.lists;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.boss.entities.BitterBoss;
import com.github.immortalmice.foodpower.boss.entities.EnderBoss;
import com.github.immortalmice.foodpower.boss.entities.NetherBoss;
import com.github.immortalmice.foodpower.boss.entities.SaltyBoss;
import com.github.immortalmice.foodpower.boss.entities.SourBoss;
import com.github.immortalmice.foodpower.boss.entities.SweetBoss;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class Bosses {
	@ObjectHolder(FoodPower.MODID)
	public static class EntityTypes {
		public static final EntityType<SweetBoss> SWEET_BOSS = null;
		public static final EntityType<BitterBoss> BITTER_BOSS = null;
		public static final EntityType<SourBoss> SOUR_BOSS = null;
		public static final EntityType<SaltyBoss> SALTY_BOSS = null;
		public static final EntityType<NetherBoss> NETHER_BOSS = null;
		public static final EntityType<EnderBoss> ENDER_BOSS = null;
	}
	
	public static DeferredRegister<EntityType<?>> getEntityTypeRegister() {
		return BossesRegistry.ENTITY_TYPE_REGISTER;
	}
}

class BossesRegistry {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = new DeferredRegister<EntityType<?>>(ForgeRegistries.ENTITIES, FoodPower.MODID);
	
	public static final RegistryObject<EntityType<?>> OBJ_SWEET_BOSS = BossesRegistry.register("sweet_boss", EntityType.Builder.<SweetBoss>create(SweetBoss::new, EntityClassification.MONSTER));
	public static final RegistryObject<EntityType<?>> OBJ_BITTER_BOSS = BossesRegistry.register("bitter_boss", EntityType.Builder.<BitterBoss>create(BitterBoss::new, EntityClassification.MONSTER));
	public static final RegistryObject<EntityType<?>> OBJ_SOUR_BOSS = BossesRegistry.register("sour_boss", EntityType.Builder.<SourBoss>create(SourBoss::new, EntityClassification.MONSTER));
	public static final RegistryObject<EntityType<?>> OBJ_SALTY_BOSS = BossesRegistry.register("salty_boss", EntityType.Builder.<SaltyBoss>create(SaltyBoss::new, EntityClassification.MONSTER));
	public static final RegistryObject<EntityType<?>> OBJ_NETHER_BOSS = BossesRegistry.register("nether_boss", EntityType.Builder.<NetherBoss>create(NetherBoss::new, EntityClassification.MONSTER));
	public static final RegistryObject<EntityType<?>> OBJ_ENDER_BOSS = BossesRegistry.register("ender_boss", EntityType.Builder.<EnderBoss>create(EnderBoss::new, EntityClassification.MONSTER));
	
	private static RegistryObject<EntityType<?>> register(String name, EntityType.Builder<?> builder){
		return BossesRegistry.ENTITY_TYPE_REGISTER.register(name, () -> builder.build(name));
	}
}
