package com.github.immortalmice.foodpower.model.meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

/* Used in read json of meals */
public enum MealModelLoader implements IModelLoader<MealModel>{
	INSTANCE;

	public static final List<ResourceLocation> textures = new ArrayList<ResourceLocation>();

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager){
			
	}

	@Override
	public MealModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents){
		List<TypedTextures> typedTexturesList = new ArrayList<>();
		ResourceLocation baseLocation = new ResourceLocation("");
		
		if(modelContents.has("ingredients")){
			JsonObject ingredientJsonObject = modelContents.getAsJsonObject("ingredients");
			baseLocation = new ResourceLocation(ingredientJsonObject.get("base").getAsString());
			MealModelLoader.textures.add(baseLocation);
			
			JsonArray parts = ingredientJsonObject.get("parts").getAsJsonArray();
			for(JsonElement element : parts){
				TypedTextures typedTextures = new TypedTextures((JsonObject) element);
				typedTexturesList.add(typedTextures);
				
				for(Entry<String, ResourceLocation> entry : typedTextures.getTextures().entrySet()){
					MealModelLoader.textures.add(entry.getValue());
				}
			}
		}
		
		return new MealModel(baseLocation, ImmutableList.copyOf(typedTexturesList));
	}
	
	public static class TypedTextures{
		private final String type;
		private final ImmutableMap<String, ResourceLocation> textures;
		
		private TypedTextures(JsonObject elementIn){
			this.type = elementIn.get("type").getAsString();
			
			Map<String, ResourceLocation> map = new HashMap<>();
			JsonObject jsonObject = elementIn.get("textures").getAsJsonObject();
			for(Entry<String, JsonElement> entry : jsonObject.entrySet()){
				ResourceLocation location = new ResourceLocation(entry.getValue().getAsString());
				map.put(entry.getKey(), location);
			}
			
			this.textures = ImmutableMap.copyOf(map);
		}

		public String getType(){
			return type;
		}

		public ImmutableMap<String, ResourceLocation> getTextures(){
			return textures;
		}
		
		@Nullable
		public TextureAtlasSprite getSprite(String name, Function<Material, TextureAtlasSprite> spriteGetter){
			ResourceLocation location = this.textures.get(name);
			@SuppressWarnings("deprecation")
			Material material = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, location);
            TextureAtlasSprite sprite = material != null ? spriteGetter.apply(material) : null;
            if(sprite != null && !sprite.getName().equals(MissingTextureSprite.getLocation())){
                return sprite;
            }
			return null;
		}
	}
}