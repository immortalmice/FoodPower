package com.github.immortalmice.foodpower.lists;

import java.util.function.Function;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.effect.FoodEffect;

public class Effects{

	@ObjectHolder("foodpower")
	public static class FoodEffects{
		/* Mod Ingrediant */
		public static final FoodEffect BUTTER = null;
		public static final FoodEffect ORANGE = null;
		public static final FoodEffect KIWI = null;
		public static final FoodEffect PAPAYA = null;
		public static final FoodEffect MANGO = null;
		public static final FoodEffect LEMON = null;
		public static final FoodEffect MINT = null;
		public static final FoodEffect FERMENTED_ENDEREYE = null;
		public static final FoodEffect DOUGH = null;
		public static final FoodEffect TOMATO = null;
		public static final FoodEffect KETCHUP = null;
		public static final FoodEffect SAUCE = null;
		public static final FoodEffect SALT = null;
		public static final FoodEffect OIL = null;
		public static final FoodEffect RICE = null;
		public static final FoodEffect CHEESE = null;
		public static final FoodEffect CHILI = null;
		public static final FoodEffect SPINACH = null;
		public static final FoodEffect CABBAGE = null;
		public static final FoodEffect FLOUR = null;
		public static final FoodEffect CORN = null;
		public static final FoodEffect CREAM = null;

		/* Vanilla Ingrediant */
		public static final FoodEffect APPLE = null;
		public static final FoodEffect MELON = null;
		public static final FoodEffect PUMPKIN = null;
		public static final FoodEffect CARROT = null;
		public static final FoodEffect POTATO = null;
		public static final FoodEffect BEETROOT = null;
		public static final FoodEffect BROWN_MUSHROOM = null;
		public static final FoodEffect RED_MUSHROOM = null;
		public static final FoodEffect EGG = null;
		public static final FoodEffect MILK_BUCKET = null;
		public static final FoodEffect PORKCHOP = null;
		public static final FoodEffect BEEF = null;
		public static final FoodEffect CHICKEN = null;
		public static final FoodEffect MUTTON = null;
		public static final FoodEffect CHORUS_FRUIT = null;
		public static final FoodEffect COCOA_BEANS = null;
		public static final FoodEffect SUGAR = null;
		public static final FoodEffect WATER_BUCKET = null;
		public static final FoodEffect NETHER_WART = null; 
	}

	public static DeferredRegister<Effect> getRegister(){
		return Registry.REGISTER;
	}
}

class Registry{
	public static final DeferredRegister<Effect> REGISTER = new DeferredRegister<Effect>(ForgeRegistries.POTIONS, FoodPower.MODID);

	/* Mod Ingrediant */
	public static final RegistryObject<Effect> OBJ_BUTTER = Registry.foodEffectRegister("butter", (str) -> new FoodEffect(str, 0xFFFF00));;
	public static final RegistryObject<Effect> OBJ_ORANGE = Registry.foodEffectRegister("orange", (str) -> new FoodEffect(str, 0xFFAA33));;
	public static final RegistryObject<Effect> OBJ_KIWI = Registry.foodEffectRegister("kiwi", (str) -> new FoodEffect(str, 0x00FF00));;
	public static final RegistryObject<Effect> OBJ_PAPAYA = Registry.foodEffectRegister("papaya", (str) -> new FoodEffect(str, 0xDDAA00));;
	public static final RegistryObject<Effect> OBJ_MANGO = Registry.foodEffectRegister("mango", (str) -> new FoodEffect(str, 0xFFAA33));;
	public static final RegistryObject<Effect> OBJ_LEMON = Registry.foodEffectRegister("lemon", (str) -> new FoodEffect(str, 0xFFFF33));;
	public static final RegistryObject<Effect> OBJ_MINT = Registry.foodEffectRegister("mint", (str) -> new FoodEffect(str, 0x77FF00));;
	public static final RegistryObject<Effect> OBJ_FERMENTED_ENDEREYE = Registry.foodEffectRegister("fermented_endereye", (str) -> new FoodEffect(str, 0xFF5511));;
	public static final RegistryObject<Effect> OBJ_DOUGH = Registry.foodEffectRegister("dough", (str) -> new FoodEffect(str, 0xFFCC22));;
	public static final RegistryObject<Effect> OBJ_TOMATO = Registry.foodEffectRegister("tomato", (str) -> new FoodEffect(str, 0xE63F00));;
	public static final RegistryObject<Effect> OBJ_KETCHUP = Registry.foodEffectRegister("ketchup", (str) -> new FoodEffect(str, 0xC63300));;
	public static final RegistryObject<Effect> OBJ_SAUCE = Registry.foodEffectRegister("sauce", (str) -> new FoodEffect(str, 0xCC0000));;
	public static final RegistryObject<Effect> OBJ_SALT = Registry.foodEffectRegister("salt", (str) -> new FoodEffect(str, 0xFFFFFF));;
	public static final RegistryObject<Effect> OBJ_OIL = Registry.foodEffectRegister("oil", (str) -> new FoodEffect(str, 0xFFEE99));;
	public static final RegistryObject<Effect> OBJ_RICE = Registry.foodEffectRegister("rice", (str) -> new FoodEffect(str, 0xFFFFBB));;
	public static final RegistryObject<Effect> OBJ_CHEESE = Registry.foodEffectRegister("cheese", (str) -> new FoodEffect(str, 0xFF8800));;
	public static final RegistryObject<Effect> OBJ_CHILI = Registry.foodEffectRegister("chili", (str) -> new FoodEffect(str, 0xFF0000));;
	public static final RegistryObject<Effect> OBJ_SPINACH = Registry.foodEffectRegister("spinach", (str) -> new FoodEffect(str, 0x00DD00));;
	public static final RegistryObject<Effect> OBJ_CABBAGE = Registry.foodEffectRegister("cabbage", (str) -> new FoodEffect(str, 0x00AA00));;
	public static final RegistryObject<Effect> OBJ_FLOUR = Registry.foodEffectRegister("flour", (str) -> new FoodEffect(str, 0xEEFFBB));;
	public static final RegistryObject<Effect> OBJ_CORN = Registry.foodEffectRegister("corn", (str) -> new FoodEffect(str, 0xFFFF00));;
	public static final RegistryObject<Effect> OBJ_CREAM = Registry.foodEffectRegister("cream", (str) -> new FoodEffect(str, 0xFFFFBB));;

	/* Vanilla Ingrediant */
	public static final RegistryObject<Effect> OBJ_APPLE = Registry.foodEffectRegister("apple", (str) -> new FoodEffect(str, 0xE63F00));;
	public static final RegistryObject<Effect> OBJ_MELON = Registry.foodEffectRegister("melon", (str) -> new FoodEffect(str, 0x33FF33));;
	public static final RegistryObject<Effect> OBJ_PUMPKIN = Registry.foodEffectRegister("pumpkin", (str) -> new FoodEffect(str, 0xEE7700));;
	public static final RegistryObject<Effect> OBJ_CARROT = Registry.foodEffectRegister("carrot", (str) -> new FoodEffect(str, 0xFFAA33));;
	public static final RegistryObject<Effect> OBJ_POTATO = Registry.foodEffectRegister("potato", (str) -> new FoodEffect(str, 0xFFDDAA));;
	public static final RegistryObject<Effect> OBJ_BEETROOT = Registry.foodEffectRegister("beetroot", (str) -> new FoodEffect(str, 0xFF5511));;
	public static final RegistryObject<Effect> OBJ_BROWN_MUSHROOM = Registry.foodEffectRegister("brown_mushroom", (str) -> new FoodEffect(str, 0xAA7700));;
	public static final RegistryObject<Effect> OBJ_RED_MUSHROOM = Registry.foodEffectRegister("red_mushroom", (str) -> new FoodEffect(str, 0xE63F00));;
	public static final RegistryObject<Effect> OBJ_EGG = Registry.foodEffectRegister("egg", (str) -> new FoodEffect(str, 0xDDDDDD));;
	public static final RegistryObject<Effect> OBJ_MILK_BUCKET = Registry.foodEffectRegister("milk_bucket", (str) -> new FoodEffect(str, 0xFFFFFF));;
	public static final RegistryObject<Effect> OBJ_PORKCHOP = Registry.foodEffectRegister("porkchop", (str) -> new FoodEffect(str, 0xFFCCCC));;
	public static final RegistryObject<Effect> OBJ_BEEF = Registry.foodEffectRegister("beef", (str) -> new FoodEffect(str, 0xFF8888));;
	public static final RegistryObject<Effect> OBJ_CHICKEN = Registry.foodEffectRegister("chicken", (str) -> new FoodEffect(str, 0xFFCCCC));;
	public static final RegistryObject<Effect> OBJ_MUTTON = Registry.foodEffectRegister("mutton", (str) -> new FoodEffect(str, 0xFF8888));;
	public static final RegistryObject<Effect> OBJ_CHORUS_FRUIT = Registry.foodEffectRegister("chorus_fruit", (str) -> new FoodEffect(str, 0xD28EFF));;
	public static final RegistryObject<Effect> OBJ_COCOA_BEANS = Registry.foodEffectRegister("cocoa_beans", (str) -> new FoodEffect(str, 0xBB5500));;
	public static final RegistryObject<Effect> OBJ_SUGAR = Registry.foodEffectRegister("sugar", (str) -> new FoodEffect(str, 0xFFFFFF));;
	public static final RegistryObject<Effect> OBJ_WATER_BUCKET = Registry.foodEffectRegister("water_bucket", (str) -> new FoodEffect(str, 0x00FFFF));;
	public static final RegistryObject<Effect> OBJ_NETHER_WART = Registry.foodEffectRegister("nether_wart", (str) -> new FoodEffect(str, 0xAA0000));; 

	private static RegistryObject<Effect> foodEffectRegister(String str, Function<String, Effect> fun){
		return Registry.REGISTER.register(FoodEffect.getFPNameByIngredientName(str), () -> fun.apply(str));
	}
}