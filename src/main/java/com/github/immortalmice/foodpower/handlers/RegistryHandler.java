package com.github.immortalmice.foodpower.handlers;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.Crops;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;
import com.github.immortalmice.foodpower.lists.TileEntitys;
import com.github.immortalmice.foodpower.lists.Trees;

public class RegistryHandler{
	private static final IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();

	public static void registAll(){
		RegistryHandler.registAllItem();
		//RegistryHandler.registAllBlock();
		//RegistryHandler.registAllContainer();
		//RegistryHandler.registAllTileEntityType();
	}
	public static void registAllItem(){
		Ingredients.getRegister().register(RegistryHandler.BUS);
		//Crops.ITEM_REGISTER.register(RegistryHandler.BUS);
	}
	public static void registAllBlock(){
		KitchenAppliances.REGISTER.register(RegistryHandler.BUS);
		Crops.BLOCK_REGISTER.register(RegistryHandler.BUS);
		Trees.REGISTER.register(RegistryHandler.BUS);
	}
	public static void registAllContainer(){
		Containers.REGISTER.register(RegistryHandler.BUS);
	}
	public static void registAllTileEntityType(){
		TileEntitys.REGISTER.register(RegistryHandler.BUS);
	}
}