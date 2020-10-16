package com.github.immortalmice.foodpower.lists;

import java.util.function.Supplier;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.effect.FoodEffect;
import com.github.immortalmice.foodpower.util.ReflectList;

public class Effects{
	public static final ReflectList<FoodEffect, FoodEffects> list = new ReflectList<FoodEffect, FoodEffects>(FoodEffect.class, FoodEffects.class);

	@ObjectHolder(FoodPower.MODID)
	public static class FoodEffects{
		/* Mod Ingrediant */
		public static final FoodEffect MINT_POWER = null;
		public static final FoodEffect FERMENTED_ENDEREYE_POWER = null;
		public static final FoodEffect KETCHUP_POWER = null;
		public static final FoodEffect SAUCE_POWER = null;
		public static final FoodEffect SALT_POWER = null;
		public static final FoodEffect OIL_POWER = null;
		public static final FoodEffect RICE_POWER = null;
		public static final FoodEffect CHEESE_POWER = null;
		public static final FoodEffect CHILI_POWER = null;
		public static final FoodEffect SPINACH_POWER = null;
		public static final FoodEffect CABBAGE_POWER = null;
		public static final FoodEffect CORN_POWER = null;

		/* Vanilla Ingrediant */
		public static final FoodEffect APPLE_POWER = null;
		public static final FoodEffect MELON_POWER = null;
		public static final FoodEffect PUMPKIN_POWER = null;
		public static final FoodEffect CARROT_POWER = null;
		public static final FoodEffect POTATO_POWER = null;
		public static final FoodEffect BEETROOT_POWER = null;
		public static final FoodEffect BROWN_MUSHROOM_POWER = null;
		public static final FoodEffect RED_MUSHROOM_POWER = null;
		public static final FoodEffect EGG_POWER = null;
		public static final FoodEffect MILK_BUCKET_POWER = null;
		public static final FoodEffect PORKCHOP_POWER = null;
		public static final FoodEffect BEEF_POWER = null;
		public static final FoodEffect CHICKEN_POWER = null;
		public static final FoodEffect MUTTON_POWER = null;
		public static final FoodEffect CHORUS_FRUIT_POWER = null;
		public static final FoodEffect COCOA_BEANS_POWER = null;
		public static final FoodEffect WATER_BUCKET_POWER = null;
		public static final FoodEffect HONEY_BOTTLE_POWER = null; 
		public static final FoodEffect SWEET_BERRIES_POWER = null; 
		public static final FoodEffect DRAGON_BREATH_POWER = null; 
		public static final FoodEffect EXPERIENCE_BOTTLE_POWER = null; 
	}

	public static DeferredRegister<Effect> getRegister(){
		return EffectRegistry.REGISTER;
	}
}

class EffectRegistry{
	public static final DeferredRegister<Effect> REGISTER = new DeferredRegister<Effect>(ForgeRegistries.POTIONS, FoodPower.MODID);

	/* Mod Ingrediant */
	public static final RegistryObject<Effect> OBJ_MINT_POWER = EffectRegistry.foodEffectRegister("mint_power", () -> new FoodEffect(0x77FF00));;
	public static final RegistryObject<Effect> OBJ_FERMENTED_ENDEREYE_POWER = EffectRegistry.foodEffectRegister("fermented_endereye_power", () -> new FoodEffect(0xFF5511));;
	public static final RegistryObject<Effect> OBJ_KETCHUP_POWER = EffectRegistry.foodEffectRegister("ketchup_power", () -> new FoodEffect(0xC63300));;
	public static final RegistryObject<Effect> OBJ_SAUCE_POWER = EffectRegistry.foodEffectRegister("sauce_power", () -> new FoodEffect(0xCC0000));;
	public static final RegistryObject<Effect> OBJ_SALT_POWER = EffectRegistry.foodEffectRegister("salt_power", () -> new FoodEffect(0xFFFFFF));;
	public static final RegistryObject<Effect> OBJ_OIL_POWER = EffectRegistry.foodEffectRegister("oil_power", () -> new FoodEffect(0xFFEE99));;
	public static final RegistryObject<Effect> OBJ_RICE_POWER = EffectRegistry.foodEffectRegister("rice_power", () -> new FoodEffect(0xFFFFBB));;
	public static final RegistryObject<Effect> OBJ_CHEESE_POWER = EffectRegistry.foodEffectRegister("cheese_power", () -> new FoodEffect(0xFF8800));;
	public static final RegistryObject<Effect> OBJ_CHILI_POWER = EffectRegistry.foodEffectRegister("chili_power", () -> new FoodEffect(0xFF0000));;
	public static final RegistryObject<Effect> OBJ_SPINACH_POWER = EffectRegistry.foodEffectRegister("spinach_power", () -> new FoodEffect(0x00DD00));;
	public static final RegistryObject<Effect> OBJ_CABBAGE_POWER = EffectRegistry.foodEffectRegister("cabbage_power", () -> new FoodEffect(0x00AA00));;
	public static final RegistryObject<Effect> OBJ_CORN_POWER = EffectRegistry.foodEffectRegister("corn_power", () -> new FoodEffect(0xFFFF00));;

	/* Vanilla Ingrediant */
	public static final RegistryObject<Effect> OBJ_APPLE_POWER = EffectRegistry.foodEffectRegister("apple_power", () -> new FoodEffect(0xE63F00));;
	public static final RegistryObject<Effect> OBJ_MELON_POWER = EffectRegistry.foodEffectRegister("melon_power", () -> new FoodEffect(0x33FF33));;
	public static final RegistryObject<Effect> OBJ_PUMPKIN_POWER = EffectRegistry.foodEffectRegister("pumpkin_power", () -> new FoodEffect(0xEE7700));;
	public static final RegistryObject<Effect> OBJ_CARROT_POWER = EffectRegistry.foodEffectRegister("carrot_power", () -> new FoodEffect(0xFFAA33));;
	public static final RegistryObject<Effect> OBJ_POTATO_POWER = EffectRegistry.foodEffectRegister("potato_power", () -> new FoodEffect(0xFFDDAA));;
	public static final RegistryObject<Effect> OBJ_BEETROOT_POWER = EffectRegistry.foodEffectRegister("beetroot_power", () -> new FoodEffect(0xFF5511));;
	public static final RegistryObject<Effect> OBJ_BROWN_MUSHROOM_POWER = EffectRegistry.foodEffectRegister("brown_mushroom_power", () -> new FoodEffect(0xAA7700));;
	public static final RegistryObject<Effect> OBJ_RED_MUSHROOM_POWER = EffectRegistry.foodEffectRegister("red_mushroom_power", () -> new FoodEffect(0xE63F00));;
	public static final RegistryObject<Effect> OBJ_EGG_POWER = EffectRegistry.foodEffectRegister("egg_power", () -> new FoodEffect(0xDDDDDD));;
	public static final RegistryObject<Effect> OBJ_MILK_BUCKET_POWER = EffectRegistry.foodEffectRegister("milk_bucket_power", () -> new FoodEffect(0xFFFFFF));;
	public static final RegistryObject<Effect> OBJ_PORKCHOP_POWER = EffectRegistry.foodEffectRegister("porkchop_power", () -> new FoodEffect(0xFFCCCC));;
	public static final RegistryObject<Effect> OBJ_BEEF_POWER = EffectRegistry.foodEffectRegister("beef_power", () -> new FoodEffect(0xFF8888));;
	public static final RegistryObject<Effect> OBJ_CHICKEN_POWER = EffectRegistry.foodEffectRegister("chicken_power", () -> new FoodEffect(0xFFCCCC));;
	public static final RegistryObject<Effect> OBJ_MUTTON_POWER = EffectRegistry.foodEffectRegister("mutton_power", () -> new FoodEffect(0xFF8888));;
	public static final RegistryObject<Effect> OBJ_CHORUS_FRUIT_POWER = EffectRegistry.foodEffectRegister("chorus_fruit_power", () -> new FoodEffect(0xD28EFF));;
	public static final RegistryObject<Effect> OBJ_COCOA_BEANS_POWER = EffectRegistry.foodEffectRegister("cocoa_beans_power", () -> new FoodEffect(0xBB5500));;
	public static final RegistryObject<Effect> OBJ_WATER_BUCKET_POWER = EffectRegistry.foodEffectRegister("water_bucket_power", () -> new FoodEffect(0x00FFFF));;
	public static final RegistryObject<Effect> OBJ_HONEY_BOTTLE_POWER = EffectRegistry.foodEffectRegister("honey_bottle_power", () -> new FoodEffect(0xF9F900));; 
	public static final RegistryObject<Effect> OBJ_SWEET_BERRIES_POWER = EffectRegistry.foodEffectRegister("sweet_berries_power", () -> new FoodEffect(0xF00078));; 
	public static final RegistryObject<Effect> OBJ_DRAGON_BREATH_POWER = EffectRegistry.foodEffectRegister("dragon_breath_power", () -> new FoodEffect(0x9955FF));
	public static final RegistryObject<Effect> OBJ_EXPERIENCE_BOTTLE_POWER = EffectRegistry.foodEffectRegister("experience_bottle_power", () -> new FoodEffect(0xBBFF00));

	private static RegistryObject<Effect> foodEffectRegister(String str, Supplier<Effect> sup){
		return EffectRegistry.REGISTER.register(str, sup);
	}
}