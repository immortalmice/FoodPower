package com.github.immortalmice.foodpower;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import com.github.immortalmice.foodpower.bus.ModEventHandlers;
import com.github.immortalmice.foodpower.handlers.ConfigHandler;
import com.github.immortalmice.foodpower.handlers.RegistryHandler;

@Mod(FoodPower.MODID)
public class FoodPower{
    public static final String MODID = "foodpower";
    public static final String NAME = "Food Power";
    public static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
    	new ResourceLocation(FoodPower.MODID, "main")
    	, () -> FoodPower.PROTOCOL_VERSION
    	, FoodPower.PROTOCOL_VERSION::equals
    	, FoodPower.PROTOCOL_VERSION::equals
    );

    public FoodPower(){
    	ModEventHandlers.registAllEvent();
        RegistryHandler.registAll();
        ConfigHandler.registConfigs();
    }
}
