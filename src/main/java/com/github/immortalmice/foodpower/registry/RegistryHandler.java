package com.github.immortalmice.foodpower.registry;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import com.github.immortalmice.foodpower.lists.Crops;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;

@Mod.EventBusSubscriber
public class RegistryHandler{

	/** Regist mod items */
	@SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event){
    	/** Regular items */
    	event.getRegistry().registerAll(Ingredients.list.toArray(new Item[0]));
    	event.getRegistry().registerAll(Crops.seedList.toArray(new Item[0]));

    	/** ItemBlocks */
    	registItemBlockArrayList(KitchenAppliances.list, event);
    }

	/** Regist mod blocks */
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event){
    	event.getRegistry().registerAll(KitchenAppliances.list.toArray(new Block[0]));
    	event.getRegistry().registerAll(Crops.blockList.toArray(new Block[0]));
    }

    /** Regist mod item model */
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
    	/** Item models */
    	registItemModelArrayList(Ingredients.list);
    	registItemModelArrayList(Crops.seedList);

    	/** Block in item form models */
    	registItemBlockModelArrayList(KitchenAppliances.list);
    	registItemBlockModelArrayList(Crops.blockList);
    }

    /** Helper To Regist All ArrayList */
    private static void registItemBlockArrayList(List<? extends Block> arrayList, RegistryEvent.Register<Item> event){
    	for(int i = 0; i <= arrayList.size()-1; i++) {
    		Block block = arrayList.get(i);
    		ItemBlock itemBlock = new ItemBlock(block);
    		itemBlock.setRegistryName(block.getRegistryName());
    		event.getRegistry().register(itemBlock);
    	}
    }
    private static void registItemModelArrayList(List<? extends Item> arrayList){
		for(int i = 0; i <= arrayList.size()-1; i ++){
    		Item item = arrayList.get(i);
    		ModelResourceLocation marl = new ModelResourceLocation(item.getRegistryName(), "inventory");
        	ModelLoader.setCustomModelResourceLocation(item, 0, marl);
    	}
    }
    private static void registItemBlockModelArrayList(List<? extends Block> arrayList){
    	for(int i = 0; i <= arrayList.size()-1; i ++){
    		Block block = arrayList.get(i);
    		Item item = Item.getItemFromBlock(block);
    		ModelResourceLocation marl = new ModelResourceLocation(item.getRegistryName(), "inventory");
        	ModelLoader.setCustomModelResourceLocation(item, 0, marl);
    	}
    }
}
