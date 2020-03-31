package com.github.immortalmice.foodpower.bus;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.capability.classes.FPFlavorExpCapability;
import com.github.immortalmice.foodpower.customclass.capability.classes.FPPatternExpCapability;
import com.github.immortalmice.foodpower.customclass.capability.interfaces.IFPFlavorExpCapability;
import com.github.immortalmice.foodpower.customclass.capability.interfaces.IFPPatternExpCapability;
import com.github.immortalmice.foodpower.handlers.CommandHandler;
import com.github.immortalmice.foodpower.lists.Capabilities;

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
	public static void addCapabilities(AttachCapabilitiesEvent<Entity> event){
		if(event.getObject() instanceof PlayerEntity){
			event.addCapability(new ResourceLocation(FoodPower.MODID, "pattern_exp_capability"), new FPPatternExpCapability.Provider());
			event.addCapability(new ResourceLocation(FoodPower.MODID, "flavor_exp_capability"), new FPFlavorExpCapability.Provider());
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event){
		/* Copy capability data to new player */
		event.getOriginal().getCapability(Capabilities.PATTERN_EXP_CAPABILITY, null).ifPresent((old_cap) -> {
			event.getPlayer().getCapability(Capabilities.PATTERN_EXP_CAPABILITY, null).ifPresent((new_cap) -> {
				IStorage<IFPPatternExpCapability> storage = Capabilities.PATTERN_EXP_CAPABILITY.getStorage();

				CompoundNBT nbt = (CompoundNBT) storage.writeNBT(Capabilities.PATTERN_EXP_CAPABILITY, old_cap, null);
				storage.readNBT(Capabilities.PATTERN_EXP_CAPABILITY, new_cap, null, nbt);
			});
		});

		event.getOriginal().getCapability(Capabilities.FLAVOR_EXP_CAPABILITY, null).ifPresent((old_cap) -> {
			event.getPlayer().getCapability(Capabilities.FLAVOR_EXP_CAPABILITY, null).ifPresent((new_cap) -> {
				IStorage<IFPFlavorExpCapability> storage = Capabilities.FLAVOR_EXP_CAPABILITY.getStorage();

				CompoundNBT nbt = (CompoundNBT) storage.writeNBT(Capabilities.FLAVOR_EXP_CAPABILITY, old_cap, null);
				storage.readNBT(Capabilities.FLAVOR_EXP_CAPABILITY, new_cap, null, nbt);
			});
		});
	}
}