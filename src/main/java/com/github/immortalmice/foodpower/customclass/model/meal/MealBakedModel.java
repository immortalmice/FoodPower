package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.BakedItemModel;

@SuppressWarnings("deprecation")
public class MealBakedModel extends BakedItemModel{

	private boolean untransformed;
	public MealBakedModel(ImmutableList<BakedQuad> quads, TextureAtlasSprite particle,
			ImmutableMap<TransformType, TransformationMatrix> transforms,
			boolean untransformedIn, boolean isSideLit, MealModel mealModelIn){
		super(quads, particle, transforms, new MealItemOverrideList(mealModelIn), untransformedIn, isSideLit);
		this.untransformed = untransformedIn;
	}

	protected BakedItemModel getNewModelWithSelectedQuads(ImmutableList<BakedQuad> quadsIn, MealItemOverrideList overrides){
		return new BakedItemModel(quadsIn, this.particle, this.transforms, overrides, this.untransformed, this.isSideLit);
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand){
		return ImmutableList.of();
	}
}