package com.github.immortalmice.foodpower.handlers;

import org.apache.commons.lang3.tuple.Pair;

import com.github.immortalmice.foodpower.FoodPower;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigHandler {
	public static final ClientConfig CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;
	
	static {
		final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		CLIENT = specPair.getLeft();
		CLIENT_SPEC = specPair.getRight();
	}
	
	public static void registConfigs() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
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
}
