package com.github.immortalmice.foodpower.registry;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import com.github.immortalmice.foodpower.lists.IngredientList;

@Mod.EventBusSubscriber
public class RegistryHandler{
	@SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event){
    	event.getRegistry().registerAll(IngredientList.list.toArray(new Item[0]));
    }
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Block> event){

    }
    @SubscribeEvent
    public static void onModelReg(ModelRegistryEvent event) {
    	for(int i = 0; i <= IngredientList.list.size(); i ++){
    		Item item = IngredientList.list.get(i);
    		ModelResourceLocation marl = new ModelResourceLocation(item.getRegistryName(), "inventory");
        	ModelLoader.setCustomModelResourceLocation(item, 0, marl);
    	}
    }
}
