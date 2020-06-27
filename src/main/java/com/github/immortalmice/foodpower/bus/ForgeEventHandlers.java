package com.github.immortalmice.foodpower.bus;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.capability.classes.FPFlavorExpCapability;
import com.github.immortalmice.foodpower.customclass.capability.classes.FPPatternExpCapability;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.github.immortalmice.foodpower.handlers.CapabilityHandler;
import com.github.immortalmice.foodpower.handlers.CommandHandler;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class ForgeEventHandlers{
	private static final IEventBus BUS = MinecraftForge.EVENT_BUS;

	public static void registAllEvent(){
		ForgeEventHandlers.BUS.register(ForgeEventHandlers.class);
		ForgeEventHandlers.BUS.register(new ForgeEventHandlers());
	}

	@SubscribeEvent
	public static void onServerLoad(FMLServerStartingEvent event){
		CommandHandler.registAllCommand(event.getCommandDispatcher());
	}

	@SubscribeEvent
	public static void onLootLoad(LootTableLoadEvent event){
		/* Add crop seeds into vanilla grass drop */
		ResourceLocation eventResourceLocation = event.getName();
		if(eventResourceLocation.equals(new ResourceLocation("minecraft", "grass"))) {
			event.getTable().addPool(
				LootPool.builder().addEntry(
					TableLootEntry.builder(
						new ResourceLocation(FoodPower.MODID, "grass")))
				.build());
		}
	}

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event){
		PlayerEntity player = event.getPlayer();
		ItemStack stack = player.getHeldItem(event.getHand());
		if(stack.getItem() instanceof Meal && event.getTarget() instanceof AnimalEntity){
			if(!player.world.isRemote()){
				int level = Meal.getIngredientLevel(stack, Ingredients.Items.BUTTER);
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

	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event){
		Item item = event.getItemStack().getItem();
		Ingredient ingredient = Ingredients.getIngredientByItem(item);

		if(ingredient != null){
			Ingredient.addFoodAndFlavorTooltip(ingredient, event.getToolTip());
		}
	}

	@SubscribeEvent
	public static void addCapabilities(AttachCapabilitiesEvent<Entity> event){
		if(event.getObject() instanceof PlayerEntity){
			event.addCapability(new ResourceLocation(FoodPower.MODID, "pattern_exp_capability"), new FPPatternExpCapability.Provider());
			event.addCapability(new ResourceLocation(FoodPower.MODID, "flavor_exp_capability"), new FPFlavorExpCapability.Provider());
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event){
		/* Copy capability data to new player */
		for(Capability<?> cap : Capabilities.list){
			CapabilityHandler.copyCapabilityData(event.getOriginal(), event.getPlayer(), cap);
		}
	}
}