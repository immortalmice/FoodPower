package com.github.immortalmice.foodpower.handlers;

import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.model.meal.MealModelLoader;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

@OnlyIn(Dist.CLIENT)
public class ModelHandler{
	public static void registLoader(){
		ModelLoaderRegistry.registerLoader(
			new ResourceLocation(FoodPower.MODID, "meal")
			, MealModelLoader.INSTANCE);
	}

	/* Stich texture to the atla */
	public static void registTextures(TextureStitchEvent.Pre event){
		List<CookingPattern> mealList = CookingPatterns.list;

		for(CookingPattern pattern : mealList){
			String name = pattern.getName();
			event.addSprite(ModelHandler.locationGen(name, "base"));
			for(Ingredient ingredient : pattern.getAllPossibleIngredients()){
				event.addSprite(ModelHandler.locationGen(name, ingredient.getFPName()));
			}
		}
	}

	public static ResourceLocation locationGen(String patternName, String elementName){
		return new ResourceLocation(FoodPower.MODID, "meals/" + patternName + "/" + patternName + "_" + elementName);
	}
}