package com.github.immortalmice.foodpower.customclass.model.meal;

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
		String name = modelContents.has("name") ? modelContents.get("name").getAsString() : "";

		return new MealModel(name);
	}
}