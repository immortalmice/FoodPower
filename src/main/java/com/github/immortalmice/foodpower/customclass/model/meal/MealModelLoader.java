package com.github.immortalmice.foodpower.customclass.model.meal;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public enum MealModelLoader implements IModelLoader<MealModel>{
	INSTANCE;

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager){
		// TODO Auto-generated method stub
		
	}

	@Override
	public MealModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents){
		System.out.println(modelContents);
		// TODO Auto-generated method stub
		return new MealModel();
	}
}