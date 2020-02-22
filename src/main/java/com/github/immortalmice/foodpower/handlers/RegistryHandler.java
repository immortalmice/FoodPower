package com.github.immortalmice.foodpower.handlers;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

import com.github.immortalmice.foodpower.lists.Crops;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.other.OtherItems;
import com.github.immortalmice.foodpower.lists.other.OtherBlocks;
import com.github.immortalmice.foodpower.lists.other.OtherItemBlocks;
import com.github.immortalmice.foodpower.lists.TileEntitys;
import com.github.immortalmice.foodpower.customclass.tileentity.TileEntityPack;

@Mod.EventBusSubscriber
public class RegistryHandler{

	/** Regist mod items */
	@SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event){
    	/** Regular items */
        event.getRegistry().registerAll(Ingredients.list.toArray(new Item[0]));
        event.getRegistry().registerAll(Ingredients.vanillaList.toArray(new Item[0]));
        event.getRegistry().registerAll(Ingredients.cookedFoodList.toArray(new Item[0]));
        event.getRegistry().registerAll(Ingredients.mealFoodList.toArray(new Item[0]));
        event.getRegistry().registerAll(Crops.seedList.toArray(new Item[0]));
        event.getRegistry().registerAll(OtherItems.list.toArray(new Item[0]));

    	/** ItemBlocks */
    	registItemBlockArrayList(KitchenAppliances.list, event);
        registItemBlockArrayList(Trees.leaveList, event);
        registItemBlockArrayList(Trees.saplingBushList, event);
        registItemBlockArrayList(OtherItemBlocks.list, event);
    }

	/** Regist mod blocks */
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event){
        /** Blocks */
    	event.getRegistry().registerAll(KitchenAppliances.list.toArray(new Block[0]));
    	event.getRegistry().registerAll(Crops.blockList.toArray(new Block[0]));
        event.getRegistry().registerAll(Trees.leaveList.toArray(new Block[0]));
        event.getRegistry().registerAll(Trees.saplingBushList.toArray(new Block[0]));
        event.getRegistry().registerAll(OtherBlocks.list.toArray(new Block[0]));
        event.getRegistry().registerAll(OtherItemBlocks.list.toArray(new Block[0]));
        /** TileEntitys */
        registTileEntitysArrayList(TileEntitys.list);
    }

    /** Regist mod item model */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
    	/** Item models */
    	registItemModelArrayList(Ingredients.list);
        registItemModelArrayList(Ingredients.cookedFoodList);
    	registItemModelArrayList(Crops.seedList);

        /** Vanilla Item Model */
        registItemVanillaModelArrayList(Ingredients.vanillaList);

    	/** Block in item form models */
    	registItemBlockModelArrayList(KitchenAppliances.list);
    	registItemBlockModelArrayList(Crops.blockList);
        registItemBlockModelArrayList(Trees.leaveList);
        registItemBlockModelArrayList(Trees.saplingBushList);

        /** Others */
        OtherItems.registModel();
        OtherItemBlocks.registModel();
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
    private static void registItemVanillaModelArrayList(List<? extends Item> arrayList){
        for(int i = 0; i <= arrayList.size()-1; i ++){
            Item item = arrayList.get(i);
            String path = item.getRegistryName().getPath();
            ModelResourceLocation marl = new ModelResourceLocation(new ResourceLocation("Minecraft", path), "inventory");
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
    private static void registTileEntitysArrayList(List<? extends TileEntityPack> arrayList){
        for(int i = 0; i <= arrayList.size()-1; i ++){
            arrayList.get(i).registThis();
        }
    }
}
