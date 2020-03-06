package com.github.immortalmice.foodpower.lists;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.RegistryObject;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.github.immortalmice.foodpower.lists.FoodTypes;

/* All the ingredient need to be registed will list below. */

public class Ingredients{

	@ObjectHolder("foodpower")
	public static class Items{
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
		public static final Ingredient APPLE = null;
		public static final Ingredient MELON = null;
		public static final Ingredient PUMPKIN = null;
		public static final Ingredient CARROT = null;
		public static final Ingredient POTATO = null;
		public static final Ingredient BEETROOT = null;
		public static final Ingredient BROWN_MUSHROOM = null;
		public static final Ingredient RED_MUSHROOM = null;
		public static final Ingredient EGG = null;
		public static final Ingredient MILK_BUCKET = null;
		public static final Ingredient PORKCHOP = null;
		public static final Ingredient BEEF = null;
		public static final Ingredient CHICKEN = null;
		public static final Ingredient MUTTON = null;
		public static final Ingredient CHORUS_FRUIT = null;
		public static final Ingredient COCOA = null;
		public static final Ingredient SUGAR = null;
		public static final Ingredient WATER_BUCKET = null;
		public static final Ingredient NETHER_WART = null;

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

	private static class Lists{
		public static final List<Ingredient> list = new ArrayList<Ingredient>();
		public static final List<CookedFood> cookedFoodList = new ArrayList<CookedFood>();
		public static final List<Meal> mealList = new ArrayList<Meal>();
	}

	public static DeferredRegister<Item> getRegister(){
		return IngredientRegistry.REGISTER;
	}

	public static Ingredient getIngredientByName(String nameIn){
		for(Ingredient ingredient : Ingredients.Lists.list){
			if(ingredient.getFPName() == nameIn)
				return ingredient;
		}
		for(Ingredient ingredient : Ingredients.Lists.cookedFoodList){
			if(ingredient.getFPName() == nameIn)
				return ingredient;
		}
		for(Ingredient ingredient : Ingredients.Lists.mealList){
			if(ingredient.getFPName() == nameIn)
				return ingredient;
		}
		return new Ingredient(FoodTypes.NONE);
	}

	private static void setList(){
		if(Ingredients.Lists.list.isEmpty()
			|| Ingredients.Lists.cookedFoodList.isEmpty()
			|| Ingredients.Lists.mealList.isEmpty()){

			Ingredients.Lists.list.clear();
			Ingredients.Lists.cookedFoodList.clear();
			Ingredients.Lists.mealList.clear();

			Field[] fields = Ingredients.Items.class.getFields();
			for(Field field : fields){
				try{
					if(field.get(null) != null){
						if(field.getType() == Meal.class){
							Ingredients.Lists.mealList.add((Meal)field.get(null));
						}else if(field.getType() == CookedFood.class){
							Ingredients.Lists.cookedFoodList.add((CookedFood)field.get(null));
						}else if(field.getType() == Ingredient.class){
							Ingredients.Lists.list.add((Ingredient)field.get(null));
						}
					}else if(field.getType() == Meal.class
							|| field.getType() == CookedFood.class
							|| field.getType() == Ingredient.class){

						throw new Exception();
					}
				}catch(Exception e){
					Ingredients.Lists.list.clear();
					Ingredients.Lists.cookedFoodList.clear();
					Ingredients.Lists.mealList.clear();
				}
			}

		}
	}

	public static List<Ingredient> getIngredientList(){
		Ingredients.setList();
		return Ingredients.Lists.list;
	}
	public static List<CookedFood> getCookedFoodList(){
		Ingredients.setList();
		return Ingredients.Lists.cookedFoodList;
	}
	public static List<Meal> getMealList(){
		Ingredients.setList();
		return Ingredients.Lists.mealList;
	}

}

class IngredientRegistry{
	public static final DeferredRegister<Item> REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	/* Mod Ingrediant */
	public static final RegistryObject<Item> OBJ_BUTTER = IngredientRegistry.register("butter", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 1));
	public static final RegistryObject<Item> OBJ_ORANGE = IngredientRegistry.register("orange", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> OBJ_KIWI = IngredientRegistry.register("kiwi", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> OBJ_PAPAYA = IngredientRegistry.register("papaya", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> OBJ_MANGO = IngredientRegistry.register("mango", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> OBJ_LEMON = IngredientRegistry.register("lemon", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> OBJ_MINT = IngredientRegistry.register("mint", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SWEET, 1.8));
	public static final RegistryObject<Item> OBJ_FERMENTED_ENDEREYE = IngredientRegistry.register("fermented_endereye", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SWEET, 1));
	public static final RegistryObject<Item> OBJ_DOUGH = IngredientRegistry.register("dough", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 1.2));
	public static final RegistryObject<Item> OBJ_TOMATO = IngredientRegistry.register("tomato", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, 1.1));
	public static final RegistryObject<Item> OBJ_KETCHUP = IngredientRegistry.register("ketchup", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SEASONING, 0.8));
	public static final RegistryObject<Item> OBJ_SAUCE = IngredientRegistry.register("sauce", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SEASONING, 0.8));
	public static final RegistryObject<Item> OBJ_SALT = IngredientRegistry.register("salt", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 2));
	public static final RegistryObject<Item> OBJ_OIL = IngredientRegistry.register("oil", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 0.8));
	public static final RegistryObject<Item> OBJ_RICE = IngredientRegistry.register("rice", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 2.2));
	public static final RegistryObject<Item> OBJ_CHEESE = IngredientRegistry.register("cheese", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 1.2));
	public static final RegistryObject<Item> OBJ_CHILI = IngredientRegistry.register("chili", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, 0.8));
	public static final RegistryObject<Item> OBJ_SPINACH = IngredientRegistry.register("spinach", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, 1));
	public static final RegistryObject<Item> OBJ_CABBAGE = IngredientRegistry.register("cabbage", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, 1));
	public static final RegistryObject<Item> OBJ_FLOUR = IngredientRegistry.register("flour", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 0.8));
	public static final RegistryObject<Item> OBJ_CORN = IngredientRegistry.register("corn", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, 1));
	public static final RegistryObject<Item> OBJ_CREAM = IngredientRegistry.register("cream", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 1.2));

	/* Vanilla Ingrediant */
	public static final RegistryObject<Item> OBJ_APPLE = IngredientRegistry.register("apple", (str) -> new Ingredient(str, Items.APPLE, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> OBJ_MELON = IngredientRegistry.register("melon", (str) -> new Ingredient(str, Items.MELON, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> OBJ_PUMPKIN = IngredientRegistry.register("pumpkin", (str) -> new Ingredient(str, Blocks.PUMPKIN.asItem(), FoodTypes.VEGETABLE, 0.8));
	public static final RegistryObject<Item> OBJ_CARROT = IngredientRegistry.register("carrot", (str) -> new Ingredient(str, Items.CARROT, FoodTypes.VEGETABLE, 1.2));
	public static final RegistryObject<Item> OBJ_POTATO = IngredientRegistry.register("potato", (str) -> new Ingredient(str, Items.POTATO, FoodTypes.VEGETABLE, 1.2));
	public static final RegistryObject<Item> OBJ_BEETROOT = IngredientRegistry.register("beetroot", (str) -> new Ingredient(str, Items.BEETROOT, FoodTypes.VEGETABLE, 1.2));
	public static final RegistryObject<Item> OBJ_BROWN_MUSHROOM = IngredientRegistry.register("brown_mushroom", (str) -> new Ingredient(str, Blocks.BROWN_MUSHROOM.asItem(), FoodTypes.VEGETABLE, 1.4));
	public static final RegistryObject<Item> OBJ_RED_MUSHROOM = IngredientRegistry.register("red_mushroom", (str) -> new Ingredient(str, Blocks.RED_MUSHROOM.asItem(), FoodTypes.VEGETABLE, 1.4));
	public static final RegistryObject<Item> OBJ_EGG = IngredientRegistry.register("egg", (str) -> new Ingredient(str, Items.EGG, FoodTypes.NONE, 1));
	public static final RegistryObject<Item> OBJ_MILK_BUCKET = IngredientRegistry.register("milk_bucket", (str) -> new Ingredient(str, Items.MILK_BUCKET, FoodTypes.NONE, 0.6));
	public static final RegistryObject<Item> OBJ_PORKCHOP = IngredientRegistry.register("porkchop", (str) -> new Ingredient(str, Items.PORKCHOP, FoodTypes.MEAT, 1));
	public static final RegistryObject<Item> OBJ_BEEF = IngredientRegistry.register("beef", (str) -> new Ingredient(str, Items.BEEF, FoodTypes.MEAT, 1));
	public static final RegistryObject<Item> OBJ_CHICKEN = IngredientRegistry.register("chicken", (str) -> new Ingredient(str, Items.CHICKEN, FoodTypes.MEAT, 1));
	public static final RegistryObject<Item> OBJ_MUTTON = IngredientRegistry.register("mutton", (str) -> new Ingredient(str, Items.MUTTON, FoodTypes.MEAT, 1));
	public static final RegistryObject<Item> OBJ_CHORUS_FRUIT = IngredientRegistry.register("chorus_fruit", (str) -> new Ingredient(str, Items.CHORUS_FRUIT, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> OBJ_COCOA = IngredientRegistry.register("cocoa_beans", (str) -> new Ingredient(str, Items.COCOA_BEANS, FoodTypes.SWEET, 2));
	public static final RegistryObject<Item> OBJ_SUGAR = IngredientRegistry.register("sugar", (str) -> new Ingredient(str, Items.SUGAR, FoodTypes.NONE, 2));
	public static final RegistryObject<Item> OBJ_WATER_BUCKET = IngredientRegistry.register("water_bucket", (str) -> new Ingredient(str, Items.WATER_BUCKET, FoodTypes.NONE, 2));
	public static final RegistryObject<Item> OBJ_NETHER_WART = IngredientRegistry.register("nether_wart", (str) -> new Ingredient(str, Items.NETHER_WART, FoodTypes.VEGETABLE, 1.2));

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