package com.github.immortalmice.foodpower.lists;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import com.github.immortalmice.foodpower.customclass.capability.interfaces.IFPPatternExpCapability;
import com.github.immortalmice.foodpower.customclass.util.ReflectList;
import com.github.immortalmice.foodpower.customclass.capability.interfaces.IFPFlavorExpCapability;

public class Capabilities{
	@SuppressWarnings("rawtypes")
	public static final ReflectList<Capability, Capabilities> list = new ReflectList<Capability, Capabilities>(Capability.class, Capabilities.class);

	@CapabilityInject(IFPPatternExpCapability.class)
	public static final Capability<IFPPatternExpCapability> PATTERN_EXP_CAPABILITY = null;
	
	@CapabilityInject(IFPFlavorExpCapability.class)
	public static final Capability<IFPFlavorExpCapability> FLAVOR_EXP_CAPABILITY = null;
}