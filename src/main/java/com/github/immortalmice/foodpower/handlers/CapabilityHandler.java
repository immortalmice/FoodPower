package com.github.immortalmice.foodpower.handlers;

import java.util.concurrent.Callable;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import com.github.immortalmice.foodpower.customclass.capability.ExpCapability;

public class CapabilityHandler{
	public static void registAllCapabilities(){
		CapabilityHandler.register(ExpCapability.class, new ExpCapability.Storage(), () -> new ExpCapability());
	}
	private static <T> void register(Class<T> type, Capability.IStorage<T> storage, Callable<? extends T> factory){
		CapabilityManager.INSTANCE.register(type, storage, factory);
	}
}