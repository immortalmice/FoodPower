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
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.github.immortalmice.foodpower.lists.FoodTypes;

/* All the ingredient need to be registed will list below. */
@ObjectHolder("foodpower")
public class Ingredients{

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

	static{
		Field[] fields = Ingredients.class.getFields();
		for(Field field : fields){
			try{
				if(field.getType() == Ingredient.class){
					Lists.list.add((Ingredient)field.get(null));
				}else if(field.getType() == CookedFood.class){
					Lists.list.add((CookedFood)field.get(null));
				}else if(field.getType() == Meal.class){
					Lists.list.add((Meal)field.get(null));
				}
			}catch(Exception e){

			}
		}
	}

	public static DeferredRegister<Item> getRegister(){
		return Registry.REGISTER;
	}

	public static Ingredient getIngredientByName(String nameIn){
		for(Ingredient ingredient : Lists.list){
			if(ingredient.getFPName() == nameIn)
				return ingredient;
		}
		for(Ingredient ingredient : Lists.cookedFoodList){
			if(ingredient.getFPName() == nameIn)
				return ingredient;
		}
		for(Ingredient ingredient : Lists.mealFoodList){
			if(ingredient.getFPName() == nameIn)
				return ingredient;
		}
		return new Ingredient(FoodTypes.NONE);
	}


}

class Registry{
	public static final DeferredRegister<Item> REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	/* Mod Ingrediant */
	public static final RegistryObject<Item> BUTTER = Registry.register("butter", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 1));
	public static final RegistryObject<Item> ORANGE = Registry.register("orange", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> KIWI = Registry.register("kiwi", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> PAPAYA = Registry.register("papaya", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> MANGO = Registry.register("mango", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> LEMON = Registry.register("lemon", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> MINT = Registry.register("mint", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SWEET, 1.8));
	public static final RegistryObject<Item> FERMENTED_ENDEREYE = Registry.register("fermented_endereye", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SWEET, 1));
	public static final RegistryObject<Item> DOUGH = Registry.register("dough", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 1.2));
	public static final RegistryObject<Item> TOMATO = Registry.register("tomato", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, 1.1));
	public static final RegistryObject<Item> KETCHUP = Registry.register("ketchup", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SEASONING, 0.8));
	public static final RegistryObject<Item> SAUCE = Registry.register("sauce", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.SEASONING, 0.8));
	public static final RegistryObject<Item> SALT = Registry.register("salt", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 2));
	public static final RegistryObject<Item> OIL = Registry.register("oil", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 0.8));
	public static final RegistryObject<Item> RICE = Registry.register("rice", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 2.2));
	public static final RegistryObject<Item> CHEESE = Registry.register("cheese", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 1.2));
	public static final RegistryObject<Item> CHILI = Registry.register("chili", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, 0.8));
	public static final RegistryObject<Item> SPINACH = Registry.register("spinach", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, 1));
	public static final RegistryObject<Item> CABBAGE = Registry.register("cabbage", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, 1));
	public static final RegistryObject<Item> FLOUR = Registry.register("flour", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 0.8));
	public static final RegistryObject<Item> CORN = Registry.register("corn", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.VEGETABLE, 1));
	public static final RegistryObject<Item> CREAM = Registry.register("cream", (str) -> new Ingredient(str, 1, 0.1f, FoodTypes.NONE, 1.2));

	/* Vanilla Ingrediant */
	public static final RegistryObject<Item> APPLE = Registry.register("apple", (str) -> new Ingredient(str, Items.APPLE, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> MELON = Registry.register("melon", (str) -> new Ingredient(str, Items.MELON, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> PUMPKIN = Registry.register("pumpkin", (str) -> new Ingredient(str, Blocks.PUMPKIN.asItem(), FoodTypes.VEGETABLE, 0.8));
	public static final RegistryObject<Item> CARROT = Registry.register("carrot", (str) -> new Ingredient(str, Items.CARROT, FoodTypes.VEGETABLE, 1.2));
	public static final RegistryObject<Item> POTATO = Registry.register("potato", (str) -> new Ingredient(str, Items.POTATO, FoodTypes.VEGETABLE, 1.2));
	public static final RegistryObject<Item> BEETROOT = Registry.register("beetroot", (str) -> new Ingredient(str, Items.BEETROOT, FoodTypes.VEGETABLE, 1.2));
	public static final RegistryObject<Item> BROWN_MUSHROOM = Registry.register("brown_mushroom", (str) -> new Ingredient(str, Blocks.BROWN_MUSHROOM.asItem(), FoodTypes.VEGETABLE, 1.4));
	public static final RegistryObject<Item> RED_MUSHROOM = Registry.register("red_mushroom", (str) -> new Ingredient(str, Blocks.RED_MUSHROOM.asItem(), FoodTypes.VEGETABLE, 1.4));
	public static final RegistryObject<Item> EGG = Registry.register("egg", (str) -> new Ingredient(str, Items.EGG, FoodTypes.NONE, 1));
	public static final RegistryObject<Item> MILK_BUCKET = Registry.register("milk_bucket", (str) -> new Ingredient(str, Items.MILK_BUCKET, FoodTypes.NONE, 0.6));
	public static final RegistryObject<Item> PORKCHOP = Registry.register("porkchop", (str) -> new Ingredient(str, Items.PORKCHOP, FoodTypes.MEAT, 1));
	public static final RegistryObject<Item> BEEF = Registry.register("beef", (str) -> new Ingredient(str, Items.BEEF, FoodTypes.MEAT, 1));
	public static final RegistryObject<Item> CHICKEN = Registry.register("chicken", (str) -> new Ingredient(str, Items.CHICKEN, FoodTypes.MEAT, 1));
	public static final RegistryObject<Item> MUTTON = Registry.register("mutton", (str) -> new Ingredient(str, Items.MUTTON, FoodTypes.MEAT, 1));
	public static final RegistryObject<Item> CHORUS_FRUIT = Registry.register("chorus_fruit", (str) -> new Ingredient(str, Items.CHORUS_FRUIT, FoodTypes.FRUIT, 1));
	public static final RegistryObject<Item> COCOA = Registry.register("cocoa_beans", (str) -> new Ingredient(str, Items.COCOA_BEANS, FoodTypes.SWEET, 2));
	public static final RegistryObject<Item> SUGAR = Registry.register("sugar", (str) -> new Ingredient(str, Items.SUGAR, FoodTypes.NONE, 2));
	public static final RegistryObject<Item> WATER_BUCKET = Registry.register("water_bucket", (str) -> new Ingredient(str, Items.WATER_BUCKET, FoodTypes.NONE, 2));
	public static final RegistryObject<Item> NETHER_WART = Registry.register("nether_wart", (str) -> new Ingredient(str, Items.NETHER_WART, FoodTypes.VEGETABLE, 1.2));

	/* CookedFoods */
	public static final RegistryObject<Item> BATTER = Registry.register("batter", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> CAKE_BASE = Registry.register("cake_base", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> FLAT_DOUGH = Registry.register("flat_dough", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> RAW_PIZZA = Registry.register("raw_pizza", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> COOKED_PIZZA = Registry.register("cooked_pizza", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> RAW_JUICE = Registry.register("raw_juice", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> MIXED_JUICE = Registry.register("mixed_juice", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> ICE_CREAM_BASE = Registry.register("ice_cream_base", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> TOAST = Registry.register("toast", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> TOAST_SLICE = Registry.register("toast_slice", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> COOKED_RICE = Registry.register("cooked_rice", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> NOODLE = Registry.register("noodle", (str) -> new CookedFood(str));
	public static final RegistryObject<Item> ICE = Registry.register("ice", (str) -> new CookedFood(str));

	/* Meals */
	public static final RegistryObject<Item> CAKE = Registry.register("cake", (str) -> new Meal(str));
	public static final RegistryObject<Item> PIZZA = Registry.register("pizza", (str) -> new Meal(str));
	public static final RegistryObject<Item> SANDWICH = Registry.register("sandwich", (str) -> new Meal(str));
	public static final RegistryObject<Item> ICE_CREAM = Registry.register("ice_cream", (str) -> new Meal(str));
	public static final RegistryObject<Item> FRIED_RICE = Registry.register("fried_rice", (str) -> new Meal(str));
	public static final RegistryObject<Item> NOODLE_SOUP = Registry.register("noodle_soup", (str) -> new Meal(str));
	public static final RegistryObject<Item> HONEY_TOAST = Registry.register("honey_toast", (str) -> new Meal(str));
	public static final RegistryObject<Item> SALAD = Registry.register("salad", (str) -> new Meal(str));
	public static final RegistryObject<Item> JUICE = Registry.register("juice", (str) -> new Meal(str));

	private static RegistryObject<Item> register(String name, Function<String, Item> fun){
		return Registry.REGISTER.register(name, () -> fun.apply(name));
	}
}

class Lists{
	public static final List<Ingredient> list = new ArrayList<Ingredient>();
	public static final List<CookedFood> cookedFoodList = new ArrayList<CookedFood>();
	public static final List<Meal> mealFoodList = new ArrayList<Meal>();
}