package com.github.immortalmice.foodpower.proxy.common;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import com.github.immortalmice.foodpower.event.TerrainEventHandler;
import com.github.immortalmice.foodpower.lists.Crops;

public class CommonProxy{
	public void preInit(FMLPreInitializationEvent event){
		
	}

	public void init(FMLInitializationEvent event){
        new TerrainEventHandler();
        Crops.registSeeds();
	}

	public void postInit(FMLPostInitializationEvent event){
		
	}
}