package com.github.immortalmice.foodpower.lists;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

/* All the ingredient need to be registed will list below. */

public class KitchenAppliances{
	
	@ObjectHolder("foodpower")
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

	/* Construct lists when first time getList() called. */
	private static class Lists{
		public static final List<KitchenAppliance> list = new ArrayList<KitchenAppliance>();
	}

	public static DeferredRegister<Block> getBlockRegister(){
		return KitchenAppliancesRegistry.BLOCK_REGISTER;
	}
	public static DeferredRegister<Item> getItemRegister(){
		return KitchenAppliancesRegistry.ITEM_REGISTER;
	}
	
	/* If called before ObjectHolder worked, list will be empty */
	public static List<KitchenAppliance> getList(){
		if(KitchenAppliances.Lists.list.isEmpty()){
			Field[] fields = KitchenAppliances.Blocks.class.getFields();
			for(Field field : fields){
				try{
					if(field.getType() == KitchenAppliance.class){
						if(field.get(null) != null){
							KitchenAppliances.Lists.list.add((KitchenAppliance)field.get(null));
						}else{
							throw new Exception();
						}
					}
				}catch(Exception e){
					KitchenAppliances.Lists.list.clear();
					break;
				}
			}
		}
		return KitchenAppliances.Lists.list;
	}
}

class KitchenAppliancesRegistry{
	public static final DeferredRegister<Block> BLOCK_REGISTER = new DeferredRegister<Block>(ForgeRegistries.BLOCKS, FoodPower.MODID);
	public static final DeferredRegister<Item> ITEM_REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	/* Constructor: name, bounding block */
	public static final RegistryObject<Block> OBJ_BLOCK_OVEN = KitchenAppliancesRegistry.registerBlock("oven", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_JUICER = KitchenAppliancesRegistry.registerBlock("juicer", (str) -> new KitchenAppliance(str, new AxisAlignedBB(3.0D/16, 2.0D/16, 3.0D/16, 13.0D/16, 1.0D, 13.0D/16)));
	public static final RegistryObject<Block> OBJ_BLOCK_REVOLVING_CAKE_STAND = KitchenAppliancesRegistry.registerBlock("revolving_cake_stand", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_REFRIGERATOR = KitchenAppliancesRegistry.registerBlock("refrigerator", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_ELECTRIC_POT = KitchenAppliancesRegistry.registerBlock("electric_pot", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_STOCKPOT = KitchenAppliancesRegistry.registerBlock("stockpot", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_FRYING_PAN = KitchenAppliancesRegistry.registerBlock("frying_pan", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_ELECTRIC_STOVE = KitchenAppliancesRegistry.registerBlock("electric_stove", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_SHAKER = KitchenAppliancesRegistry.registerBlock("shaker", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_CHOPPING_BOARD = KitchenAppliancesRegistry.registerBlock("chopping_board", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_UNIVERSAL_STATION = KitchenAppliancesRegistry.registerBlock("universal_station", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));

	public static final RegistryObject<Item> OBJ_ITEM_OVEN = KitchenAppliancesRegistry.registerItem("oven", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_OVEN.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_JUICER = KitchenAppliancesRegistry.registerItem("juicer", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_JUICER.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_REVOLVING_CAKE_STAND = KitchenAppliancesRegistry.registerItem("revolving_cake_stand", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_REVOLVING_CAKE_STAND.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_REFRIGERATOR = KitchenAppliancesRegistry.registerItem("refrigerator", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_REFRIGERATOR.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_ELECTRIC_POT = KitchenAppliancesRegistry.registerItem("electric_pot", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_ELECTRIC_POT.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_STOCKPOT = KitchenAppliancesRegistry.registerItem("stockpot", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_STOCKPOT.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_FRYING_PAN = KitchenAppliancesRegistry.registerItem("frying_pan", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_FRYING_PAN.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_ELECTRIC_STOVE = KitchenAppliancesRegistry.registerItem("electric_stove", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_ELECTRIC_STOVE.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_SHAKER = KitchenAppliancesRegistry.registerItem("shaker", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_SHAKER.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_CHOPPING_BOARD = KitchenAppliancesRegistry.registerItem("chopping_board", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_CHOPPING_BOARD.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_UNIVERSAL_STATION = KitchenAppliancesRegistry.registerItem("universal_station", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_UNIVERSAL_STATION.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));

	private static RegistryObject<Block> registerBlock(String name, Function<String, Block> fun){
		return KitchenAppliancesRegistry.BLOCK_REGISTER.register(name, () -> fun.apply(name));
	}
	private static RegistryObject<Item> registerItem(String name, Function<String, Item> fun){
		return KitchenAppliancesRegistry.ITEM_REGISTER.register(name, () -> fun.apply(name));
	}
}