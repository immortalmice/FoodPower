package com.github.immortalmice.foodpower.lists;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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

/* All the other items in mod need to be registed will list below */
public class OtherItems{

	@ObjectHolder("foodpower")
	public static class Items{
		public static final DirtyFood DIRTY_FOOD = null;
		public static final RecipeScroll RECIPE_SCROLL = null;	
	}

	/* Construct lists when first time getList() called. */
	private static class Lists{
		public static final List<ItemBase> list = new ArrayList<ItemBase>();
	}

	public static DeferredRegister<Item> getRegister(){
		return OtherItemsRegistry.REGISTER;
	}

	/* If called before ObjectHolder worked, list will be empty */
	public static List<ItemBase> getList(){
		if(OtherItems.Lists.list.isEmpty()){
			Field[] fields = OtherItems.Items.class.getFields();
			for(Field field : fields){
				try{
					if(field.get(null) != null){
						if(field.getType().isAssignableFrom(ItemBase.class)){
							OtherItems.Lists.list.add((ItemBase)field.get(null));
						}
					}else if(field.getType() == ItemBase.class){
						throw new Exception();
					}
				}catch(Exception e){
					OtherItems.Lists.list.clear();
				}
			}
		}
		return OtherItems.Lists.list;
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