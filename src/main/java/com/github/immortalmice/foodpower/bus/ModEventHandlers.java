package com.github.immortalmice.foodpower.bus;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.bus.ForgeEventHandlers;
import com.github.immortalmice.foodpower.customclass.model.meal.MealModelLoader;
import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.Messages;
import com.github.immortalmice.foodpower.handlers.BiomeHandler;
import com.github.immortalmice.foodpower.handlers.EffectHandler;
import com.github.immortalmice.foodpower.handlers.RenderHandler;

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
		BiomeHandler.setup();
		Messages.registAllMessage();
		EffectHandler.setup();
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event){
		Containers.registAllScreen();
		RenderHandler.setup();
		ModelLoaderRegistry.registerLoader(new ResourceLocation(FoodPower.MODID, "meal"), MealModelLoader.INSTANCE);
	}
}