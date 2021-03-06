package com.github.immortalmice.foodpower.lists;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.food.Meal;
import com.github.immortalmice.foodpower.util.ReflectList;

public class Meals{
	public static final ReflectList<Meal, Items> list = new ReflectList<Meal, Items>(Meal.class, Items.class);

	@ObjectHolder(FoodPower.MODID)
	public static class Items{
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
		return MealRegistry.REGISTER;
	}
}

class MealRegistry{
	public static final DeferredRegister<Item> REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	/* Meals */
	public static final RegistryObject<Item> OBJ_CAKE = MealRegistry.register("cake", () -> new Meal());
	public static final RegistryObject<Item> OBJ_PIZZA = MealRegistry.register("pizza", () -> new Meal());
	public static final RegistryObject<Item> OBJ_SANDWICH = MealRegistry.register("sandwich", () -> new Meal());
	public static final RegistryObject<Item> OBJ_ICE_CREAM = MealRegistry.register("ice_cream", () -> new Meal());
	public static final RegistryObject<Item> OBJ_FRIED_RICE = MealRegistry.register("fried_rice", () -> new Meal());
	public static final RegistryObject<Item> OBJ_NOODLE_SOUP = MealRegistry.register("noodle_soup", () -> new Meal());
	public static final RegistryObject<Item> OBJ_HONEY_TOAST = MealRegistry.register("honey_toast", () -> new Meal());
	public static final RegistryObject<Item> OBJ_SALAD = MealRegistry.register("salad", () -> new Meal());
	public static final RegistryObject<Item> OBJ_JUICE = MealRegistry.register("juice", () -> new Meal());

	private static RegistryObject<Item> register(String name, Supplier<Item> sup){
		return MealRegistry.REGISTER.register(name, sup);
	}
}