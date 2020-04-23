package com.github.immortalmice.foodpower.lists;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.BlockBase;
import com.github.immortalmice.foodpower.customclass.specialclass.Market;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeTable;
import com.github.immortalmice.foodpower.customclass.util.ReflectList;

/* All the other blocks in mod need to be registed will list below */
public class OtherBlocks{
	public static final ReflectList<BlockBase, Blocks> list = new ReflectList<BlockBase, Blocks>(BlockBase.class, Blocks.class, null, true);

	public static class Blocks{
		public static final Market MARKET = null;
		public static final RecipeTable RECIPE_TABLE = null;	
	}

	public static DeferredRegister<Block> getBlockRegister(){
		return OtherBlocksRegistry.BLOCK_REGISTER;
	}
	public static DeferredRegister<Item> getItemRegister(){
		return OtherBlocksRegistry.ITEM_REGISTER;
	}
}

class OtherBlocksRegistry{
	public static final DeferredRegister<Block> BLOCK_REGISTER = new DeferredRegister<Block>(ForgeRegistries.BLOCKS, FoodPower.MODID);
	public static final DeferredRegister<Item> ITEM_REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	public static final RegistryObject<Block> OBJ_BLOCK_MARKET = OtherBlocksRegistry.registerBlock("market", () -> new Market());
	public static final RegistryObject<Block> OBJ_BLOCK_RECIPE_TABLE = OtherBlocksRegistry.registerBlock("recipe_table", () -> new RecipeTable());

	public static final RegistryObject<Item> OBJ_ITEM_MARKET = OtherBlocksRegistry.registerItem("market", () -> new BlockItem(OtherBlocksRegistry.OBJ_BLOCK_MARKET.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_RECIPE_TABLE = OtherBlocksRegistry.registerItem("recipe_table", () -> new BlockItem(OtherBlocksRegistry.OBJ_BLOCK_RECIPE_TABLE.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));

	private static RegistryObject<Block> registerBlock(String name, Supplier<Block> sup){
		return OtherBlocksRegistry.BLOCK_REGISTER.register(name, sup);
	}
	private static RegistryObject<Item> registerItem(String name, Supplier<Item> sup){
		return OtherBlocksRegistry.ITEM_REGISTER.register(name, sup);
	}
}