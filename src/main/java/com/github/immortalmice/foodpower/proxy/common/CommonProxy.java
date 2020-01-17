package com.github.immortalmice.foodpower.proxy.common;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import com.github.immortalmice.foodpower.event.TerrainEventHandler;

public class CommonProxy{
	public void preInit(FMLPreInitializationEvent event){
		new TerrainEventHandler();
	}

	public void init(FMLInitializationEvent event){
        
	}

	public void postInit(FMLPostInitializationEvent event){
		
	}
}