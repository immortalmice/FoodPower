package com.github.immortalmice.foodpower.lists;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import com.github.immortalmice.foodpower.customclass.capability.interfaces.IFPPatternExpCapability;

public class Capabilities{
	@CapabilityInject(IFPPatternExpCapability.class)
	public static final Capability<IFPPatternExpCapability> EXP_CAPABILITY = null;
}