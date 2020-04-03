package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;
import com.google.common.collect.ImmutableList;
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
import net.minecraftforge.client.model.BakedItemModel;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.ModelTransformComposition;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.handlers.ModelHandler;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

@SuppressWarnings("deprecation")
public class MealModel implements IModelGeometry<MealModel>{
	private String name;
	private Map<String, Material> materials = new HashMap<>();

	public MealModel(String nameIn){
		this.name = nameIn;
		this.init();
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery
			, Function<Material, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform
			, ItemOverrideList overrides, ResourceLocation modelLocation){

		TextureAtlasSprite particle = spriteGetter.apply(owner.resolveTexture("particle"));
		TransformationMatrix transform = modelTransform.func_225615_b_();

		IModelTransform transformsFromModel = owner.getCombinedTransform();
		ImmutableMap<TransformType, TransformationMatrix> transformMap = transformsFromModel != null ?
                        PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(transformsFromModel, modelTransform)) :
                        PerspectiveMapWrapper.getTransforms(modelTransform);

        /* Vanillad BakedItemModel but with custom MealItemOverrideList */
		return new BakedItemModel(ImmutableList.of(), particle, transformMap
			, new MealItemOverrideList(this, owner, bakery, spriteGetter, modelTransform, overrides, modelLocation)
			, transform.isIdentity(), owner.isSideLit());
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner
		, Function<ResourceLocation, IUnbakedModel> modelGetter
		, Set<Pair<String, String>> missingTextureErrors){

		return this.materials.values();
	}

	public Map<String, Material> getMaterials(){
		return this.materials;
	}

	/* Init materials, capture all texture may use in the map */
	private void init(){
		CookingPattern pattern = CookingPatterns.getPatternByName(this.name);
		if(pattern == null) return;
		this.materials.clear();
		this.materials.put("base"
			, new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, ModelHandler.locationGen(this.name, "base")));
		for(Ingredient ingredient : pattern.getAllPossibleIngredients()){
			this.materials.put(ingredient.getFPName()
				, new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, ModelHandler.locationGen(this.name, ingredient.getFPName())));
		}
	}
}