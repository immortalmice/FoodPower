package com.github.immortalmice.foodpower.registry;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import com.github.immortalmice.foodpower.lists.IngredientList;

@Mod.EventBusSubscriber
public class RegistryHandler{
	@SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event){
    	event.getRegistry().registerAll(IngredientList.list.toArray(new Item[0]));
    }
}