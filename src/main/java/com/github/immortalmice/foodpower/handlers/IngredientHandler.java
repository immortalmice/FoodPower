package com.github.immortalmice.foodpower.handlers;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.github.immortalmice.foodpower.customclass.message.classes.ShootPapayaSeedMessage;
import com.github.immortalmice.foodpower.lists.Effects.FoodEffects;
import com.github.immortalmice.foodpower.lists.Ingredients;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.LogicalSide;

public class IngredientHandler{
	public static void setupAllEffect(){
		/* Mod Ingrediants */
		Ingredients.Items.BUTTER.setInteractEffectBiConsumer((rawEvent, level) -> {
			if(rawEvent instanceof PlayerInteractEvent.EntityInteract){
				PlayerInteractEvent.EntityInteract event = (PlayerInteractEvent.EntityInteract) rawEvent;
				PlayerEntity player = event.getPlayer();
				ItemStack stack = player.getHeldItem(event.getHand());
				if(stack.getItem() instanceof Meal && event.getTarget() instanceof AnimalEntity){
					if(!player.world.isRemote()){
						AnimalEntity target = (AnimalEntity) event.getTarget();
						boolean isVaildAnimal = false;
						switch(level){
							case 3:
								isVaildAnimal = true;
							case 2:
								if(target instanceof CowEntity || target instanceof SheepEntity || target instanceof PigEntity)
									isVaildAnimal = true;
							case 1:
								if(target instanceof ChickenEntity)
									isVaildAnimal = true;
						}

						if(isVaildAnimal && target.getGrowingAge() == 0 && target.canBreed()){
							if(target instanceof TameableEntity && !((TameableEntity)target).isTamed()) return;
							if(target instanceof AbstractHorseEntity && !((AbstractHorseEntity)target).isTame()) return;

							target.setInLove(player);
							if(!player.abilities.isCreativeMode) stack.shrink(1);
						}
					}
					event.setCanceled(true);
				}
			}
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
		Ingredients.Items.PAPAYA.setInteractEffectBiConsumer((rawEvent, level) -> {
			if(rawEvent instanceof PlayerInteractEvent.LeftClickEmpty){
				FoodPower.NETWORK.sendToServer(new ShootPapayaSeedMessage());
			}
		});
		Ingredients.Items.MANGO.setMealEffectBiConsumer((effectContainer, level) -> {
			effectContainer.addEffectInstance(new EffectInstance(Effects.HASTE, (level - 1) * 1500 + 600, level - 1));
		});
		Ingredients.Items.LEMON.setInteractEffectBiConsumer((rawEvent, level) -> {
			if(rawEvent instanceof PlayerInteractEvent.RightClickBlock && rawEvent.getSide() == LogicalSide.SERVER){
				Block target = rawEvent.getWorld().getBlockState(rawEvent.getPos()).getBlock();
				if(target instanceof KitchenAppliance && ((KitchenAppliance)target).isElectrical()){
					TileEntity rawTileEntity = rawEvent.getWorld().getTileEntity(rawEvent.getPos());
					if(rawTileEntity != null){
						rawTileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent((capability) -> {
							int result = capability.receiveEnergy(IngredientHandler.LEMON_ENERGY_REWORD[level-1], false);

							if(result != 0 && !rawEvent.getPlayer().isCreative()){
								rawEvent.getPlayer().getHeldItem(rawEvent.getHand()).shrink(1);
							}

							if(result != 0){
								rawEvent.setCanceled(true);
							}
						});
					}
				}
			}
		});
		Ingredients.Items.MINT.setMealEffectBiConsumer((effectContainer, level) -> {
			effectContainer.addEffectInstance(new EffectInstance(FoodEffects.MINT_POWER, (level - 1) * 1500 + 600, level - 1));
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

	private static final int[] LEMON_ENERGY_REWORD = {10000, 50000, 500000};
}