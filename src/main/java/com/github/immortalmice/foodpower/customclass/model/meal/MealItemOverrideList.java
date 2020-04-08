package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.customclass.food.Meal;

@OnlyIn(Dist.CLIENT)
public class MealItemOverrideList extends ItemOverrideList{
	private Map<String, TextureAtlasSprite> sprites = new HashMap<String, TextureAtlasSprite>();
	private Map<String, Material> materials;
	private Function<Material, TextureAtlasSprite> spriteGetter;

	public MealItemOverrideList(Map<String, Material> materialsIn
		, Function<Material, TextureAtlasSprite> spriteGetterIn){

		this.materials = materialsIn;
		this.spriteGetter = spriteGetterIn;
	}

	/* Read NBT data from stack and choose what textures in use and merge them */
	@Override
	public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn){
		this.initSprites();

		IBakedModel returnModel = model;
		if(stack.getItem() instanceof Meal && returnModel instanceof MealBakedModel){
			MealBakedModel mealModel = (MealBakedModel) returnModel;
			CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();

			if(nbt.contains("ingredients")){
				List<TextureAtlasSprite> selectedSprites = new ArrayList<TextureAtlasSprite>();
				ListNBT list = (ListNBT)nbt.get("ingredients");
				for(INBT ele : list){
					CompoundNBT element = (CompoundNBT)ele;
					String ingredientName = element.getString("name");
					if(this.sprites.containsKey(ingredientName)){
						selectedSprites.add(this.sprites.get(ingredientName));
					}
				}
				/* Get a new model with sprites passed in */
				mealModel = mealModel.setIngredientSprites(selectedSprites);
			}
			return mealModel.getNewBakedItemModel();
		}
		return returnModel;
	}

	/* Check materials is missing texture or not, then put in to this.sprites */
	private void initSprites(){
		for(String name : this.materials.keySet()){
			Material material = this.materials.get(name);
			TextureAtlasSprite sprite = this.spriteGetter.apply(material);
			if(!sprite.getName().equals(MissingTextureSprite.getLocation())){
				sprites.put(name, sprite);
			}
		}
	}
}