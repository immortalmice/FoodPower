package com.github.immortalmice.foodpower.handlers;

import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.model.meal.MealModelLoader;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.Ingredients;

@OnlyIn(Dist.CLIENT)
public class ModelHandler{
	public static void registLoader(){
		ModelLoaderRegistry.registerLoader(
			new ResourceLocation(FoodPower.MODID, "meal")
			, MealModelLoader.INSTANCE);
	}

	public static void registTextures(TextureStitchEvent.Pre event){
		List<CookingPattern> mealList = CookingPatterns.list;

		for(CookingPattern pattern : mealList){
			String name = pattern.getName();
			event.addSprite(ModelHandler.locationGen(name, "base"));
			List<FoodType> ftList = pattern.getFoodTypes();
			List<Ingredient> ingredientList = Ingredients.getIngredientsByTypes(ftList);
			for(Ingredient ingredient : ingredientList){
				event.addSprite(ModelHandler.locationGen(name, ingredient.getFPName()));
			}
		}
	}

	public static ResourceLocation locationGen(String patternName, String elementName){
		return new ResourceLocation(FoodPower.MODID, "meals/" + patternName + "/" + patternName + "_" + elementName);
	}
}