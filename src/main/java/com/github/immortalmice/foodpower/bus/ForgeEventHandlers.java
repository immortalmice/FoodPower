package com.github.immortalmice.foodpower.bus;

import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.capability.implement.FPFlavorExpCapability;
import com.github.immortalmice.foodpower.capability.implement.FPPatternExpCapability;
import com.github.immortalmice.foodpower.food.Ingredient;
import com.github.immortalmice.foodpower.food.Meal;
import com.github.immortalmice.foodpower.handlers.CapabilityHandler;
import com.github.immortalmice.foodpower.handlers.CommandHandler;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.Effects.FoodEffects;
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
		final String[] modifyLootTables = new String[]{
			"grass", "ice", "packed_ice", "blue_ice"
		};
		
		for(String name : modifyLootTables){
			ResourceLocation eventResourceLocation = event.getName();
			if(eventResourceLocation.equals(new ResourceLocation("minecraft", "blocks/" + name))) {
				event.getTable().addPool(
					LootPool.builder().addEntry(
						TableLootEntry.builder(
							new ResourceLocation(FoodPower.MODID, "inject/" + name)))
					.build());
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent event){
		ItemStack stack = event.getPlayer().getHeldItem(event.getHand());
		if(stack.getItem() instanceof Meal){
			ListNBT nbt = Meal.getIngredientsListNBT(stack);
			List<Ingredient> ingrdients = Meal.getIngredients(nbt);
			for(Ingredient ingredient : ingrdients){
				ingredient.applyInteractEffect(event, Meal.getIngredientLevel(nbt, ingredient));
			}
		}
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onItemTooltip(ItemTooltipEvent event){
		Item item = event.getItemStack().getItem();
		Ingredient ingredient = Ingredients.getIngredientByItem(item);

		if(ingredient != null){
			Ingredient.addFoodAndFlavorTooltip(ingredient, event.getToolTip());
		}
	}

	@SubscribeEvent
	public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event){
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
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event){
		PlayerEntity player = event.player;
		if(!player.world.isRemote && player.isPotionActive(FoodEffects.MINT_POWER)){
			int level = player.getActivePotionEffect(FoodEffects.MINT_POWER).getAmplifier();
			FrostWalkerEnchantment.freezeNearby(player, player.world, player.getPosition(), level);
		}
	}
}