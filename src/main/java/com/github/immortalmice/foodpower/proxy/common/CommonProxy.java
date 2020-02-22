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
import com.github.immortalmice.foodpower.customclass.message.classes.MarketMessage;
import com.github.immortalmice.foodpower.customclass.message.classes.RecipeTableMessage;

public class CommonProxy{
	public void preInit(FMLPreInitializationEvent event){
		FoodPower.network.registerMessage(MarketMessage.Handler.class, MarketMessage.class, 0, Side.SERVER);
		FoodPower.network.registerMessage(RecipeTableMessage.Handler.class, RecipeTableMessage.class, 1, Side.SERVER);
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