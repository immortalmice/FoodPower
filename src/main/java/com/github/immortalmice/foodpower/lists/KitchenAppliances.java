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
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;

/* All the ingredient need to be registed will list below. */
public class KitchenAppliances{
	/* Constructor: name, bounding block */
	public static final KitchenAppliance OVEN = new KitchenAppliance("oven", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance JUICER = new KitchenAppliance("juicer", new AxisAlignedBB(3.0D/16, 2.0D/16, 3.0D/16, 13.0D/16, 1.0D, 13.0D/16));
	public static final KitchenAppliance REVOLVING_CAKE_STAND = new KitchenAppliance("revolving_cake_stand", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance REFRIGERATOR = new KitchenAppliance("refrigerator", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance ELECTRIC_POT = new KitchenAppliance("electric_pot", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance STOCKPOT = new KitchenAppliance("stockpot", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance FRYING_PAN = new KitchenAppliance("frying_pan", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance ELECRIC_STOVE = new KitchenAppliance("electric_stove", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance SHAKER = new KitchenAppliance("shaker", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance CHOPPING_BOARD = new KitchenAppliance("chopping_board", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance UNIVERSAL_STATION = new KitchenAppliance("universal_stand", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));

	static{
		Field[] fields = KitchenAppliances.class.getFields();
		for(Field field : fields){
			try{
				if(field.getType() == KitchenAppliance.class){
					KitchenAppliancesList.list.add((KitchenAppliance)field.get(null));
				}
			}catch(Exception e){

			}
		}
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

	public static final RegistryObject<Block> OBJ_BLOCK_OVEN = KitchenAppliancesRegistry.registerBlock("oven", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_JUICER = KitchenAppliancesRegistry.registerBlock("juicer", (str) -> new KitchenAppliance(str, new AxisAlignedBB(3.0D/16, 2.0D/16, 3.0D/16, 13.0D/16, 1.0D, 13.0D/16)));
	public static final RegistryObject<Block> OBJ_BLOCK_REVOLVING_CAKE_STAND = KitchenAppliancesRegistry.registerBlock("revolving_cake_stand", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_REFRIGERATOR = KitchenAppliancesRegistry.registerBlock("refrigerator", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_ELECTRIC_POT = KitchenAppliancesRegistry.registerBlock("electric_pot", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_STOCKPOT = KitchenAppliancesRegistry.registerBlock("stockpot", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_FRYING_PAN = KitchenAppliancesRegistry.registerBlock("frying_pan", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_ELECRIC_STOVE = KitchenAppliancesRegistry.registerBlock("electric_stove", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_SHAKER = KitchenAppliancesRegistry.registerBlock("shaker", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_CHOPPING_BOARD = KitchenAppliancesRegistry.registerBlock("chopping_board", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));
	public static final RegistryObject<Block> OBJ_BLOCK_UNIVERSAL_STATION = KitchenAppliancesRegistry.registerBlock("universal_stand", (str) -> new KitchenAppliance(str, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)));

	public static final RegistryObject<Item> OBJ_ITEM_OVEN = KitchenAppliancesRegistry.registerItem("oven", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_OVEN.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_JUICER = KitchenAppliancesRegistry.registerItem("juicer", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_JUICER.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_REVOLVING_CAKE_STAND = KitchenAppliancesRegistry.registerItem("revolving_cake_stand", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_REVOLVING_CAKE_STAND.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_REFRIGERATOR = KitchenAppliancesRegistry.registerItem("refrigerator", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_REFRIGERATOR.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_ELECTRIC_POT = KitchenAppliancesRegistry.registerItem("electric_pot", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_ELECTRIC_POT.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_STOCKPOT = KitchenAppliancesRegistry.registerItem("stockpot", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_STOCKPOT.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_FRYING_PAN = KitchenAppliancesRegistry.registerItem("frying_pan", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_FRYING_PAN.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_ELECRIC_STOVE = KitchenAppliancesRegistry.registerItem("electric_stove", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_ELECRIC_STOVE.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_SHAKER = KitchenAppliancesRegistry.registerItem("shaker", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_SHAKER.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_CHOPPING_BOARD = KitchenAppliancesRegistry.registerItem("chopping_board", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_CHOPPING_BOARD.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_UNIVERSAL_STATION = KitchenAppliancesRegistry.registerItem("universal_stand", (str) -> new BlockItem(KitchenAppliancesRegistry.OBJ_BLOCK_UNIVERSAL_STATION.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));

	private static RegistryObject<Block> registerBlock(String name, Function<String, Block> fun){
		return KitchenAppliancesRegistry.BLOCK_REGISTER.register(name, () -> fun.apply(name));
	}
	private static RegistryObject<Item> registerItem(String name, Function<String, Item> fun){
		return KitchenAppliancesRegistry.ITEM_REGISTER.register(name, () -> fun.apply(name));
	}
}

class KitchenAppliancesList{
	public static final List<KitchenAppliance> list = new ArrayList<KitchenAppliance>();
}