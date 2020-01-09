package com.github.immortalmice.foodpower.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;

@Mod.EventBusSubscriber
public class RegistryHandler{

	/** Regist mod items */
	@SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event){
    	/** Regular items */
    	event.getRegistry().registerAll(Ingredients.list.toArray(new Item[0]));

    	/** ItemBlocks */
    	for(int i = 0; i <= KitchenAppliances.list.size()-1; i++) {
    		Block block = KitchenAppliances.list.get(i);
    		ItemBlock itemBlock = new ItemBlock(block);
    		itemBlock.setRegistryName(block.getRegistryName());
    		event.getRegistry().register(itemBlock);
    	}
    }

	/** Regist mod blocks */
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event){
    	event.getRegistry().registerAll(KitchenAppliances.list.toArray(new Block[0]));
    }

    /** Regist mod item model */
    @SubscribeEvent
    public static void onModelReg(ModelRegistryEvent event) {
    	for(int i = 0; i <= Ingredients.list.size()-1; i ++){
    		Item item = Ingredients.list.get(i);
    		ModelResourceLocation marl = new ModelResourceLocation(item.getRegistryName(), "inventory");
        	ModelLoader.setCustomModelResourceLocation(item, 0, marl);
    	}
    }
}
