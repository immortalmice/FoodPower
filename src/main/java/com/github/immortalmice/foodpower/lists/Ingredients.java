package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.RegistryObject;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.github.immortalmice.foodpower.customclass.util.ReflectList;

/* All the ingredient need to be registed will list below. */
/* Do not use ingrediant directly to create ItemStack, use Ingredient#asItem */
public class Ingredients{
	public static final ReflectList<Ingredient, Items> list = new ReflectList<Ingredient, Items>(Ingredient.class, Items.class, null);
	public static final ReflectList<CookedFood, Items> cookedFoodList = new ReflectList<CookedFood, Items>(CookedFood.class, Items.class, null);
	public static final ReflectList<Meal, Items> mealList = new ReflectList<Meal, Items>(Meal.class, Items.class, null);

	@ObjectHolder("foodpower")
	public static class Items{
		/* Mod Ingrediant */
		public static final Ingredient BUTTER = null;
		public static final Ingredient ORANGE = null;
		public static final Ingredient KIWI = null;
		public static final Ingredient PAPAYA = null;
		public static final Ingredient MANGO = null;
		public static final Ingredient LEMON = null;
		public static final Ingredient MINT = null;
		public static final Ingredient FERMENTED_ENDEREYE = null;
		public static final Ingredient DOUGH = null;
		public static final Ingredient TOMATO = null;
		public static final Ingredient KETCHUP = null;
		public static final Ingredient SAUCE = null;
		public static final Ingredient SALT = null;
		public static final Ingredient OIL = null;
		public static final Ingredient RICE = null;
		public static final Ingredient CHEESE = null;
		public static final Ingredient CHILI = null;
		public static final Ingredient SPINACH = null;
		public static final Ingredient CABBAGE = null;
		public static final Ingredient FLOUR = null;
		public static final Ingredient CORN = null;
		public static final Ingredient CREAM = null;

		/* Vanilla Ingrediant */
		public static final Ingredient APPLE = new Ingredient("apple", net.minecraft.item.Items.APPLE, FoodTypes.FRUIT, FlavorTypes.SOUR, 1.25);
		public static final Ingredient MELON = new Ingredient("melon", net.minecraft.item.Items.MELON, FoodTypes.FRUIT, FlavorTypes.SWEET, 0.85);
		public static final Ingredient PUMPKIN = new Ingredient("pumpkin", Blocks.PUMPKIN.asItem(), FoodTypes.VEGETABLE, FlavorTypes.NONE, 0.8);
		public static final Ingredient CARROT = new Ingredient("carrot", net.minecraft.item.Items.CARROT, FoodTypes.VEGETABLE, FlavorTypes.SWEET, 1.25);
		public static final Ingredient POTATO = new Ingredient("potato", net.minecraft.item.Items.POTATO, FoodTypes.VEGETABLE, FlavorTypes.NONE, 1.25);
		public static final Ingredient BEETROOT = new Ingredient("beetroot", net.minecraft.item.Items.BEETROOT, FoodTypes.VEGETABLE, FlavorTypes.SWEET, 1.2);
		public static final Ingredient BROWN_MUSHROOM = new Ingredient("brown_mushroom", Blocks.BROWN_MUSHROOM.asItem(), FoodTypes.VEGETABLE, FlavorTypes.NETHER, 1.5);
		public static final Ingredient RED_MUSHROOM = new Ingredient("red_mushroom", Blocks.RED_MUSHROOM.asItem(), FoodTypes.VEGETABLE, FlavorTypes.NETHER, 1.5);
		public static final Ingredient EGG = new Ingredient("egg", net.minecraft.item.Items.EGG, FoodTypes.NONE, FlavorTypes.NONE, 1.1);
		public static final Ingredient MILK_BUCKET = new Ingredient("milk_bucket", net.minecraft.item.Items.MILK_BUCKET, FoodTypes.NONE, FlavorTypes.NONE, 0.6);
		public static final Ingredient PORKCHOP = new Ingredient("porkchop", net.minecraft.item.Items.PORKCHOP, FoodTypes.MEAT, FlavorTypes.NONE, 0.95);
		public static final Ingredient BEEF = new Ingredient("beef", net.minecraft.item.Items.BEEF, FoodTypes.MEAT, FlavorTypes.NONE, 0.9);
		public static final Ingredient CHICKEN = new Ingredient("chicken", net.minecraft.item.Items.CHICKEN, FoodTypes.MEAT, FlavorTypes.NONE, 1.2);
		public static final Ingredient MUTTON = new Ingredient("mutton", net.minecraft.item.Items.MUTTON, FoodTypes.MEAT, FlavorTypes.NONE, 0.95);
		public static final Ingredient CHORUS_FRUIT = new Ingredient("chorus_fruit", net.minecraft.item.Items.CHORUS_FRUIT, FoodTypes.FRUIT, FlavorTypes.ENDER, 0.85);
		public static final Ingredient COCOA_BEANS = new Ingredient("cocoa_beans", net.minecraft.item.Items.COCOA_BEANS, FoodTypes.SWEET, FlavorTypes.SWEET, 2.2);
		public static final Ingredient SUGAR = new Ingredient("sugar", net.minecraft.item.Items.SUGAR, FoodTypes.NONE, FlavorTypes.SWEET, 2.5);
		public static final Ingredient WATER_BUCKET = new Ingredient("water_bucket", net.minecraft.item.Items.WATER_BUCKET, FoodTypes.NONE, FlavorTypes.NONE, 1.8);
		public static final Ingredient NETHER_WART = new Ingredient("nether_wart", net.minecraft.item.Items.NETHER_WART, FoodTypes.VEGETABLE, FlavorTypes.NETHER, 1.2);
		public static final Ingredient HONEY_BOTTLE = new Ingredient("honey_bottle", net.minecraft.item.Items.field_226638_pX_, FoodTypes.SEASONING, FlavorTypes.SWEET, 1.9);
		public static final Ingredient KELP = new Ingredient("kelp", net.minecraft.item.Items.KELP, FoodTypes.VEGETABLE, FlavorTypes.SALTY, 1.75);
		public static final Ingredient RABBIT = new Ingredient("rabbit", net.minecraft.item.Items.RABBIT, FoodTypes.MEAT, FlavorTypes.NONE, 1.1);
		public static final Ingredient SWEET_BERRIES = new Ingredient("sweet_berries", net.minecraft.item.Items.SWEET_BERRIES, FoodTypes.FRUIT, FlavorTypes.SWEET, 1.7);
		public static final Ingredient MAGMA_CREAM = new Ingredient("magma_cream", net.minecraft.item.Items.MAGMA_CREAM, FoodTypes.SWEET, FlavorTypes.NETHER, 0.8);
		public static final Ingredient GHAST_TEAR = new Ingredient("ghast_tear", net.minecraft.item.Items.GHAST_TEAR, FoodTypes.SEASONING, FlavorTypes.NETHER, 0.7);
		public static final Ingredient DRAGON_BREATH = new Ingredient("dragon_breath", net.minecraft.item.Items.DRAGON_BREATH, FoodTypes.SEASONING, FlavorTypes.ENDER, 1);
		public static final Ingredient EXPERIENCE_BOTTLE = new Ingredient("experience_bottle", net.minecraft.item.Items.EXPERIENCE_BOTTLE, FoodTypes.SEASONING, FlavorTypes.BITTER, 0.1);

		/* CookedFoods */
		public static final CookedFood BATTER = null;
		public static final CookedFood CAKE_BASE = null;
		public static final CookedFood FLAT_DOUGH = null;
		public static final CookedFood RAW_PIZZA = null;
		public static final CookedFood COOKED_PIZZA = null;
		public static final CookedFood RAW_JUICE = null;
		public static final CookedFood MIXED_JUICE = null;
		public static final CookedFood ICE_CREAM_BASE = null;
		public static final CookedFood TOAST = null;
		public static final CookedFood TOAST_SLICE = null;
		public static final CookedFood COOKED_RICE = null;
		public static final CookedFood NOODLE = null;
		public static final CookedFood ICE = null;
		
		/* Meals */
		public static final Meal CAKE = null;
		public static final Meal PIZZA = null;
		public static final Meal SANDWICH = null;
		public static final Meal ICE_CREAM = null;
		public static final Meal FRIED_RICE = null;
		public static final Meal NOODLE_SOUP = null;
		public static final Meal HONEY_TOAST = null;
		public static final Meal SALAD = null;
		public static final Meal JUICE = null;
	}

	public static DeferredRegister<Item> getRegister(){
		return IngredientRegistry.REGISTER;
	}

	public static Ingredient getIngredientByName(String nameIn){
		for(Ingredient ingredient : Ingredients.list){
			if(ingredient.getFPName().equals(nameIn))
				return ingredient;
		}
		for(Ingredient ingredient : Ingredients.cookedFoodList){
			if(ingredient.getFPName().equals(nameIn))
				return ingredient;
		}
		for(Ingredient ingredient : Ingredients.mealList){
			if(ingredient.getFPName().equals(nameIn))
				return ingredient;
		}
		return new Ingredient(FoodTypes.NONE);
	}

	public static Ingredient getIngredientByItem(Item itemIn){
		for(Ingredient ingredient : Ingredients.list){
			if(ingredient.asItem() == itemIn)
				return ingredient;
		}
		for(Ingredient ingredient : Ingredients.cookedFoodList){
			if(ingredient.asItem() == itemIn)
				return ingredient;
		}
		for(Ingredient ingredient : Ingredients.mealList){
			if(ingredient.asItem() == itemIn)
				return ingredient;
		}
		return new Ingredient(FoodTypes.NONE);
	}

	public static List<Ingredient> getIngredientsByType(FoodType foodType){
		List<Ingredient> returnList = new ArrayList<Ingredient>();
		for(Ingredient ingredient : Ingredients.list){
			if(foodType == ingredient.getFoodType()){
				returnList.add(ingredient);
			}
		}
		return returnList;
	}
}

class IngredientRegistry{
	public static final DeferredRegister<Item> REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	/* Mod Ingrediant */
	public static final RegistryObject<Item> OBJ_BUTTER = IngredientRegistry.register("butter", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, FlavorTypes.NONE, 1.1));
	public static final RegistryObject<Item> OBJ_ORANGE = IngredientRegistry.register("orange", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, FlavorTypes.SOUR, 0.95));
	public static final RegistryObject<Item> OBJ_KIWI = IngredientRegistry.register("kiwi", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, FlavorTypes.SWEET, 1));
	public static final RegistryObject<Item> OBJ_PAPAYA = IngredientRegistry.register("papaya", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, FlavorTypes.SWEET, 0.9));
	public static final RegistryObject<Item> OBJ_MANGO = IngredientRegistry.register("mango", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, FlavorTypes.SWEET, 1.05));
	public static final RegistryObject<Item> OBJ_LEMON = IngredientRegistry.register("lemon", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, FlavorTypes.SOUR, 1.1));
	public static final RegistryObject<Item> OBJ_MINT = IngredientRegistry.register("mint", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SWEET, FlavorTypes.BITTER, 1.9));
	public static final RegistryObject<Item> OBJ_FERMENTED_ENDEREYE = IngredientRegistry.register("fermented_endereye", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SWEET, FlavorTypes.ENDER, 0.9));
	public static final RegistryObject<Item> OBJ_DOUGH = IngredientRegistry.register("dough", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, FlavorTypes.NONE, 1.2));
	public static final RegistryObject<Item> OBJ_TOMATO = IngredientRegistry.register("tomato", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, FlavorTypes.SOUR, 1.1));
	public static final RegistryObject<Item> OBJ_KETCHUP = IngredientRegistry.register("ketchup", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SEASONING, FlavorTypes.SOUR, 0.8));
	public static final RegistryObject<Item> OBJ_SAUCE = IngredientRegistry.register("sauce", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SEASONING, FlavorTypes.SALTY, 0.8));
	public static final RegistryObject<Item> OBJ_SALT = IngredientRegistry.register("salt", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, FlavorTypes.SALTY, 2.1));
	public static final RegistryObject<Item> OBJ_OIL = IngredientRegistry.register("oil", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, FlavorTypes.NONE, 0.8));
	public static final RegistryObject<Item> OBJ_RICE = IngredientRegistry.register("rice", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, FlavorTypes.NONE, 2.2));
	public static final RegistryObject<Item> OBJ_CHEESE = IngredientRegistry.register("cheese", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, FlavorTypes.SALTY, 1.2));
	public static final RegistryObject<Item> OBJ_CHILI = IngredientRegistry.register("chili", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, FlavorTypes.NETHER, 0.8));
	public static final RegistryObject<Item> OBJ_SPINACH = IngredientRegistry.register("spinach", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, FlavorTypes.BITTER, 1.2));
	public static final RegistryObject<Item> OBJ_CABBAGE = IngredientRegistry.register("cabbage", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, FlavorTypes.BITTER, 1.2));
	public static final RegistryObject<Item> OBJ_FLOUR = IngredientRegistry.register("flour", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, FlavorTypes.NONE, 0.8));
	public static final RegistryObject<Item> OBJ_CORN = IngredientRegistry.register("corn", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, FlavorTypes.NONE, 0.95));
	public static final RegistryObject<Item> OBJ_CREAM = IngredientRegistry.register("cream", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, FlavorTypes.NONE, 1.2));

	/* Vanilla Ingrediant */
	/*
	public static final RegistryObject<Item> OBJ_APPLE = IngredientRegistry.register("apple", (str) -> new Ingredient(str, Items.APPLE, FoodTypes.FRUIT, FlavorTypes.SOUR, 1.25));
	public static final RegistryObject<Item> OBJ_MELON = IngredientRegistry.register("melon", (str) -> new Ingredient(str, Items.MELON, FoodTypes.FRUIT, FlavorTypes.SWEET, 0.85));
	public static final RegistryObject<Item> OBJ_PUMPKIN = IngredientRegistry.register("pumpkin", (str) -> new Ingredient(str, Blocks.PUMPKIN.asItem(), FoodTypes.VEGETABLE, FlavorTypes.NONE, 0.8));
	public static final RegistryObject<Item> OBJ_CARROT = IngredientRegistry.register("carrot", (str) -> new Ingredient(str, Items.CARROT, FoodTypes.VEGETABLE, FlavorTypes.SWEET, 1.25));
	public static final RegistryObject<Item> OBJ_POTATO = IngredientRegistry.register("potato", (str) -> new Ingredient(str, Items.POTATO, FoodTypes.VEGETABLE, FlavorTypes.NONE, 1.25));
	public static final RegistryObject<Item> OBJ_BEETROOT = IngredientRegistry.register("beetroot", (str) -> new Ingredient(str, Items.BEETROOT, FoodTypes.VEGETABLE, FlavorTypes.SWEET, 1.2));
	public static final RegistryObject<Item> OBJ_BROWN_MUSHROOM = IngredientRegistry.register("brown_mushroom", (str) -> new Ingredient(str, Blocks.BROWN_MUSHROOM.asItem(), FoodTypes.VEGETABLE, FlavorTypes.NETHER, 1.5));
	public static final RegistryObject<Item> OBJ_RED_MUSHROOM = IngredientRegistry.register("red_mushroom", (str) -> new Ingredient(str, Blocks.RED_MUSHROOM.asItem(), FoodTypes.VEGETABLE, FlavorTypes.NETHER, 1.5));
	public static final RegistryObject<Item> OBJ_EGG = IngredientRegistry.register("egg", (str) -> new Ingredient(str, Items.EGG, FoodTypes.NONE, FlavorTypes.NONE, 1.1));
	public static final RegistryObject<Item> OBJ_MILK_BUCKET = IngredientRegistry.register("milk_bucket", (str) -> new Ingredient(str, Items.MILK_BUCKET, FoodTypes.NONE, FlavorTypes.NONE, 0.6));
	public static final RegistryObject<Item> OBJ_PORKCHOP = IngredientRegistry.register("porkchop", (str) -> new Ingredient(str, Items.PORKCHOP, FoodTypes.MEAT, FlavorTypes.NONE, 0.95));
	public static final RegistryObject<Item> OBJ_BEEF = IngredientRegistry.register("beef", (str) -> new Ingredient(str, Items.BEEF, FoodTypes.MEAT, FlavorTypes.NONE, 0.9));
	public static final RegistryObject<Item> OBJ_CHICKEN = IngredientRegistry.register("chicken", (str) -> new Ingredient(str, Items.CHICKEN, FoodTypes.MEAT, FlavorTypes.NONE, 1.2));
	public static final RegistryObject<Item> OBJ_MUTTON = IngredientRegistry.register("mutton", (str) -> new Ingredient(str, Items.MUTTON, FoodTypes.MEAT, FlavorTypes.NONE, 0.95));
	public static final RegistryObject<Item> OBJ_CHORUS_FRUIT = IngredientRegistry.register("chorus_fruit", (str) -> new Ingredient(str, Items.CHORUS_FRUIT, FoodTypes.FRUIT, FlavorTypes.ENDER, 0.85));
	public static final RegistryObject<Item> OBJ_COCOA_BEANS = IngredientRegistry.register("cocoa_beans", (str) -> new Ingredient(str, Items.COCOA_BEANS, FoodTypes.SWEET, FlavorTypes.SWEET, 2.2));
	public static final RegistryObject<Item> OBJ_SUGAR = IngredientRegistry.register("sugar", (str) -> new Ingredient(str, Items.SUGAR, FoodTypes.NONE, FlavorTypes.SWEET, 2.5));
	public static final RegistryObject<Item> OBJ_WATER_BUCKET = IngredientRegistry.register("water_bucket", (str) -> new Ingredient(str, Items.WATER_BUCKET, FoodTypes.NONE, FlavorTypes.NONE, 1.8));
	public static final RegistryObject<Item> OBJ_NETHER_WART = IngredientRegistry.register("nether_wart", (str) -> new Ingredient(str, Items.NETHER_WART, FoodTypes.VEGETABLE, FlavorTypes.NETHER, 1.2));
	public static final RegistryObject<Item> OBJ_HONEY_BOTTLE = IngredientRegistry.register("honey_bottle", (str) -> new Ingredient(str, Items.field_226638_pX_, FoodTypes.SEASONING, FlavorTypes.SWEET, 1.9));
	public static final RegistryObject<Item> OBJ_KELP = IngredientRegistry.register("kelp", (str) -> new Ingredient(str, Items.KELP, FoodTypes.VEGETABLE, FlavorTypes.SALTY, 1.75));
	public static final RegistryObject<Item> OBJ_RABBIT = IngredientRegistry.register("rabbit", (str) -> new Ingredient(str, Items.RABBIT, FoodTypes.MEAT, FlavorTypes.NONE, 1.1));
	public static final RegistryObject<Item> OBJ_SWEET_BERRIES = IngredientRegistry.register("sweet_berries", (str) -> new Ingredient(str, Items.SWEET_BERRIES, FoodTypes.FRUIT, FlavorTypes.SWEET, 1.7));
	public static final RegistryObject<Item> OBJ_MAGMA_CREAM = IngredientRegistry.register("magma_cream", (str) -> new Ingredient(str, Items.MAGMA_CREAM, FoodTypes.SWEET, FlavorTypes.NETHER, 0.8));
	public static final RegistryObject<Item> OBJ_GHAST_TEAR = IngredientRegistry.register("ghast_tear", (str) -> new Ingredient(str, Items.GHAST_TEAR, FoodTypes.SEASONING, FlavorTypes.NETHER, 0.7));
	public static final RegistryObject<Item> OBJ_DRAGON_BREATH = IngredientRegistry.register("dragon_breath", (str) -> new Ingredient(str, Items.DRAGON_BREATH, FoodTypes.SEASONING, FlavorTypes.ENDER, 1));
	public static final RegistryObject<Item> OBJ_EXPERIENCE_BOTTLE = IngredientRegistry.register("experience_bottle", (str) -> new Ingredient(str, Items.EXPERIENCE_BOTTLE, FoodTypes.SEASONING, FlavorTypes.BITTER, 0.1));
	*/

	/* CookedFoods */
	public static final RegistryObject<Item> OBJ_BATTER = IngredientRegistry.register("batter", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_CAKE_BASE = IngredientRegistry.register("cake_base", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_FLAT_DOUGH = IngredientRegistry.register("flat_dough", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_RAW_PIZZA = IngredientRegistry.register("raw_pizza", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_COOKED_PIZZA = IngredientRegistry.register("cooked_pizza", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_RAW_JUICE = IngredientRegistry.register("raw_juice", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_MIXED_JUICE = IngredientRegistry.register("mixed_juice", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_ICE_CREAM_BASE = IngredientRegistry.register("ice_cream_base", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_TOAST = IngredientRegistry.register("toast", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_TOAST_SLICE = IngredientRegistry.register("toast_slice", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_COOKED_RICE = IngredientRegistry.register("cooked_rice", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_NOODLE = IngredientRegistry.register("noodle", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> OBJ_ICE = IngredientRegistry.register("ice", (str) -> new CookedFood(str));

	/* Meals */
	public static final RegistryObject<Item> OBJ_CAKE = IngredientRegistry.register("cake", (str) -> new Meal(str));
	public static final RegistryObject<Item> OBJ_PIZZA = IngredientRegistry.register("pizza", (str) -> new Meal(str));
	public static final RegistryObject<Item> OBJ_SANDWICH = IngredientRegistry.register("sandwich", (str) -> new Meal(str));
	public static final RegistryObject<Item> OBJ_ICE_CREAM = IngredientRegistry.register("ice_cream", (str) -> new Meal(str));
	public static final RegistryObject<Item> OBJ_FRIED_RICE = IngredientRegistry.register("fried_rice", (str) -> new Meal(str));
	public static final RegistryObject<Item> OBJ_NOODLE_SOUP = IngredientRegistry.register("noodle_soup", (str) -> new Meal(str));
	public static final RegistryObject<Item> OBJ_HONEY_TOAST = IngredientRegistry.register("honey_toast", (str) -> new Meal(str));
	public static final RegistryObject<Item> OBJ_SALAD = IngredientRegistry.register("salad", (str) -> new Meal(str));
	public static final RegistryObject<Item> OBJ_JUICE = IngredientRegistry.register("juice", (str) -> new Meal(str));

	private static RegistryObject<Item> register(String name, Function<String, Item> fun){
		return IngredientRegistry.REGISTER.register(name, () -> fun.apply(name));
	}
}