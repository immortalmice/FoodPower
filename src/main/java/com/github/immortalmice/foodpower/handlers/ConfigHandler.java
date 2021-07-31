package com.github.immortalmice.foodpower.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import com.google.common.base.CaseFormat;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.util.LevelPointConverter;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
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
		
		public boolean doShowFlavorEffect;
		public final BooleanValue doShowFlavorEffectValue;
		
		public ClientConfig(ForgeConfigSpec.Builder builder) {
			this.doShowFoodEffectBubbleValue = builder
				.comment("Do food effect show bubbles animation when applied to player.")
				.translation(FoodPower.MODID + ".config.do_show_food_effect_bubble")
				.define("doShowFoodEffectBubble", true);
			
			this.doShowFlavorEffectValue = builder
				.comment("Do flavor effect show in player's inventory & HUD.")
				.translation(FoodPower.MODID + ".config.do_show_flavor_effect")
				.define("doShowFlavorEffect", false);
		}
		
		public void bake() {
			this.doShowFoodEffectBubble = this.doShowFoodEffectBubbleValue.get();
			this.doShowFlavorEffect = this.doShowFlavorEffectValue.get();
		}
	}
	
	public static class ServerConfig {
		private static final List<String> ingredients = Arrays.asList(
			"butter", "orange", "kiwi", "papaya", "mango", "lemon", "mint", "fermented_endereye", "dough", "tomato", "ketchup", "sauce", "salt", "oil", "rice", "cheese", "chili", "spinach", "cabbage", "flour", "corn", "cream", "apple", "melon", "pumpking", "carrot", "potato", "beetroot", "brown_mushroom", "red_mushroom", "egg", "milk_bucket", "porkchop", "beef", "chicken", "mutton", "chorus_fruit", "cocoa_beans", "sugar", "water_bucket", "nether_wart", "honey_bottle", "kelp", "rabbit", "sweet_berries", "magma_cream", "ghast_tear", "dragon_breath", "experience_bottle"
		);
		
		public Map<String, Boolean> disables = new HashMap<>();
		public final Map<String, BooleanValue> disableValues = new HashMap<>();
		
		public int patternExpBase;
		public double patternExpFactor;
		public IntValue patternExpBaseValue;
		public DoubleValue patternExpFactorValue;
		
		public int flavorExpBase;
		public double flavorExpFactor;
		public IntValue flavorExpBaseValue;
		public DoubleValue flavorExpFactorValue;
		
		public boolean canSummonBoss;
		public BooleanValue canSummonBossValue;
		
		public ServerConfig(ForgeConfigSpec.Builder builder) {
			builder.push("Capability levels");
			this.patternExpBaseValue = builder
				.comment("The points consum by pattern level 1.")
				.defineInRange("patternExpBase", 10, 0, 100000);
			this.patternExpFactorValue = builder
				.comment("The factor applied to points that consum by new pattern level.")
				.defineInRange("patternExpFactor", 1.1d, 1, 1000);
			this.flavorExpBaseValue = builder
				.comment("The points consum by flavor level 1.")
				.defineInRange("flavorExpBase", 5, 0, 100000);
			this.flavorExpFactorValue = builder
				.comment("The factor applied to points that consum by new flavor level.")
				.defineInRange("flavorExpFactor", 1.1d, 1, 1000);
			builder.pop();
			
			builder.push("Disable ingredient effects");
			ServerConfig.ingredients.forEach(ingredient -> {
				disableValues.put(ingredient, builder
					.comment("")
					.translation(FoodPower.MODID + ".config.disable_" + ingredient + "_effect")
					.define("disable" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, ingredient) + "Effect", false)
				);
			});
			builder.pop();
			
			builder.push("Other");
			this.canSummonBossValue = builder
				.comment("Can player summon bosses.")
				.define("canSummonBoss", true);
			builder.pop();
		}
		
		public void bake() {
			this.disableValues.forEach((ingredient, value) -> {
				this.disables.put(ingredient, value.get());
			});
			
			this.patternExpBase = this.patternExpBaseValue.get();
			this.patternExpFactor = this.patternExpFactorValue.get();
			LevelPointConverter.PATTERN_CONVERTER = new LevelPointConverter(this.patternExpBase, (float) this.patternExpFactor);
			
			this.flavorExpBase = this.flavorExpBaseValue.get();
			this.flavorExpFactor = this.flavorExpFactorValue.get();
			LevelPointConverter.FLAVOR_CONVERTER = new LevelPointConverter(this.flavorExpBase, (float) this.flavorExpFactor);
			
			this.canSummonBoss = this.canSummonBossValue.get();
		}
	}
}
