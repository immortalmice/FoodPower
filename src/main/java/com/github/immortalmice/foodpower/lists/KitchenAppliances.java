package com.github.immortalmice.foodpower.lists;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.fml.RegistryObject;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.util.ReflectList;

/* All the KitchenAppliances need to be registed will list below. */
public class KitchenAppliances{
	public static final ReflectList<KitchenAppliance, Blocks> list = new ReflectList<KitchenAppliance, Blocks>(KitchenAppliance.class, Blocks.class, null);
	
	@ObjectHolder(FoodPower.MODID)
	public static class Blocks{
		public static final KitchenAppliance OVEN = null;
		public static final KitchenAppliance JUICER = null;
		public static final KitchenAppliance REVOLVING_CAKE_STAND = null;
		public static final KitchenAppliance REFRIGERATOR = null;
		public static final KitchenAppliance ELECTRIC_POT = null;
		public static final KitchenAppliance STOCKPOT = null;
		public static final KitchenAppliance FRYING_PAN = null;
		public static final KitchenAppliance ELECTRIC_STOVE = null;
		public static final KitchenAppliance SHAKER = null;
		public static final KitchenAppliance CHOPPING_BOARD = null;
		public static final KitchenAppliance UNIVERSAL_STATION = null;
	}

	public static DeferredRegister<Block> getBlockRegister(){
		return KitchenAppliancesRegistry.BLOCK_REGISTER;
	}
	public static DeferredRegister<Item> getItemRegister(){
		return KitchenAppliancesRegistry.ITEM_REGISTER;
	}
}

class KitchenAppliancesRegistry{
	public static final DeferredRegister<Block> BLOCK_REGISTER = new DeferredRegister<Block>(ForgeRegistries.BLOCKS, FoodPower.MODID);
	public static final DeferredRegister<Item> ITEM_REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	/* Constructor: name, bounding block */
	public static final RegistryObject<Block> OBJ_BLOCK_OVEN = KitchenAppliancesRegistry.registerBlock("oven", () -> new KitchenAppliance(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_JUICER = KitchenAppliancesRegistry.registerBlock("juicer", () -> new KitchenAppliance(new AxisAlignedBB(3.0D, 2.0D, 3.0D, 13.0D, 16.0D, 13.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_REVOLVING_CAKE_STAND = KitchenAppliancesRegistry.registerBlock("revolving_cake_stand", () -> new KitchenAppliance(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_REFRIGERATOR = KitchenAppliancesRegistry.registerBlock("refrigerator", () -> new KitchenAppliance(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_ELECTRIC_POT = KitchenAppliancesRegistry.registerBlock("electric_pot", () -> new KitchenAppliance(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_STOCKPOT = KitchenAppliancesRegistry.registerBlock("stockpot", () -> new KitchenAppliance(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_FRYING_PAN = KitchenAppliancesRegistry.registerBlock("frying_pan", () -> new KitchenAppliance(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_ELECTRIC_STOVE = KitchenAppliancesRegistry.registerBlock("electric_stove", () -> new KitchenAppliance(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_SHAKER = KitchenAppliancesRegistry.registerBlock("shaker", () -> new KitchenAppliance(new AxisAlignedBB(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_CHOPPING_BOARD = KitchenAppliancesRegistry.registerBlock("chopping_board", () -> new KitchenAppliance(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_UNIVERSAL_STATION = KitchenAppliancesRegistry.registerBlock("universal_station", () -> new KitchenAppliance(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)));

	public static final RegistryObject<Item> OBJ_ITEM_OVEN = KitchenAppliancesRegistry.registerItem("oven", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_OVEN.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_JUICER = KitchenAppliancesRegistry.registerItem("juicer", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_JUICER.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_REVOLVING_CAKE_STAND = KitchenAppliancesRegistry.registerItem("revolving_cake_stand", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_REVOLVING_CAKE_STAND.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_REFRIGERATOR = KitchenAppliancesRegistry.registerItem("refrigerator", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_REFRIGERATOR.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_ELECTRIC_POT = KitchenAppliancesRegistry.registerItem("electric_pot", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_ELECTRIC_POT.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_STOCKPOT = KitchenAppliancesRegistry.registerItem("stockpot", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_STOCKPOT.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_FRYING_PAN = KitchenAppliancesRegistry.registerItem("frying_pan", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_FRYING_PAN.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_ELECTRIC_STOVE = KitchenAppliancesRegistry.registerItem("electric_stove", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_ELECTRIC_STOVE.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_SHAKER = KitchenAppliancesRegistry.registerItem("shaker", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_SHAKER.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_CHOPPING_BOARD = KitchenAppliancesRegistry.registerItem("chopping_board", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_CHOPPING_BOARD.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_UNIVERSAL_STATION = KitchenAppliancesRegistry.registerItem("universal_station", () -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_UNIVERSAL_STATION.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));

	private static RegistryObject<Block> registerBlock(String name, Supplier<Block> sup){
		return KitchenAppliancesRegistry.BLOCK_REGISTER.register(name, sup);
	}
	private static RegistryObject<Item> registerItem(String name, Supplier<Item> sup){
		return KitchenAppliancesRegistry.ITEM_REGISTER.register(name, sup);
	}
}