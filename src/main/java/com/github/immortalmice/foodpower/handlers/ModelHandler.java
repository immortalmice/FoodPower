package com.github.immortalmice.foodpower.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.boss.render.BitterBossRenderer;
import com.github.immortalmice.foodpower.boss.render.EnderBossRenderer;
import com.github.immortalmice.foodpower.boss.render.NetherBossRenderer;
import com.github.immortalmice.foodpower.boss.render.SaltyBossRenderer;
import com.github.immortalmice.foodpower.boss.render.SourBossRenderer;
import com.github.immortalmice.foodpower.boss.render.SweetBossRenderer;
import com.github.immortalmice.foodpower.lists.Bosses;
import com.github.immortalmice.foodpower.model.meal.MealModelLoader;

public class ModelHandler{
	public static void setupClient() {
		ModelHandler.registLoader();
		ModelHandler.registRendering();
	}
	
	public static void registLoader(){
		ModelLoaderRegistry.registerLoader(
			new ResourceLocation(FoodPower.MODID, "meal")
			, MealModelLoader.INSTANCE);
	}
	
	public static void registRendering() {
		RenderingRegistry.registerEntityRenderingHandler(Bosses.EntityTypes.SWEET_BOSS, SweetBossRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(Bosses.EntityTypes.BITTER_BOSS, BitterBossRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(Bosses.EntityTypes.SOUR_BOSS, SourBossRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(Bosses.EntityTypes.SALTY_BOSS, SaltyBossRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(Bosses.EntityTypes.NETHER_BOSS, NetherBossRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(Bosses.EntityTypes.ENDER_BOSS, EnderBossRenderer::new);
	}

	/* Stich texture to the atla */
	public static void registTextures(TextureStitchEvent.Pre event){
		for(ResourceLocation location : MealModelLoader.textures){
			event.addSprite(location);
		}
	}
}