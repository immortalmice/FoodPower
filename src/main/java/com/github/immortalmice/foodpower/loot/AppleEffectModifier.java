package com.github.immortalmice.foodpower.loot;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.lists.Effects;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.google.gson.JsonObject;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
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

public class AppleEffectModifier extends LootModifier {
	private List<Float> probabilities = new ArrayList<>();
	private List<Integer> bonus = new ArrayList<>();
	
	public AppleEffectModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		BlockState state = context.get(LootParameters.BLOCK_STATE);
		Entity entity = context.get(LootParameters.THIS_ENTITY);
		if(state.getBlock() instanceof LeavesBlock
			&& entity instanceof PlayerEntity) {
			
			int level = ((PlayerEntity) entity).getActivePotionEffect(Effects.FoodEffects.APPLE_POWER).getAmplifier();
			
			generatedLoot.forEach(stack -> {
				Item item = stack.getItem();
				if(item == Items.APPLE
					|| item == Ingredients.Items.ORANGE
					|| item == Ingredients.Items.KIWI
					|| item == Ingredients.Items.PAPAYA
					|| item == Ingredients.Items.MANGO
					|| item == Ingredients.Items.LEMON){
					
					if(entity.world.rand.nextFloat() <= this.probabilities.get(level > 2 ? 2 : level)) {
						stack.grow(this.bonus.get(level > 2 ? 2 : level));
					}
				}
			});
		}
		
		return generatedLoot;
	}
	
	public static class Serializer extends GlobalLootModifierSerializer<AppleEffectModifier> {
		@Override
		public AppleEffectModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
			AppleEffectModifier modifier = new AppleEffectModifier(ailootcondition);
			
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
