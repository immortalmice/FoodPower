package com.github.immortalmice.foodpower.bus;

import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.Messages;
import com.github.immortalmice.foodpower.handlers.BiomeHandler;
import com.github.immortalmice.foodpower.handlers.CapabilityHandler;
import com.github.immortalmice.foodpower.handlers.ConfigHandler;
import com.github.immortalmice.foodpower.handlers.IngredientHandler;
import com.github.immortalmice.foodpower.handlers.ModelHandler;
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
		CapabilityHandler.registAllCapabilities();
		Messages.registAllMessage();
		BiomeHandler.setup();
		IngredientHandler.setupAllEffect();
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event){
		Containers.registAllScreen();
		RenderHandler.setupClient();
		ModelHandler.registLoader();
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onTextureStitch(TextureStitchEvent.Pre event){
		if(event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)){
			ModelHandler.registTextures(event);
		}
	}
	
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
		if(event.getConfig().getSpec() == ConfigHandler.CLIENT_SPEC) {
			ConfigHandler.CLIENT.bake();
		}else if(event.getConfig().getSpec() == ConfigHandler.SERVER_SPEC) {
			ConfigHandler.SERVER.bake();
		}
	}
}