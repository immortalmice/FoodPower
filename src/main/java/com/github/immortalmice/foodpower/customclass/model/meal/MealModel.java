package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.handlers.ModelHandler;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.model.BakedQuad;
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
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelTransformComposition;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.geometry.IModelGeometry;

@SuppressWarnings("deprecation")
public class MealModel implements IModelGeometry<MealModel>{
	private String name;
	private Map<String, Material> materials = new HashMap<>();
	private Map<String, ImmutableList<BakedQuad>> mapQuads = new HashMap<>();

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

		ImmutableList.Builder<BakedQuad> quads = new ImmutableList.Builder<BakedQuad>();
		for(String key : materials.keySet()){
			mapQuads.put(key, ItemLayerModel.getQuadsForSprite(0
				, spriteGetter.apply(materials.get(key))
				, transform));
			quads.addAll(mapQuads.get(key));
		}

		return new MealBakedModel(quads.build(), particle, Maps.immutableEnumMap(transformMap), transform.isIdentity(), owner.isSideLit(), this);
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner
		, Function<ResourceLocation, IUnbakedModel> modelGetter
		, Set<Pair<String, String>> missingTextureErrors){

		return this.materials.values();
	}

	public Map<String, ImmutableList<BakedQuad>> getMapQuads(){
		return this.mapQuads;
	}

	private void init(){
		CookingPattern pattern = CookingPatterns.getPatternByName(this.name);
		this.materials.clear();
		this.materials.put("base", new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, ModelHandler.locationGen(this.name, "base")));
		List<FoodType> ftList = pattern.getFoodTypes();
		List<Ingredient> ingredientList = Ingredients.getIngredientsByTypes(ftList);
		for(Ingredient ingredient : ingredientList){
			Material m = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, ModelHandler.locationGen(this.name, ingredient.getFPName()));
			this.materials.put(ingredient.getFPName(), m);
		}
	}
}