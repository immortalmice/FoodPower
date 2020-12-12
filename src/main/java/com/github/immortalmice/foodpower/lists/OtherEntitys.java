package com.github.immortalmice.foodpower.lists;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.entity.PapayaSeedEntity;
import com.github.immortalmice.foodpower.entity.VenomEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class OtherEntitys{
	@ObjectHolder(FoodPower.MODID)
	public static class EntityTypes{
		public static final EntityType<PapayaSeedEntity> PAPAYA_SEED = null;
		public static final EntityType<VenomEntity> VENOM = null;
	}
	public static DeferredRegister<EntityType<?>> getRegister(){
		return OtherEntitysRegistry.REGISTER;
	}
}

class OtherEntitysRegistry{
	public static final DeferredRegister<EntityType<?>> REGISTER = new DeferredRegister<EntityType<?>>(ForgeRegistries.ENTITIES, FoodPower.MODID);

	public static final RegistryObject<EntityType<?>> OBJ_PAPAYA_SEED = OtherEntitysRegistry.register("papaya_seed", EntityType.Builder.<PapayaSeedEntity>create(PapayaSeedEntity::new, EntityClassification.MISC));
	public static final RegistryObject<EntityType<?>> OBJ_VENOM = OtherEntitysRegistry.register("venom", EntityType.Builder.<VenomEntity>create(VenomEntity::new, EntityClassification.MISC));

	private static RegistryObject<EntityType<?>> register(String name, EntityType.Builder<?> builder){
		return OtherEntitysRegistry.REGISTER.register(name, () -> builder.build(name));
	}
}