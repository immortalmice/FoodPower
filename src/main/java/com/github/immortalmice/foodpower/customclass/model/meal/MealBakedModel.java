package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.ArrayList;
import java.util.HashMap;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;

/* Do not use this baked model directly, it'll display nothing, use MealBakedModel#getNewBakedItemModel */
@SuppressWarnings("deprecation")
public class MealBakedModel extends BakedItemModel{
	private TextureAtlasSprite baseSprite = null;
	private List<TextureAtlasSprite> ingredientSprites = new ArrayList<TextureAtlasSprite>();
	private TransformationMatrix transform;
	/* Catch the result of quads, using a location combination */
	private static Map<String, ImmutableList<BakedQuad>> catche = new HashMap<String, ImmutableList<BakedQuad>>();

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
		String catcheKey = this.getCatchKeyString();

		/* Check is this sprite location combination is already baked or not  */
		if(MealBakedModel.catche.containsKey(catcheKey))
			return MealBakedModel.catche.get(catcheKey);

		ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();
		List<TextureAtlasSprite> sprites = new ArrayList<TextureAtlasSprite>();

		if(this.baseSprite != null)
			sprites.add(this.baseSprite);
		sprites.addAll(this.ingredientSprites);

		if(sprites.size() > 0){
			/* North & South Side */
			for(int ix = 0; ix <= 15; ix ++){
				for(int iy = 0; iy <= 15; iy ++){
					/* Find the last pixel not transparent in sprites, use that to build North/South quads */
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
			
			boolean isTransparent = true;

			/* Up & Down Side */
			for(int ix = 0; ix <= 15; ix ++){
				float xStart = ix / 16.0f;
				float xEnd = (ix + 1) / 16.0f;

				/* Scan from Up to Bottom, find the pixel not transparent, use that to build Top quads */
				for(int iy = 0; iy <= 15; iy ++){
					TextureAtlasSprite sprite = MealBakedModel.findLastNotTransparent(ix, iy, sprites);
					if(sprite == null){
						isTransparent = true;
						continue;
					}

					if(isTransparent){
						quads.add(this.createQuad(
							new Vec3d(xStart, (16 - iy) / 16.0f, MealBakedModel.NORTH_Z)
							, new Vec3d(xStart, (16 - iy) / 16.0f, MealBakedModel.SOUTH_Z)
							, new Vec3d(xEnd, (16 - iy) / 16.0f, MealBakedModel.SOUTH_Z)
							, new Vec3d(xEnd, (16 - iy) / 16.0f, MealBakedModel.NORTH_Z)
							, ix, ix + 1, iy, iy + 1
							, sprite, Direction.UP));

						isTransparent = false;
					}
				}
				/* Scan from Bottom to Up, find the pixel not transparent, use that to build Down quads */
				for(int iy = 15; iy >= 0; iy --){
					TextureAtlasSprite sprite = MealBakedModel.findLastNotTransparent(ix, iy, sprites);
					if(sprite == null){
						isTransparent = true;
						continue;
					}

					if(isTransparent){
						quads.add(this.createQuad(
							new Vec3d(xStart, (16 - (iy + 1)) / 16.0f, MealBakedModel.NORTH_Z)
							, new Vec3d(xEnd, (16 - (iy + 1)) / 16.0f, MealBakedModel.NORTH_Z)
							, new Vec3d(xEnd, (16 - (iy + 1)) / 16.0f, MealBakedModel.SOUTH_Z)
							, new Vec3d(xStart, (16 - (iy + 1)) / 16.0f, MealBakedModel.SOUTH_Z)
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

				/* Scan from Left to Right, find the pixel not transparent, use that to build West quads */
				for(int ix = 0; ix <= 15; ix ++){
					TextureAtlasSprite sprite = MealBakedModel.findLastNotTransparent(ix, iy, sprites);
					if(sprite == null){
						isTransparent = true;
						continue;
					}
					if(isTransparent){
						quads.add(this.createQuad(
							new Vec3d(ix / 16.0f, yStart, MealBakedModel.NORTH_Z)
							, new Vec3d(ix / 16.0f, yStart, MealBakedModel.SOUTH_Z)
							, new Vec3d(ix / 16.0f, yEnd, MealBakedModel.SOUTH_Z)
							, new Vec3d(ix / 16.0f, yEnd, MealBakedModel.NORTH_Z)
							, ix, ix + 1, iy, iy + 1
							, sprite, Direction.WEST));

						isTransparent = false;
					}
				}
				/* Scan from Right to Left, find the pixel not transparent, use that to build East quads */
				for(int ix = 15; ix >= 0; ix --){
					TextureAtlasSprite sprite = MealBakedModel.findLastNotTransparent(ix, iy, sprites);
					if(sprite == null){
						isTransparent = true;
						continue;
					}
					if(isTransparent){
						quads.add(this.createQuad(
							new Vec3d((ix + 1) / 16.0f, yStart, MealBakedModel.NORTH_Z)
							, new Vec3d((ix + 1) / 16.0f, yEnd, MealBakedModel.NORTH_Z)
							, new Vec3d((ix + 1) / 16.0f, yEnd, MealBakedModel.SOUTH_Z)
							, new Vec3d((ix + 1) / 16.0f, yStart, MealBakedModel.SOUTH_Z)
							, ix, ix + 1, iy, iy + 1
							, sprite, Direction.EAST));

						isTransparent = false;
					}
				}
			}
		}

		ImmutableList<BakedQuad> returnQuads = quads.build();
		MealBakedModel.catche.put(catcheKey, returnQuads);

		return returnQuads;
	}

	/* Give four corner, generate a quad */
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

	/* Put data into the consumer */
	private static void putVertex(IVertexConsumer consumer
		, Vec3d vec, double u, double v
		, TextureAtlasSprite sprite, Direction orientation){

		/* For each VertexFormatElement in VertexFormat of consumer  */
		ImmutableList<VertexFormatElement> elements = consumer.getVertexFormat().getElements();
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

	/* Give a BakedItemModel base on data in this, can use directly to display */
	public BakedItemModel getNewBakedItemModel(){
		return new BakedItemModel(this.genQuads(), this.particle, this.transforms, this.overrides, this.transform.isIdentity(), this.isSideLit);
	}

	/* Give a new MealBakedModel with sprites added */
	public MealBakedModel setIngredientSprites(List<TextureAtlasSprite> spritesIn){
		return new MealBakedModel(this, spritesIn);
	}

	/* Get a combination string of loactions, used in catche's key */
	private String getCatchKeyString(){
		List<String> locations = new ArrayList<String>();
		if(this.baseSprite != null)
			locations.add(this.baseSprite.getName().toString());

		for(TextureAtlasSprite sprite : this.ingredientSprites){
			locations.add(sprite.getName().toString());
		}

		String str = String.join(",", locations);
		return str;
	}
}