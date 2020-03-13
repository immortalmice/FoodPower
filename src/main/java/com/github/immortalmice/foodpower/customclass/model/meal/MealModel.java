package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.geometry.IModelGeometry;

@SuppressWarnings("deprecation")
public class MealModel implements IModelGeometry<MealModel>{
	private String pathName, baseFilePath;
	private JsonArray parts;

	public MealModel(String pathNameIn, String baseFilePathIn, JsonArray partsIn){
		this.pathName = pathNameIn;
		this.baseFilePath = baseFilePathIn;
		this.parts = partsIn;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery
			, Function<Material, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform
			, ItemOverrideList overrides, ResourceLocation modelLocation){

		System.out.println(owner.resolveTexture("base"));

		TransformationMatrix transform = modelTransform.func_225615_b_();
		TextureAtlasSprite particle = spriteGetter.apply(owner.resolveTexture("particle"));
		ImmutableMap<TransformType, TransformationMatrix> map = PerspectiveMapWrapper.getTransforms(modelTransform);
		// TODO Auto-generated method stub
		return new MealBakedModel(ImmutableList.of(), particle, map, overrides, transform.isIdentity(), owner.isSideLit());
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner
		, Function<ResourceLocation, IUnbakedModel> modelGetter
		, Set<Pair<String, String>> missingTextureErrors){

		Set<Material> texs = Sets.newHashSet();

		texs.add(owner.resolveTexture("base"));
		return texs;
	}
	
}