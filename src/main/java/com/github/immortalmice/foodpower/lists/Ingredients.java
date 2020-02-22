package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.github.immortalmice.foodpower.lists.FoodTypes;

/** All the ingredient need to be registed will list below. */
public class Ingredients{
	public static final List<Ingredient> list = new ArrayList<Ingredient>();
	public static final List<Ingredient> vanillaList = new ArrayList<Ingredient>();
	public static final List<CookedFood> cookedFoodList = new ArrayList<CookedFood>();
	public static final List<Meal> mealFoodList = new ArrayList<Meal>();

	/** Mod Ingrediant */
	public static final Ingredient BUTTER = new Ingredient("butter", 1, 0.1f, FoodTypes.NONE, 1);
	public static final Ingredient ORANGE = new Ingredient("orange", 1, 0.1f, FoodTypes.FRUIT, 1);
	public static final Ingredient KIWI = new Ingredient("kiwi", 1, 0.1f, FoodTypes.FRUIT, 1);
	public static final Ingredient PAPAYA = new Ingredient("papaya", 1, 0.1f, FoodTypes.FRUIT, 1);
	public static final Ingredient MANGO = new Ingredient("mango", 1, 0.1f, FoodTypes.FRUIT, 1);
	public static final Ingredient LEMON = new Ingredient("lemon", 1, 0.1f, FoodTypes.FRUIT, 1);
	public static final Ingredient MINT = new Ingredient("mint", 1, 0.1f, FoodTypes.SWEET, 1.8);
	public static final Ingredient FERMENTED_ENDEREYE = new Ingredient("fermented_endereye", 1, 0.1f, FoodTypes.SWEET, 1);
	public static final Ingredient DOUGH = new Ingredient("dough", 1, 0.1f, FoodTypes.NONE, 1.2);
	public static final Ingredient TOMATO = new Ingredient("tomato", 1, 0.1f, FoodTypes.VEGETABLE, 1.1);
	public static final Ingredient KETCHUP = new Ingredient("ketchup", 1, 0.1f, FoodTypes.SEASONING, 0.8);
	public static final Ingredient SAUCE = new Ingredient("sauce", 1, 0.1f, FoodTypes.SEASONING, 0.8);
	public static final Ingredient SALT = new Ingredient("salt", 1, 0.1f, FoodTypes.NONE, 2);
	public static final Ingredient OIL = new Ingredient("oil", 1, 0.1f, FoodTypes.NONE, 0.8);
	public static final Ingredient RICE = new Ingredient("rice", 1, 0.1f, FoodTypes.NONE, 2.2);
	public static final Ingredient CHEESE = new Ingredient("cheese", 1, 0.1f, FoodTypes.NONE, 1.2);
	public static final Ingredient CHILI = new Ingredient("chili", 1, 0.1f, FoodTypes.VEGETABLE, 0.8);
	public static final Ingredient SPINACH = new Ingredient("spinach", 1, 0.1f, FoodTypes.VEGETABLE, 1);
	public static final Ingredient CABBAGE = new Ingredient("cabbage", 1, 0.1f, FoodTypes.VEGETABLE, 1);
	public static final Ingredient FLOUR = new Ingredient("flour", 1, 0.1f, FoodTypes.NONE, 0.8);
	public static final Ingredient CORN = new Ingredient("corn", 1, 0.1f, FoodTypes.VEGETABLE, 1);
	public static final Ingredient CREAM = new Ingredient("cream", 1, 0.1f, FoodTypes.NONE, 1.2);

	/** Vanilla Ingrediant */
	public static final Ingredient APPLE = new Ingredient("apple", (ItemFood)Items.APPLE, FoodTypes.FRUIT, 1);
	public static final Ingredient MELON = new Ingredient("melon", (ItemFood)Items.MELON, FoodTypes.FRUIT, 1);
	public static final Ingredient PUMPKIN = new Ingredient("pumpkin", Item.getItemFromBlock(Blocks.PUMPKIN), FoodTypes.VEGETABLE, 0.8);
	public static final Ingredient CARROT = new Ingredient("carrot", (ItemFood)Items.CARROT, FoodTypes.VEGETABLE, 1.2);
	public static final Ingredient POTATO = new Ingredient("potato", (ItemFood)Items.POTATO, FoodTypes.VEGETABLE, 1.2);
	public static final Ingredient BEETROOT = new Ingredient("beetroot", (ItemFood)Items.BEETROOT, FoodTypes.VEGETABLE, 1.2);
	public static final Ingredient BROWN_MUSHROOM = new Ingredient("brown_mushroom", Item.getItemFromBlock(Blocks.BROWN_MUSHROOM), FoodTypes.VEGETABLE, 1.4);
	public static final Ingredient RED_MUSHROOM = new Ingredient("red_mushroom", Item.getItemFromBlock(Blocks.RED_MUSHROOM), FoodTypes.VEGETABLE, 1.4);
	public static final Ingredient EGG = new Ingredient("egg", Items.EGG, FoodTypes.NONE, 1);
	public static final Ingredient MILK_BUCKET = new Ingredient("milk_bucket", Items.MILK_BUCKET, FoodTypes.NONE, 0.6);
	public static final Ingredient PORKCHOP = new Ingredient("porkchop", (ItemFood)Items.PORKCHOP, FoodTypes.MEAT, 1);
	public static final Ingredient BEEF = new Ingredient("beef", (ItemFood)Items.BEEF, FoodTypes.MEAT, 1);
	public static final Ingredient CHICKEN = new Ingredient("chicken", (ItemFood)Items.CHICKEN, FoodTypes.MEAT, 1);
	public static final Ingredient MUTTON = new Ingredient("mutton", (ItemFood)Items.MUTTON, FoodTypes.MEAT, 1);
	public static final Ingredient CHORUS_FRUIT = new Ingredient("chorus_fruit", (ItemFood)Items.CHORUS_FRUIT, FoodTypes.FRUIT, 1);
	public static final Ingredient COCOA = new Ingredient("dye_brown", Items.DYE, FoodTypes.SWEET, 2);
	public static final Ingredient SUGAR = new Ingredient("sugar", Items.SUGAR, FoodTypes.NONE, 2);
	public static final Ingredient WATER_BUCKET = new Ingredient("water_bucket", Items.WATER_BUCKET, FoodTypes.NONE, 2);
	public static final Ingredient NETHER_WART = new Ingredient("nether_wart", Items.NETHER_WART, FoodTypes.VEGETABLE, 1.2);

	/** CookedFoods */
	public static final Meal CAKE = new Meal("cake");
	public static final Meal PIZZA = new Meal("pizza");
	public static final Meal SANDWICH = new Meal("sandwich");
	public static final Meal ICE_CREAM = new Meal("ice_cream");
	public static final Meal FRIED_RICE = new Meal("fried_rice");
	public static final Meal NOODLE_SOUP = new Meal("noodle_soup");
	public static final Meal HONEY_TOAST = new Meal("honey_toast");
	public static final Meal SALAD = new Meal("salad");
	public static final Meal JUICE = new Meal("juice");

	public static final CookedFood BATTER = new CookedFood("batter");
	public static final CookedFood CAKE_BASE = new CookedFood("cake_base");
	public static final CookedFood FLAT_DOUGH = new CookedFood("flat_dough");
	public static final CookedFood RAW_PIZZA = new CookedFood("raw_pizza");
	public static final CookedFood COOKED_PIZZA = new CookedFood("cooked_pizza");
	public static final CookedFood RAW_JUICE = new CookedFood("raw_juice");
	public static final CookedFood MIXED_JUICE = new CookedFood("mixed_juice");
	public static final CookedFood ICE_CREAM_BASE = new CookedFood("ice_cream_base");
	public static final CookedFood TOAST = new CookedFood("toast");
	public static final CookedFood TOAST_SLICE = new CookedFood("toast_slice");
	public static final CookedFood COOKED_RICE = new CookedFood("cooked_rice");
	public static final CookedFood NOODLE = new CookedFood("noodle");
	public static final CookedFood ICE = new CookedFood("ice");

	public static Ingredient getIngredientByName(String nameIn){
		for(Ingredient ingredient : list){
			if(ingredient.getName() == nameIn)
				return ingredient;
		}
		for(Ingredient ingredient : vanillaList){
			if(ingredient.getName() == nameIn)
				return ingredient;
		}
		for(Ingredient ingredient : cookedFoodList){
			if(ingredient.getName() == nameIn)
				return ingredient;
		}
		for(Ingredient ingredient : mealFoodList){
			if(ingredient.getName() == nameIn)
				return ingredient;
		}
		return new Ingredient(FoodTypes.NONE);
	}
}