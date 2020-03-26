package com.github.immortalmice.foodpower.lists;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import com.github.immortalmice.foodpower.customclass.capability.IExpCapability;

public class Capabilities{
	@CapabilityInject(IExpCapability.class)
	public static final Capability<IExpCapability> EXP_CAPABILITY = null;
}