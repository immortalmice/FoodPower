package com.github.immortalmice.foodpower.model.meal;

import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.immortalmice.foodpower.model.meal.MealModelLoader.TypedTextures;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
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
	private ResourceLocation baseMaterial;
	private ImmutableList<TypedTextures> typedTexturesList;

	public MealModel(ResourceLocation baseMaterialIn, ImmutableList<TypedTextures> typedTexturesListIn){
		this.typedTexturesList = typedTexturesListIn;
		this.baseMaterial = baseMaterialIn;
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
		return new MealBakedModel(this.baseMaterial, this.typedTexturesList, spriteGetter, particle, transformMap, transform, owner.isSideLit());
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner
		, Function<ResourceLocation, IUnbakedModel> modelGetter
		, Set<Pair<String, String>> missingTextureErrors){

//		return this.typedTexturesList.stream().map(
//				typedTexture -> typedTexture.getTextures()
//			).flatMap(
//				textures -> textures.values().map(location -> new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, location)).stream()
//			).collect(Collectors.toList());
		return new ArrayList<>();
	}
}