package com.github.immortalmice.foodpower.lists;

import java.util.function.Supplier;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.loot.CabbageEffectModifier;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class LootModifiers {
	@ObjectHolder(FoodPower.MODID)
	public static class ModifierSerializers {
		public static final CabbageEffectModifier.Serializer CABBAGE_EFFECT = null;
	}
	
	public static DeferredRegister<GlobalLootModifierSerializer<?>> getRegister(){
		return LootModifiersRegistry.REGISTER;
	}
}

class LootModifiersRegistry {
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> REGISTER = new DeferredRegister<GlobalLootModifierSerializer<?>>(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, FoodPower.MODID);
	
	public static final RegistryObject<GlobalLootModifierSerializer<?>> OBJ_CABBAGE_EFFECT = LootModifiersRegistry.register("cabbage_effect", () -> new CabbageEffectModifier.Serializer());
			
	private static RegistryObject<GlobalLootModifierSerializer<?>> register(String name, Supplier<GlobalLootModifierSerializer<?>> sup){
		return LootModifiersRegistry.REGISTER.register(name, sup);
	}
}
