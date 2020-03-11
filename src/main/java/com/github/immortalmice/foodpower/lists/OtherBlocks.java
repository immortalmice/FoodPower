package com.github.immortalmice.foodpower.lists;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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

/* All the other blocks in mod need to be registed will list below */
public class OtherBlocks{

	public static class Blocks{
		public static final Market MARKET = null;
		public static final RecipeTable RECIPE_TABLE = null;	
	}

	/* Construct lists when first time getList() called. */
	private static class Lists{
		public static final List<BlockBase> list = new ArrayList<BlockBase>();
	}

	public static DeferredRegister<Block> getBlockRegister(){
		return OtherBlocksRegistry.BLOCK_REGISTER;
	}
	public static DeferredRegister<Item> getItemRegister(){
		return OtherBlocksRegistry.ITEM_REGISTER;
	}

	/* If called before ObjectHolder worked, list will be empty */
	public static List<BlockBase> getList(){
		if(OtherBlocks.Lists.list.isEmpty()){
			Field[] fields = OtherBlocks.Blocks.class.getFields();
			for(Field field : fields){
				try{
					if(field.getType().isAssignableFrom(BlockBase.class)){
						if(field.get(null) != null){
							OtherBlocks.Lists.list.add((BlockBase)field.get(null));
						}else{
							throw new Exception();
						}
					}
				}catch(Exception e){
					OtherBlocks.Lists.list.clear();
					break;
				}
			}
		}
		return OtherBlocks.Lists.list;
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