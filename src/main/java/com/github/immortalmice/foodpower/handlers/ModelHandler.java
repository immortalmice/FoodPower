package com.github.immortalmice.foodpower.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.model.meal.MealModelLoader;

@OnlyIn(Dist.CLIENT)
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