package com.github.immortalmice.foodpower.lists;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import com.github.immortalmice.foodpower.customclass.capability.ExpCapability;

public class Capabilities{
	@CapabilityInject(ExpCapability.class)
	public static final Capability<ExpCapability> EXP_CAPABILITY = null;
}