package com.github.immortalmice.foodpower.proxy.common;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.event.TerrainEventHandler;
import com.github.immortalmice.foodpower.event.NormalEventHandler;
import com.github.immortalmice.foodpower.handlers.GuiHandler;
import com.github.immortalmice.foodpower.lists.Crops;
import com.github.immortalmice.foodpower.customclass.message.TileEntityNBTMessage;

public class CommonProxy{
	public void preInit(FMLPreInitializationEvent event){
		FoodPower.network.registerMessage(TileEntityNBTMessage.Handler.class, TileEntityNBTMessage.class, 0, Side.SERVER);
	}

	public void init(FMLInitializationEvent event){
        new NormalEventHandler();
        new TerrainEventHandler();
        new GuiHandler();

        Crops.registSeeds();
	}

	public void postInit(FMLPostInitializationEvent event){
		
	}
}