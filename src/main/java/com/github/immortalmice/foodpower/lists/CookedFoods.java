package com.github.immortalmice.foodpower.lists;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.customclass.util.ReflectList;

public class CookedFoods{
	public static final ReflectList<CookedFood, Items> list = new ReflectList<CookedFood, Items>(CookedFood.class, Items.class, null);

	@ObjectHolder(FoodPower.MODID)
	public static class Items{
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
	}

	public static DeferredRegister<Item> getRegister(){
		return CookedFoodRegistry.REGISTER;
	}
}

class CookedFoodRegistry{
	public static final DeferredRegister<Item> REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	/* CookedFoods */
	public static final RegistryObject<Item> OBJ_BATTER = CookedFoodRegistry.register("batter", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_CAKE_BASE = CookedFoodRegistry.register("cake_base", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_FLAT_DOUGH = CookedFoodRegistry.register("flat_dough", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_RAW_PIZZA = CookedFoodRegistry.register("raw_pizza", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_COOKED_PIZZA = CookedFoodRegistry.register("cooked_pizza", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_RAW_JUICE = CookedFoodRegistry.register("raw_juice", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_MIXED_JUICE = CookedFoodRegistry.register("mixed_juice", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_ICE_CREAM_BASE = CookedFoodRegistry.register("ice_cream_base", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_TOAST = CookedFoodRegistry.register("toast", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_TOAST_SLICE = CookedFoodRegistry.register("toast_slice", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_COOKED_RICE = CookedFoodRegistry.register("cooked_rice", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_NOODLE = CookedFoodRegistry.register("noodle", () -> new CookedFood());
	public static final RegistryObject<Item> OBJ_ICE = CookedFoodRegistry.register("ice", () -> new CookedFood());

	private static RegistryObject<Item> register(String name, Supplier<Item> sup){
		return CookedFoodRegistry.REGISTER.register(name, sup);
	}
}