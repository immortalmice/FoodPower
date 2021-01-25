package com.github.immortalmice.foodpower.lists;

import java.util.function.Supplier;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.specialclass.ChefCard;
import com.github.immortalmice.foodpower.specialclass.CreamShield;
import com.github.immortalmice.foodpower.specialclass.DirtyFood;
import com.github.immortalmice.foodpower.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.specialclass.TutorialBook;
import com.github.immortalmice.foodpower.util.ReflectList;

/* All the other items in mod need to be registed will list below */
public class OtherItems{
	public static final ReflectList<ItemBase, Items> list = new ReflectList<ItemBase, Items>(ItemBase.class, Items.class, true);

	@ObjectHolder(FoodPower.MODID)
	public static class Items{
		public static final DirtyFood DIRTY_FOOD = null;
		public static final RecipeScroll RECIPE_SCROLL = null;
		public static final Item PAPAYA_SEED = null;
		public static final CreamShield CREAM_SHIELD = null;
		public static final ChefCard CHEF_CARD = null;
		public static final Item VENOM = null;
		public static final TutorialBook TUTORIAL_BOOK = null;
	}
	
	public static DeferredRegister<Item> getRegister(){
		return OtherItemsRegistry.REGISTER;
	}
}

class OtherItemsRegistry{
	public static final DeferredRegister<Item> REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	public static final RegistryObject<Item> OBJ_DIRTY_FOOD = OtherItemsRegistry.register("dirty_food", () -> new DirtyFood());
	public static final RegistryObject<Item> OBJ_RECIPE_SCROLL = OtherItemsRegistry.register("recipe_scroll", () -> new RecipeScroll());
	public static final RegistryObject<Item> OBJ_PAPAYA_SEED = OtherItemsRegistry.register("papaya_seed", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> OBJ_CREAM_SHIELD = OtherItemsRegistry.register("cream_shield", () -> new CreamShield());
	public static final RegistryObject<Item> OBJ_CHEF_CARD = OtherItemsRegistry.register("chef_card", () -> new ChefCard());
	public static final RegistryObject<Item> OBJ_VENOM = OtherItemsRegistry.register("venom", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> OBJ_TUTORIAL_BOOK = OtherItemsRegistry.register("tutorial_book", () -> new TutorialBook());

	private static RegistryObject<Item> register(String name, Supplier<Item> sup){
		return OtherItemsRegistry.REGISTER.register(name, sup);
	}
}