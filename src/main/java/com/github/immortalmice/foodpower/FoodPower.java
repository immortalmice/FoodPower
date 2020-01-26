package com.github.immortalmice.foodpower;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.github.immortalmice.foodpower.proxy.common.CommonProxy;

@Mod(modid = FoodPower.MODID, name = FoodPower.NAME, version = FoodPower.VERSION)
public class FoodPower{
    public static final String MODID = "foodpower";
    public static final String NAME = "Food Power";
    public static final String VERSION = "0.1";

    @Instance(FoodPower.MODID)
    public static FoodPower instance;

    @SidedProxy(clientSide = "com.github.immortalmice.foodpower.proxy.client.ClientProxy",
        serverSide = "com.github.immortalmice.foodpower.proxy.common.CommonProxy")
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(FoodPower.MODID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

}
