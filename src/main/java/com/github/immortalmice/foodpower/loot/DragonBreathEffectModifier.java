package com.github.immortalmice.foodpower.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.immortalmice.foodpower.lists.Effects;
import com.google.gson.JsonObject;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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

public class DragonBreathEffectModifier extends LootModifier {
	private List<Float> probabilities = new ArrayList<>();
	private static Map<EntityType<?>, Item> SKULL_MAP = new HashMap<>();
	
	static {
		SKULL_MAP.put(EntityType.ZOMBIE, Items.ZOMBIE_HEAD);
		SKULL_MAP.put(EntityType.SKELETON, Items.SKELETON_SKULL);
		SKULL_MAP.put(EntityType.CREEPER, Items.CREEPER_HEAD);
		SKULL_MAP.put(EntityType.WITHER_SKELETON, Items.WITHER_SKELETON_SKULL);
	}
	
	public DragonBreathEffectModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		Entity entity = context.get(LootParameters.THIS_ENTITY);
		Entity player = context.get(LootParameters.KILLER_ENTITY);
		Item skull = DragonBreathEffectModifier.SKULL_MAP.get(entity.getType());
		if(skull != null
			&& !generatedLoot.stream().filter(stack -> stack.getItem() == skull).findFirst().isPresent()
			&& player instanceof PlayerEntity) {
			
			int level = ((PlayerEntity) entity).getActivePotionEffect(Effects.FoodEffects.DRAGON_BREATH_POWER).getAmplifier();
			if(entity.world.rand.nextFloat() <= this.probabilities.get(level > 2 ? 2 : level)) {
				generatedLoot.add(new ItemStack(skull));
			}
		}
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<DragonBreathEffectModifier> {
		@Override
		public DragonBreathEffectModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
			DragonBreathEffectModifier modifier = new DragonBreathEffectModifier(ailootcondition);
			
			object.getAsJsonArray("probabilities").forEach(probability -> {
				modifier.probabilities.add(probability.getAsFloat());
			});;
			return modifier;
		}

	}
}
