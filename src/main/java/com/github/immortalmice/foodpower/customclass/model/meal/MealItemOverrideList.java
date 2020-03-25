package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nullable;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.ItemLayerModel;

import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.google.common.collect.ImmutableList;

public class MealItemOverrideList extends ItemOverrideList{
	private Map<String, Material> materials;
	private IModelConfiguration owner;
	private ModelBakery bakery;
	private Function<Material, TextureAtlasSprite> spriteGetter;
	private IModelTransform modelTransform;
	private ItemOverrideList overrides;
	private ResourceLocation modelLocation;

	public MealItemOverrideList(MealModel mealModelIn
		, IModelConfiguration ownerIn, ModelBakery bakeryIn
		, Function<Material, TextureAtlasSprite> spriteGetterIn, IModelTransform modelTransformIn
		, ItemOverrideList overridesIn, ResourceLocation modelLocationIn){

		this.materials = mealModelIn.getMaterials();
		this.owner = ownerIn;
		this.bakery = bakeryIn;
		this.spriteGetter = spriteGetterIn;
		this.modelTransform = modelTransformIn;
		this.overrides = overridesIn;
		this.modelLocation = modelLocationIn;
	}

	/* Read NBT data from stack and choose what textures in use and merge them */
	@Override
	public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn){
		IBakedModel returnModel = model;
		if(stack.getItem() instanceof Meal){
			CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();

			List<Material> textures = new ArrayList<Material>();
			textures.add(this.materials.get("base"));

			if(nbt.contains("ingredients")){
				ListNBT list = (ListNBT)nbt.get("ingredients");
				for(INBT ele : list){
					CompoundNBT element = (CompoundNBT)ele;
					textures.add(this.materials.get(element.getString("name")));
				}
			}
			returnModel = new ItemLayerModel(ImmutableList.copyOf(textures))
				.bake(this.owner, this.bakery, this.spriteGetter, this.modelTransform, this.overrides, this.modelLocation);
		}
		return returnModel;
	}
}