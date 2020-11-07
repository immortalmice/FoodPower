package com.github.immortalmice.foodpower.loot;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.lists.Effects;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.google.gson.JsonObject;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class CabbageEffectModifier extends LootModifier {
	private List<Float> probabilities = new ArrayList<>();
	private List<Integer> bonus = new ArrayList<>();
	
	public CabbageEffectModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		BlockState state = context.get(LootParameters.BLOCK_STATE);
		Entity entity = context.get(LootParameters.THIS_ENTITY);
		if(state.getBlock() instanceof CropsBlock
			&& ((CropsBlock) state.getBlock()).isMaxAge(state)
			&& entity instanceof PlayerEntity){
			
			int level = ((PlayerEntity) entity).getActivePotionEffect(Effects.FoodEffects.CABBAGE_POWER).getAmplifier();
			
			generatedLoot.forEach(stack -> {
				Item item = stack.getItem();
				if(item == Items.BEETROOT
					|| item == Items.CARROT
					|| item == Items.POTATO
					|| item == Items.WHEAT
					|| item == Ingredients.Items.MINT
					|| item == Ingredients.Items.TOMATO
					|| item == Ingredients.Items.RICE
					|| item == Ingredients.Items.CHILI
					|| item == Ingredients.Items.SPINACH
					|| item == Ingredients.Items.CABBAGE
					|| item == Ingredients.Items.CORN){
					
					if(entity.world.rand.nextFloat() <= this.probabilities.get(level > 2 ? 2 : level)) {
						stack.grow(this.bonus.get(level > 2 ? 2 : level));
					}
				}
			});
		}
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<CabbageEffectModifier> {
		@Override
		public CabbageEffectModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
			CabbageEffectModifier modifier = new CabbageEffectModifier(ailootcondition);
			
			object.getAsJsonArray("probabilities").forEach(probability -> {
				modifier.probabilities.add(probability.getAsFloat());
			});;
			object.getAsJsonArray("bonus").forEach(bonus -> {
				modifier.bonus.add(bonus.getAsInt());
			});;
			return modifier;
		}
	}
}
