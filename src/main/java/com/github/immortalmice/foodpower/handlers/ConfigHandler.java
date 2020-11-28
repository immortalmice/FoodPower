package com.github.immortalmice.foodpower.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import com.google.common.base.CaseFormat;

import com.github.immortalmice.foodpower.FoodPower;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigHandler {
	public static final ClientConfig CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;
	
	public static final ServerConfig SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;
	
	static {
		final Pair<ClientConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		CLIENT = clientSpecPair.getLeft();
		CLIENT_SPEC = clientSpecPair.getRight();
		
		final Pair<ServerConfig, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		SERVER = serverSpecPair.getLeft();
		SERVER_SPEC = serverSpecPair.getRight();
	}
	
	public static void registConfigs() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.SERVER_SPEC);
	}
	
	public static class ClientConfig {
		public boolean doShowFoodEffectBubble;
		public final BooleanValue doShowFoodEffectBubbleValue;
		
		public ClientConfig(ForgeConfigSpec.Builder builder) {
			this.doShowFoodEffectBubbleValue = builder
				.comment("Do food effect show bubbles animation when applied to player.")
				.translation(FoodPower.MODID + ".config.do_show_food_effect_bubble")
				.define("doShowFoodEffectBubble", true);
		}
		
		public void bake() {
			this.doShowFoodEffectBubble = this.doShowFoodEffectBubbleValue.get();
		}
	}
	
	public static class ServerConfig {
		private static final List<String> ingredients = Arrays.asList(
			"butter", "orange", "kiwi", "papaya", "mango", "lemon", "mint", "fermented_endereye", "dough", "tomato", "ketchup", "sauce", "salt", "oil", "rice", "cheese", "chili", "spinach", "cabbage", "flour", "corn", "cream", "apple", "melon", "pumpking", "carrot", "potato", "beetroot", "brown_mushroom", "red_mushroom", "egg", "milk_bucket", "porkchop", "beef", "chicken", "mutton", "chorus_fruit", "cocoa_beans", "sugar", "water_bucket", "nether_wart", "honey_bottle", "kelp", "rabbit", "sweet_berries", "magma_cream", "ghast_tear", "dragon_breath", "experience_bottle"
		);
		
		public Map<String, Boolean> disables = new HashMap<>();
		public final Map<String, BooleanValue> disableValues = new HashMap<>();
		
		public ServerConfig(ForgeConfigSpec.Builder builder) {
			builder.push("Disable ingredient effects");
			ServerConfig.ingredients.forEach(ingredient -> {
				disableValues.put(ingredient, builder
					.comment("")
					.translation(FoodPower.MODID + ".config.disable_" + ingredient + "_effect")
					.define("disable" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, ingredient) + "Effect", false)
				);
			});
			builder.pop();
		}
		
		public void bake() {
			this.disableValues.forEach((ingredient, value) -> {
				this.disables.put(ingredient, value.get());
			});
		}
	}
}
