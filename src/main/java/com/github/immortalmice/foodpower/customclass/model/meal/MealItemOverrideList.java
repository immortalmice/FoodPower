package com.github.immortalmice.foodpower.customclass.model.meal;

import java.util.Map;
import javax.annotation.Nullable;
import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;

public class MealItemOverrideList extends ItemOverrideList{
	private Map<String, ImmutableList<BakedQuad>> mapQuads;
	public MealItemOverrideList(MealModel mealModelIn){
		this.mapQuads = mealModelIn.getMapQuads();
	}
	@Override
	public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn){
		IBakedModel returnModel = model;
		if(model instanceof MealBakedModel){
			CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();
			ImmutableList.Builder<BakedQuad> quads = new ImmutableList.Builder<BakedQuad>();
			quads.addAll(mapQuads.get("base"));
			if(nbt.contains("ingredients")){
				ListNBT list = (ListNBT)nbt.get("ingredients");
				for(INBT ele : list){
					CompoundNBT element = (CompoundNBT)ele;
					quads.addAll(mapQuads.get(element.getString("name")));
				}
			}
			returnModel = ((MealBakedModel)model).getNewModelWithSelectedQuads(quads.build(), this);
		}
		return returnModel;
	}
}