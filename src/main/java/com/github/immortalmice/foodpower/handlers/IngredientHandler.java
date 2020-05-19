package com.github.immortalmice.foodpower.handlers;

import com.github.immortalmice.foodpower.lists.Ingredients;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class IngredientHandler{
	public static void setupAllEffect(){
		/* Mod Ingrediants */
		Ingredients.Items.BUTTER.setMealEffectBiConsumer((effectContainer, level) -> {
			/* This effect handle by ForgeEventHandlers#onEntityInteract, nothing to do here */
		});
		Ingredients.Items.ORANGE.setMealEffectBiConsumer((effectContainer, level) -> {
			switch(level){
				case 2:
					effectContainer.addExtraEffectDurationTick(127 * 20);
					break;
				case 3:
					effectContainer.addExtraEffectDurationTick(255 * 20);
			}
		});
		Ingredients.Items.KIWI.setMealEffectBiConsumer((effectContainer, level) -> {
			effectContainer.addEffectInstance(new EffectInstance(Effects.HEALTH_BOOST, (level - 1) * 1500 + 600, level - 1));
			if(level == 3){
				effectContainer.addExtraBehavior(() -> {
					effectContainer.getEntityLiving().heal(2.0f);
				});
			}
		});
		Ingredients.Items.PAPAYA.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.MANGO.setMealEffectBiConsumer((effectContainer, level) -> {
			effectContainer.addEffectInstance(new EffectInstance(Effects.HASTE, (level - 1) * 1500 + 600, level - 1));
		});
		Ingredients.Items.LEMON.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.MINT.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.FERMENTED_ENDEREYE.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.DOUGH.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.TOMATO.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.KETCHUP.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.SAUCE.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.SALT.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.OIL.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.RICE.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.CHEESE.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.CHILI.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.SPINACH.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.CABBAGE.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.FLOUR.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.CORN.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.CREAM.setMealEffectBiConsumer((effectContainer, level) -> {

		});

		/* Vanilla Ingrediants */
		Ingredients.Items.APPLE.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.MELON.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.PUMPKIN.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.CARROT.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.POTATO.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.BEETROOT.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.BROWN_MUSHROOM.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.RED_MUSHROOM.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.EGG.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.MILK_BUCKET.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.PORKCHOP.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.BEEF.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.CHICKEN.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.MUTTON.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.CHORUS_FRUIT.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.COCOA_BEANS.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.SUGAR.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.WATER_BUCKET.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.NETHER_WART.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.HONEY_BOTTLE.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.KELP.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.RABBIT.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.SWEET_BERRIES.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.MAGMA_CREAM.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.GHAST_TEAR.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.DRAGON_BREATH.setMealEffectBiConsumer((effectContainer, level) -> {

		});
		Ingredients.Items.EXPERIENCE_BOTTLE.setMealEffectBiConsumer((effectContainer, level) -> {

		});
	}
}