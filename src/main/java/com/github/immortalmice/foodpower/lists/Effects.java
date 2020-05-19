package com.github.immortalmice.foodpower.lists;

import java.util.function.Function;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.effect.FoodEffect;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.util.ReflectList;

public class Effects{
	public static final ReflectList<FoodEffect, FoodEffects> list = new ReflectList<FoodEffect, FoodEffects>(FoodEffect.class, FoodEffects.class, null);

	@ObjectHolder("foodpower")
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
		return Registry.REGISTER;
	}

	public static FoodEffect getFoodEffectByIngredient(Ingredient ingredientIn){
		for(FoodEffect effect : Effects.list){
			if(effect.getIngredientName().equals(ingredientIn.getFPName())){
				return effect;
			}
		}
		return null;
	}
}

class Registry{
	public static final DeferredRegister<Effect> REGISTER = new DeferredRegister<Effect>(ForgeRegistries.POTIONS, FoodPower.MODID);

	/* Mod Ingrediant */
	public static final RegistryObject<Effect> OBJ_MINT_POWER = Registry.foodEffectRegister("mint", (str) -> new FoodEffect(str, 0x77FF00));;
	public static final RegistryObject<Effect> OBJ_FERMENTED_ENDEREYE_POWER = Registry.foodEffectRegister("fermented_endereye", (str) -> new FoodEffect(str, 0xFF5511));;
	public static final RegistryObject<Effect> OBJ_KETCHUP_POWER = Registry.foodEffectRegister("ketchup", (str) -> new FoodEffect(str, 0xC63300));;
	public static final RegistryObject<Effect> OBJ_SAUCE_POWER = Registry.foodEffectRegister("sauce", (str) -> new FoodEffect(str, 0xCC0000));;
	public static final RegistryObject<Effect> OBJ_SALT_POWER = Registry.foodEffectRegister("salt", (str) -> new FoodEffect(str, 0xFFFFFF));;
	public static final RegistryObject<Effect> OBJ_OIL_POWER = Registry.foodEffectRegister("oil", (str) -> new FoodEffect(str, 0xFFEE99));;
	public static final RegistryObject<Effect> OBJ_RICE_POWER = Registry.foodEffectRegister("rice", (str) -> new FoodEffect(str, 0xFFFFBB));;
	public static final RegistryObject<Effect> OBJ_CHEESE_POWER = Registry.foodEffectRegister("cheese", (str) -> new FoodEffect(str, 0xFF8800));;
	public static final RegistryObject<Effect> OBJ_CHILI_POWER = Registry.foodEffectRegister("chili", (str) -> new FoodEffect(str, 0xFF0000));;
	public static final RegistryObject<Effect> OBJ_SPINACH_POWER = Registry.foodEffectRegister("spinach", (str) -> new FoodEffect(str, 0x00DD00));;
	public static final RegistryObject<Effect> OBJ_CABBAGE_POWER = Registry.foodEffectRegister("cabbage", (str) -> new FoodEffect(str, 0x00AA00));;
	public static final RegistryObject<Effect> OBJ_CORN_POWER = Registry.foodEffectRegister("corn", (str) -> new FoodEffect(str, 0xFFFF00));;

	/* Vanilla Ingrediant */
	public static final RegistryObject<Effect> OBJ_APPLE_POWER = Registry.foodEffectRegister("apple", (str) -> new FoodEffect(str, 0xE63F00));;
	public static final RegistryObject<Effect> OBJ_MELON_POWER = Registry.foodEffectRegister("melon", (str) -> new FoodEffect(str, 0x33FF33));;
	public static final RegistryObject<Effect> OBJ_PUMPKIN_POWER = Registry.foodEffectRegister("pumpkin", (str) -> new FoodEffect(str, 0xEE7700));;
	public static final RegistryObject<Effect> OBJ_CARROT_POWER = Registry.foodEffectRegister("carrot", (str) -> new FoodEffect(str, 0xFFAA33));;
	public static final RegistryObject<Effect> OBJ_POTATO_POWER = Registry.foodEffectRegister("potato", (str) -> new FoodEffect(str, 0xFFDDAA));;
	public static final RegistryObject<Effect> OBJ_BEETROOT_POWER = Registry.foodEffectRegister("beetroot", (str) -> new FoodEffect(str, 0xFF5511));;
	public static final RegistryObject<Effect> OBJ_BROWN_MUSHROOM_POWER = Registry.foodEffectRegister("brown_mushroom", (str) -> new FoodEffect(str, 0xAA7700));;
	public static final RegistryObject<Effect> OBJ_RED_MUSHROOM_POWER = Registry.foodEffectRegister("red_mushroom", (str) -> new FoodEffect(str, 0xE63F00));;
	public static final RegistryObject<Effect> OBJ_EGG_POWER = Registry.foodEffectRegister("egg", (str) -> new FoodEffect(str, 0xDDDDDD));;
	public static final RegistryObject<Effect> OBJ_MILK_BUCKET_POWER = Registry.foodEffectRegister("milk_bucket", (str) -> new FoodEffect(str, 0xFFFFFF));;
	public static final RegistryObject<Effect> OBJ_PORKCHOP_POWER = Registry.foodEffectRegister("porkchop", (str) -> new FoodEffect(str, 0xFFCCCC));;
	public static final RegistryObject<Effect> OBJ_BEEF_POWER = Registry.foodEffectRegister("beef", (str) -> new FoodEffect(str, 0xFF8888));;
	public static final RegistryObject<Effect> OBJ_CHICKEN_POWER = Registry.foodEffectRegister("chicken", (str) -> new FoodEffect(str, 0xFFCCCC));;
	public static final RegistryObject<Effect> OBJ_MUTTON_POWER = Registry.foodEffectRegister("mutton", (str) -> new FoodEffect(str, 0xFF8888));;
	public static final RegistryObject<Effect> OBJ_CHORUS_FRUIT_POWER = Registry.foodEffectRegister("chorus_fruit", (str) -> new FoodEffect(str, 0xD28EFF));;
	public static final RegistryObject<Effect> OBJ_COCOA_BEANS_POWER = Registry.foodEffectRegister("cocoa_beans", (str) -> new FoodEffect(str, 0xBB5500));;
	public static final RegistryObject<Effect> OBJ_WATER_BUCKET_POWER = Registry.foodEffectRegister("water_bucket", (str) -> new FoodEffect(str, 0x00FFFF));;
	public static final RegistryObject<Effect> OBJ_HONEY_BOTTLE_POWER = Registry.foodEffectRegister("honey_bottle", (str) -> new FoodEffect(str, 0xF9F900));; 
	public static final RegistryObject<Effect> OBJ_SWEET_BERRIES_POWER = Registry.foodEffectRegister("sweet_berries", (str) -> new FoodEffect(str, 0xF00078));; 
	public static final RegistryObject<Effect> OBJ_DRAGON_BREATH_POWER = Registry.foodEffectRegister("dragon_breath", (str) -> new FoodEffect(str, 0x9955FF));
	public static final RegistryObject<Effect> OBJ_EXPERIENCE_BOTTLE_POWER = Registry.foodEffectRegister("experience_bottle", (str) -> new FoodEffect(str, 0xBBFF00));

	private static RegistryObject<Effect> foodEffectRegister(String str, Function<String, Effect> fun){
		return Registry.REGISTER.register(FoodEffect.getFPNameByIngredientName(str), () -> fun.apply(str));
	}
}