package com.github.immortalmice.foodpower.lists;

import java.util.function.Supplier;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.customclass.specialclass.DirtyFood;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.customclass.util.ReflectList;

/* All the other items in mod need to be registed will list below */
public class OtherItems{
	public static final ReflectList<ItemBase, Items> list = new ReflectList<ItemBase, Items>(ItemBase.class, Items.class, null, true);

	@ObjectHolder(FoodPower.MODID)
	public static class Items{
		public static final DirtyFood DIRTY_FOOD = null;
		public static final RecipeScroll RECIPE_SCROLL = null;	
	}
	
	public static DeferredRegister<Item> getRegister(){
		return OtherItemsRegistry.REGISTER;
	}
}

class OtherItemsRegistry{
	public static final DeferredRegister<Item> REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	public static final RegistryObject<Item> OBJ_DIRTY_FOOD = OtherItemsRegistry.register("dirty_food", () -> new DirtyFood());
	public static final RegistryObject<Item> OBJ_RECIPE_SCROLL = OtherItemsRegistry.register("recipe_scroll", () -> new RecipeScroll());

	private static RegistryObject<Item> register(String name, Supplier<Item> sup){
		return OtherItemsRegistry.REGISTER.register(name, sup);
	}
}