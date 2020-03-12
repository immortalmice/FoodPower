package com.github.immortalmice.foodpower.customclass.model.meal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.model.BakedItemModel;

@SuppressWarnings("deprecation")
public class MealBakedModel extends BakedItemModel{

	public MealBakedModel(ImmutableList<BakedQuad> quads, TextureAtlasSprite particle,
			ImmutableMap<TransformType, TransformationMatrix> transforms, ItemOverrideList overrides,
			boolean untransformed, boolean isSideLit){
		super(quads, particle, transforms, overrides, untransformed, isSideLit);
		// TODO Auto-generated constructor stub
	}
	
}