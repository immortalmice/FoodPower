package com.github.immortalmice.foodpower.model.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.food.Meal;
import com.github.immortalmice.foodpower.model.meal.MealModelLoader.TypedTextures;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;

public class MealItemOverrideList extends ItemOverrideList{
	private ImmutableList<TypedTextures> materials;
	private Function<Material, TextureAtlasSprite> spriteGetter;

	public MealItemOverrideList(
		ImmutableList<TypedTextures> materialsIn
		, Function<Material, TextureAtlasSprite> spriteGetterIn){
		
		this.materials = materialsIn;
		this.spriteGetter = spriteGetterIn;
	}

	/* Read NBT data from stack and choose what textures in use and merge them */
	@Override
	public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn){
		IBakedModel returnModel = model;
		if(stack.getItem() instanceof Meal && returnModel instanceof MealBakedModel){
			MealBakedModel mealModel = (MealBakedModel) returnModel;
			CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();

			if(nbt.contains("ingredients")){
				List<TextureAtlasSprite> selectedSprites = new ArrayList<TextureAtlasSprite>();
				List<TypedTextures> muttableTypedTextures = Lists.newArrayList(this.materials);
				ListNBT list = (ListNBT) nbt.get("ingredients");
				for(INBT ele : list){
					CompoundNBT element = (CompoundNBT)ele;
					String ingredientName = element.getString("name");
					
					for(TypedTextures typedTextures : muttableTypedTextures){
						TextureAtlasSprite sprite = typedTextures.getSprite(ingredientName, this.spriteGetter);
						if(sprite != null){
							muttableTypedTextures.remove(typedTextures);
							selectedSprites.add(sprite);
							break;
						}
					}
				}
				/* Get a new model with sprites passed in */
				mealModel = mealModel.setIngredientSprites(selectedSprites);
			}
			return mealModel.getNewBakedItemModel();
		}
		return returnModel;
	}
}