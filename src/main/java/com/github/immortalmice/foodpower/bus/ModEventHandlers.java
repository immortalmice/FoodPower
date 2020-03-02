package com.github.immortalmice.foodpower.bus;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.github.immortalmice.foodpower.bus.ForgeEventHandlers;
import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.Messages;
import com.github.immortalmice.foodpower.handlers.BiomeAddTreeHandler;

public class ModEventHandlers{
	private static final IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();

	/* Regist All Event On Mod Event Bus (Static & Non-Static) */
	public static void registAllEvent(){
		ModEventHandlers.BUS.register(ModEventHandlers.class);
		ModEventHandlers.BUS.register(new ModEventHandlers());
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event){
		ForgeEventHandlers.registAllEvent();
		BiomeAddTreeHandler.setup();
		Messages.registAllMessage();
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event){
		Containers.registAllScreen();
	}
}