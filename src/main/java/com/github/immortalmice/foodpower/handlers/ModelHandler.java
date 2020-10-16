package com.github.immortalmice.foodpower.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.model.meal.MealModelLoader;

public class ModelHandler{
	public static void registLoader(){
		ModelLoaderRegistry.registerLoader(
			new ResourceLocation(FoodPower.MODID, "meal")
			, MealModelLoader.INSTANCE);
	}

	/* Stich texture to the atla */
	public static void registTextures(TextureStitchEvent.Pre event){
		for(ResourceLocation location : MealModelLoader.textures){
			event.addSprite(location);
		}
	}
}