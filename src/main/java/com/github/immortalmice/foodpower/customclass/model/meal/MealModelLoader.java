package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/* Used in read json of meals */
@OnlyIn(Dist.CLIENT)
public enum MealModelLoader implements IModelLoader<MealModel>{
	INSTANCE;

	public static final List<ResourceLocation> textures = new ArrayList<ResourceLocation>();

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager){
			
	}

	@Override
	public MealModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents){
		Map<String, ResourceLocation> map = new HashMap<String, ResourceLocation>();
		if(modelContents.has("ingredients")){
			JsonObject section = modelContents.getAsJsonObject("ingredients");
			for(Entry<String, JsonElement> entry : section.entrySet()){
				ResourceLocation location = new ResourceLocation(entry.getValue().getAsString());
				map.put(entry.getKey(), location);
				MealModelLoader.textures.add(location);
			}
		}

		return new MealModel(map);
	}
}