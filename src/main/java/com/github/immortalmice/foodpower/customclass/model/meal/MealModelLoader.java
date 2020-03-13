package com.github.immortalmice.foodpower.customclass.model.meal;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public enum MealModelLoader implements IModelLoader<MealModel>{
	INSTANCE;

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager){
			
	}

	@Override
	public MealModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents){
		System.out.println(modelContents);

		String pathName = modelContents.has("path_name") ? modelContents.get("path_name").getAsString() : "";
		String baseFilePath = modelContents.has("base") ? modelContents.get("base").getAsString() : "";
		JsonArray parts = modelContents.has("part") ? modelContents.get("part").getAsJsonArray() : new JsonArray();

		return new MealModel(pathName, baseFilePath, parts);
	}
}