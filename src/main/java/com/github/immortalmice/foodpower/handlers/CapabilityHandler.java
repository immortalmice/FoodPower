package com.github.immortalmice.foodpower.handlers;

import java.util.concurrent.Callable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

import com.github.immortalmice.foodpower.customclass.capability.classes.FPFlavorExpCapability;
import com.github.immortalmice.foodpower.customclass.capability.classes.FPPatternExpCapability;
import com.github.immortalmice.foodpower.customclass.capability.interfaces.IFPFlavorExpCapability;
import com.github.immortalmice.foodpower.customclass.capability.interfaces.IFPPatternExpCapability;

public class CapabilityHandler{
	public static void registAllCapabilities(){
		CapabilityHandler.register(IFPPatternExpCapability.class, new FPPatternExpCapability.Storage(), () -> new FPPatternExpCapability());
		CapabilityHandler.register(IFPFlavorExpCapability.class, new FPFlavorExpCapability.Storage(), () -> new FPFlavorExpCapability());
	}
	private static <T> void register(Class<T> type, Capability.IStorage<T> storage, Callable<? extends T> factory){
		CapabilityManager.INSTANCE.register(type, storage, factory);
	}
	public static <T> void copyCapabilityData(PlayerEntity old_player, PlayerEntity new_Player, Capability<T> cap){
		old_player.getCapability(cap, null).ifPresent((old_cap) -> {
			new_Player.getCapability(cap, null).ifPresent((new_cap) -> {
				IStorage<T> storage = cap.getStorage();

				CompoundNBT nbt = (CompoundNBT) storage.writeNBT(cap, old_cap, null);
				storage.readNBT(cap, new_cap, null, nbt);
			});
		});
	}
}