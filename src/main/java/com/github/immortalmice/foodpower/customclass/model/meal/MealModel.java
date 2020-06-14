package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.ModelTransformComposition;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.geometry.IModelGeometry;

@SuppressWarnings("deprecation")
public class MealModel implements IModelGeometry<MealModel>{
	private Map<String, Material> materials = new HashMap<>();

	public MealModel(Map<String, ResourceLocation> map){
		for(Map.Entry<String, ResourceLocation> entry : map.entrySet()){
			materials.put(entry.getKey(), new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, entry.getValue()));
		}
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery
			, Function<Material, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform
			, ItemOverrideList overrides, ResourceLocation modelLocation){

		TextureAtlasSprite particle = spriteGetter.apply(owner.resolveTexture("particle"));
		
		IModelTransform transformsFromModel = owner.getCombinedTransform();
		ImmutableMap<TransformType, TransformationMatrix> transformMap = transformsFromModel != null ?
                        PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(transformsFromModel, modelTransform)) :
                        PerspectiveMapWrapper.getTransforms(modelTransform);

        modelTransform = transformsFromModel != null ? new ModelTransformComposition(transformsFromModel, modelTransform) : modelTransform;

		TransformationMatrix transform = modelTransform.getRotation();

        /* Vanillad BakedItemModel but with custom MealItemOverrideList, used in store data, it'll display nothing */
		return new MealBakedModel(this.materials, spriteGetter, particle, transformMap, transform, owner.isSideLit());
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner
		, Function<ResourceLocation, IUnbakedModel> modelGetter
		, Set<Pair<String, String>> missingTextureErrors){

		return this.materials.values();
	}
}