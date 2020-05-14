package com.github.immortalmice.foodpower.handlers;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.CookedFoods;
import com.github.immortalmice.foodpower.lists.Crops;
import com.github.immortalmice.foodpower.lists.Effects;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;
import com.github.immortalmice.foodpower.lists.Meals;
import com.github.immortalmice.foodpower.lists.OtherBlocks;
import com.github.immortalmice.foodpower.lists.OtherItems;
import com.github.immortalmice.foodpower.lists.TileEntitys;
import com.github.immortalmice.foodpower.lists.Trees;

/* Regist things is ForgeRegistryEntry */
public class RegistryHandler{
	private static final IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();

	public static void registAll(){
		RegistryHandler.registAllItem();
		RegistryHandler.registAllBlock();
		RegistryHandler.registAllContainer();
		RegistryHandler.registAllEffect();
		//RegistryHandler.registAllTileEntityType();
	}
	public static void registAllItem(){
		Ingredients.getRegister().register(RegistryHandler.BUS);
		CookedFoods.getRegister().register(RegistryHandler.BUS);
		Meals.getRegister().register(RegistryHandler.BUS);
		KitchenAppliances.getItemRegister().register(RegistryHandler.BUS);
		Crops.getItemRegister().register(RegistryHandler.BUS);
		Trees.getItemRegister().register(RegistryHandler.BUS);
		OtherItems.getRegister().register(RegistryHandler.BUS);
		OtherBlocks.getItemRegister().register(RegistryHandler.BUS);
	}
	public static void registAllBlock(){
		KitchenAppliances.getBlockRegister().register(RegistryHandler.BUS);
		Crops.getBlockRegister().register(RegistryHandler.BUS);
		Trees.getBlockRegister().register(RegistryHandler.BUS);
		OtherBlocks.getBlockRegister().register(RegistryHandler.BUS);
	}
	public static void registAllContainer(){
		Containers.REGISTER.register(RegistryHandler.BUS);
	}
	public static void registAllEffect(){
		Effects.getRegister().register(RegistryHandler.BUS);
	}
	public static void registAllTileEntityType(){
		TileEntitys.REGISTER.register(RegistryHandler.BUS);
	}
}