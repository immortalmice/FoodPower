package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraftforge.client.model.BakedItemModel;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.TRSRTransformer;
import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("deprecation")
public class MealBakedModel extends BakedItemModel{
	private TextureAtlasSprite baseSprite = null;
	private List<TextureAtlasSprite> ingredientSprites = new ArrayList<TextureAtlasSprite>();
	private TransformationMatrix transform;

	private static float NORTH_Z = 7.496f / 16f;
	private static float SOUTH_Z = 8.504f / 16f;

	private static float COLOR_R = 1.0f;
	private static float COLOR_G = 1.0f;
	private static float COLOR_B = 1.0f;

	public MealBakedModel(Map<String, Material> materials
		, Function<Material, TextureAtlasSprite> spriteGetter, TextureAtlasSprite particle
		, ImmutableMap<TransformType, TransformationMatrix> transformMap
		, TransformationMatrix transformIn, boolean isSideLit){
		super(ImmutableList.of(), particle, transformMap, new MealItemOverrideList(materials, spriteGetter), transformIn.isIdentity(), isSideLit);

		this.transform = transformIn;

		if(materials.containsKey("base")){
			TextureAtlasSprite sprite = spriteGetter.apply(materials.get("base"));
			if(!sprite.getName().equals(MissingTextureSprite.getLocation())){
				this.baseSprite = sprite;
			}
		}
	}

	private MealBakedModel(MealBakedModel originalModel, List<TextureAtlasSprite> spritesIn){
		super(ImmutableList.of(), originalModel.particle, originalModel.transforms, originalModel.overrides, originalModel.transform.isIdentity(), originalModel.isSideLit);

		this.baseSprite = originalModel.baseSprite;
		this.ingredientSprites = spritesIn;
		this.transform = originalModel.transform;
	}

	private ImmutableList<BakedQuad> genQuads(){
		ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();
		List<TextureAtlasSprite> sprites = new ArrayList<TextureAtlasSprite>();

		if(this.baseSprite != null)
			sprites.add(this.baseSprite);
		sprites.addAll(this.ingredientSprites);

		if(sprites.size() > 0){
			/* North & South Side */
			for(int ix = 0; ix <= 15; ix ++){
				for(int iy = 0; iy <= 15; iy ++){
					TextureAtlasSprite sprite = MealBakedModel.findLastNotTransparent(ix, iy, sprites);
					if(sprite == null) continue;

					float xStart = ix / 16.0f;
					float xEnd = (ix + 1) / 16.0f;

					float yStart = (16 - (iy + 1)) / 16.0f;
					float yEnd = (16 - iy) / 16.0f;

					quads.add(this.createQuad(
						new Vec3d(xStart, yStart, MealBakedModel.NORTH_Z)
						, new Vec3d(xStart, yEnd, MealBakedModel.NORTH_Z)
						, new Vec3d(xEnd, yEnd, MealBakedModel.NORTH_Z)
						, new Vec3d(xEnd, yStart, MealBakedModel.NORTH_Z)
						, ix, ix + 1, iy, iy + 1
						, sprite, Direction.NORTH));

					quads.add(this.createQuad(
						new Vec3d(xStart, yStart, MealBakedModel.SOUTH_Z)
						, new Vec3d(xEnd, yStart, MealBakedModel.SOUTH_Z)
						, new Vec3d(xEnd, yEnd, MealBakedModel.SOUTH_Z)
						, new Vec3d(xStart, yEnd, MealBakedModel.SOUTH_Z)
						, ix, ix + 1, iy, iy + 1
						, sprite, Direction.SOUTH));
				}
			}

			float zStart = 1.0f - MealBakedModel.NORTH_Z;
			float zEnd = 1.0f - MealBakedModel.SOUTH_Z;
			boolean isTransparent = true;

			/* Up & Down Side */
			for(int ix = 0; ix <= 15; ix ++){
				float xStart = ix / 16.0f;
				float xEnd = (ix + 1) / 16.0f;

				for(int iy = 0; iy <= 15; iy ++){
					TextureAtlasSprite sprite = MealBakedModel.findLastNotTransparent(ix, iy, sprites);
					if(sprite == null){
						isTransparent = true;
						continue;
					}

					if(isTransparent){
						quads.add(this.createQuad(
							new Vec3d(xStart, (16 - iy) / 16.0f, zStart)
							, new Vec3d(xStart, (16 - iy) / 16.0f, zEnd)
							, new Vec3d(xEnd, (16 - iy) / 16.0f, zEnd)
							, new Vec3d(xEnd, (16 - iy) / 16.0f, zStart)
							, ix, ix + 1, iy, iy + 1
							, sprite, Direction.UP));

						isTransparent = false;
					}
				}
				for(int iy = 15; iy >= 0; iy --){
					TextureAtlasSprite sprite = MealBakedModel.findLastNotTransparent(ix, iy, sprites);
					if(sprite == null){
						isTransparent = true;
						continue;
					}

					if(isTransparent){
						quads.add(this.createQuad(
							new Vec3d(xStart, (16 - (iy + 1)) / 16.0f, zStart)
							, new Vec3d(xEnd, (16 - (iy + 1)) / 16.0f, zStart)
							, new Vec3d(xEnd, (16 - (iy + 1)) / 16.0f, zEnd)
							, new Vec3d(xStart, (16 - (iy + 1)) / 16.0f, zEnd)
							, ix, ix + 1, iy, iy + 1
							, sprite, Direction.DOWN));

						isTransparent = false;
					}
				}
			}

			/* West & East Side */
			for(int iy = 0; iy <= 15; iy ++){
				float yStart = (16 - (iy + 1)) / 16.0f;
				float yEnd = (16 - iy) / 16.0f;

				for(int ix = 0; ix <= 15; ix ++){
					TextureAtlasSprite sprite = MealBakedModel.findLastNotTransparent(ix, iy, sprites);
					if(sprite == null){
						isTransparent = true;
						continue;
					}
					if(isTransparent){
						quads.add(this.createQuad(
							new Vec3d(ix / 16.0f, yStart, zStart)
							, new Vec3d(ix / 16.0f, yStart, zEnd)
							, new Vec3d(ix / 16.0f, yEnd, zEnd)
							, new Vec3d(ix / 16.0f, yEnd, zStart)
							, ix, ix + 1, iy, iy + 1
							, sprite, Direction.WEST));

						isTransparent = false;
					}
				}
				for(int ix = 15; ix >= 0; ix --){
					TextureAtlasSprite sprite = MealBakedModel.findLastNotTransparent(ix, iy, sprites);
					if(sprite == null){
						isTransparent = true;
						continue;
					}
					if(isTransparent){
						quads.add(this.createQuad(
							new Vec3d((ix + 1) / 16.0f, yStart, zStart)
							, new Vec3d((ix + 1) / 16.0f, yEnd, zStart)
							, new Vec3d((ix + 1) / 16.0f, yEnd, zEnd)
							, new Vec3d((ix + 1) / 16.0f, yStart, zEnd)
							, ix, ix + 1, iy, iy + 1
							, sprite, Direction.EAST));

						isTransparent = false;
					}
				}
			}
		}

		return quads.build();
	}

	private BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4
		, int xStart, int xEnd, int yStart, int yEnd, TextureAtlasSprite sprite
		, Direction orientation){

		BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
		IVertexConsumer consumer = !this.transform.isIdentity() ? new TRSRTransformer(builder, this.transform) : builder;

		builder.setQuadOrientation(orientation);

		MealBakedModel.putVertex(consumer, v1, xStart, yEnd, sprite, orientation);
		MealBakedModel.putVertex(consumer, v2, xStart, yStart, sprite, orientation);
		MealBakedModel.putVertex(consumer, v3, xEnd, yStart, sprite, orientation);
		MealBakedModel.putVertex(consumer, v4, xEnd, yEnd, sprite, orientation);

		return builder.build();
	}

	private static void putVertex(IVertexConsumer consumer
		, Vec3d vec, double u, double v
		, TextureAtlasSprite sprite, Direction orientation){

		/* For each VertexFormatElement in VertexFormat of consumer  */
		ImmutableList<VertexFormatElement> elements = consumer.getVertexFormat().func_227894_c_();
		for(int e = 0; e <= elements.size() - 1; e ++){
			VertexFormatElement element = elements.get(e);
			switch(element.getUsage()){
				case POSITION :
					consumer.put(e, (float) vec.x, (float) vec.y, (float) vec.z, 1.0f);
					break;
				case COLOR :
					consumer.put(e, MealBakedModel.COLOR_R, MealBakedModel.COLOR_G, MealBakedModel.COLOR_B, 1.0f);
					break;
				case NORMAL :
					consumer.put(e, (float) orientation.getXOffset(), (float) orientation.getYOffset(), (float) orientation.getZOffset(), 0.0f);
					break;
				case UV :
					if(element.getIndex() == 0){
						float fu = sprite.getInterpolatedU(u);
                        float fv = sprite.getInterpolatedV(v);
                        consumer.put(e, fu, fv, 0.0f, 1.0f);
						break;
                    }
				default :
					consumer.put(e);
					break;
			}
		}
	}

	/* Find the last sprite not transparent in sprites with given position */
	@Nullable
	private static TextureAtlasSprite findLastNotTransparent(int x, int y, List<TextureAtlasSprite> sprites){
		for(int spriteIndex = sprites.size()-1; spriteIndex >= 0; spriteIndex --){
			TextureAtlasSprite sprite = sprites.get(spriteIndex);
			if(!sprite.isPixelTransparent(0, x, y)){
				return sprite;
			}
		}
		return null;
	}

	public BakedItemModel getNewBakedItemModel(){
		return new BakedItemModel(this.genQuads(), this.particle, this.transforms, this.overrides, this.transform.isIdentity(), this.isSideLit);
	}

	public MealBakedModel setIngredientSprites(List<TextureAtlasSprite> spritesIn){
		return new MealBakedModel(this, spritesIn);
	}
}