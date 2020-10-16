package com.github.immortalmice.foodpower.lists;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import com.github.immortalmice.foodpower.capability.interfaces.IFPFlavorExpCapability;
import com.github.immortalmice.foodpower.capability.interfaces.IFPPatternExpCapability;
import com.github.immortalmice.foodpower.util.ReflectList;

public class Capabilities{
	@SuppressWarnings("rawtypes")
	public static final ReflectList<Capability, Capabilities> list = new ReflectList<Capability, Capabilities>(Capability.class, Capabilities.class);

	@CapabilityInject(IFPPatternExpCapability.class)
	public static final Capability<IFPPatternExpCapability> PATTERN_EXP_CAPABILITY = null;
	
	@CapabilityInject(IFPFlavorExpCapability.class)
	public static final Capability<IFPFlavorExpCapability> FLAVOR_EXP_CAPABILITY = null;
}