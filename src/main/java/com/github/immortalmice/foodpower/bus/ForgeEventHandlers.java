package com.github.immortalmice.foodpower.bus;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.capability.ExpCapability;

public class ForgeEventHandlers{
	private static final IEventBus BUS = MinecraftForge.EVENT_BUS;

	public static void registAllEvent(){
		ForgeEventHandlers.BUS.register(ForgeEventHandlers.class);
		ForgeEventHandlers.BUS.register(new ForgeEventHandlers());
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
	public static void addCapabilities(AttachCapabilitiesEvent<Entity> event){
		if(event.getObject() instanceof PlayerEntity){
			event.addCapability(new ResourceLocation(FoodPower.MODID, "exp_capability"), new ExpCapability.Provider());
		}
	}
}