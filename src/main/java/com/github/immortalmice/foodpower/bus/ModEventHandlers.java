package com.github.immortalmice.foodpower.bus;

import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.Messages;
import com.github.immortalmice.foodpower.handlers.BiomeHandler;
import com.github.immortalmice.foodpower.handlers.CapabilityHandler;
import com.github.immortalmice.foodpower.handlers.EffectHandler;
import com.github.immortalmice.foodpower.handlers.ModelHandler;
import com.github.immortalmice.foodpower.handlers.RenderHandler;
import com.github.immortalmice.foodpower.handlers.TextureHandler;

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
		Messages.registAllMessage();
		CapabilityHandler.registAllCapabilities();
		BiomeHandler.setup();
		EffectHandler.setup();
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event){
		Containers.registAllScreen();
		RenderHandler.setup();
		ModelHandler.registLoader();
	}

	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event){
		if(event.getMap().func_229223_g_().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)){
			ModelHandler.registTextures(event);
		}
		if(event.getMap().func_229223_g_().equals(new ResourceLocation("textures/atlas/mob_effects.png"))){
			TextureHandler.deleteAllFoodEffectTexture(event);
		}
	}
}