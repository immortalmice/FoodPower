package com.github.immortalmice.foodpower.lists;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.fml.RegistryObject;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.tree.FPTree;
import com.github.immortalmice.foodpower.customclass.tree.TreeSaplingBush;
import com.github.immortalmice.foodpower.customclass.tree.TreeLeave;

public class Trees{

	public static final FPTree ORANGE = new FPTree("orange");
	public static final FPTree KIWI = new FPTree("kiwi");
	public static final FPTree PAPAYA = new FPTree("papaya");
	public static final FPTree MANGO = new FPTree("mango");
	public static final FPTree LEMON = new FPTree("lemon");

	@ObjectHolder("foodpower")
	public static class Blocks{
		public static final TreeLeave ORANGE_LEAVE = null;
		public static final TreeLeave KIWI_LEAVE = null;
		public static final TreeLeave PAPAYA_LEAVE = null;
		public static final TreeLeave MANGO_LEAVE = null;
		public static final TreeLeave LEMON_LEAVE = null;

		public static final TreeSaplingBush ORANGE_SAPLING = null;
		public static final TreeSaplingBush KIWI_SAPLING = null;
		public static final TreeSaplingBush PAPAYA_SAPLING = null;
		public static final TreeSaplingBush MANGO_SAPLING = null;
		public static final TreeSaplingBush LEMON_SAPLING = null;
	}

	private static class Lists{
		public static final List<FPTree> list = new ArrayList<FPTree>();
		public static final List<TreeSaplingBush> saplingBushList = new ArrayList<TreeSaplingBush>();
		public static final List<TreeLeave> leaveList = new ArrayList<TreeLeave>();
	}

	public static DeferredRegister<Block> getBlockRegister(){
		return TreeRegistry.BLOCK_REGISTER;
	}

	public static DeferredRegister<Item> getItemRegister(){
		return TreeRegistry.ITEM_REGISTER;
	}

	private static void setList(){
		if(Trees.Lists.list.isEmpty()){
			Field[] fields = Trees.class.getFields();
			for(Field field : fields){
				try{
					if(field.getType() == FPTree.class){
						Trees.Lists.list.add((FPTree) field.get(null));
					}
				}catch(Exception e){

				}
			}
		}
		if(Trees.Lists.saplingBushList.isEmpty()
			|| Trees.Lists.leaveList.isEmpty()){

			Trees.Lists.saplingBushList.clear();
			Trees.Lists.leaveList.clear();

			Field[] fields = Trees.Blocks.class.getFields();
			for(Field field : fields){
				try{
					if(field.get(null) != null){
						if(field.getType() == TreeLeave.class){
							Trees.Lists.leaveList.add((TreeLeave)field.get(null));
						}else if(field.getType() == TreeSaplingBush.class){
							Trees.Lists.saplingBushList.add((TreeSaplingBush)field.get(null));
						}
					}else if(field.getType() == TreeLeave.class
							|| field.getType() == TreeSaplingBush.class){

						throw new Exception();
					}
				}catch(Exception e){

				}
			}

		}
	}

	public static List<FPTree> getFPTreeList(){
		Trees.setList();
		return Trees.Lists.list;
	}

	public static List<TreeSaplingBush> getSaplingList(){
		Trees.setList();
		return Trees.Lists.saplingBushList;
	}

	public static List<TreeLeave> getLeaveList(){
		Trees.setList();
		return Trees.Lists.leaveList;
	}


	public static void setup(){
		Trees.ORANGE.setLeaveAndSapling(Trees.Blocks.ORANGE_LEAVE, Trees.Blocks.ORANGE_SAPLING);
		Trees.KIWI.setLeaveAndSapling(Trees.Blocks.KIWI_LEAVE, Trees.Blocks.KIWI_SAPLING);
		Trees.PAPAYA.setLeaveAndSapling(Trees.Blocks.PAPAYA_LEAVE, Trees.Blocks.PAPAYA_SAPLING);
		Trees.MANGO.setLeaveAndSapling(Trees.Blocks.MANGO_LEAVE, Trees.Blocks.MANGO_SAPLING);
		Trees.LEMON.setLeaveAndSapling(Trees.Blocks.LEMON_LEAVE, Trees.Blocks.LEMON_SAPLING);
	}
}

class TreeRegistry{
	public static final DeferredRegister<Block> BLOCK_REGISTER = new DeferredRegister<Block>(ForgeRegistries.BLOCKS, FoodPower.MODID);
	public static final DeferredRegister<Item> ITEM_REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	public static final RegistryObject<Block> OBJ_BLOCK_ORANGE_LEAVE = TreeRegistry.registerBlock("orange_leave", (str) -> new TreeLeave(str));
	public static final RegistryObject<Block> OBJ_BLOCK_KIWI_LEAVE = TreeRegistry.registerBlock("kiwi_leave", (str) -> new TreeLeave(str));
	public static final RegistryObject<Block> OBJ_BLOCK_PAPAYA_LEAVE = TreeRegistry.registerBlock("papaya_leave", (str) -> new TreeLeave(str));
	public static final RegistryObject<Block> OBJ_BLOCK_MANGO_LEAVE = TreeRegistry.registerBlock("mango_leave", (str) -> new TreeLeave(str));
	public static final RegistryObject<Block> OBJ_BLOCK_LEMON_LEAVE = TreeRegistry.registerBlock("lemon_leave", (str) -> new TreeLeave(str));

	public static final RegistryObject<Block> OBJ_BLOCK_ORANGE_SAPLING = TreeRegistry.registerBlock("orange_sapling", (str) -> new TreeSaplingBush(str, Trees.ORANGE));
	public static final RegistryObject<Block> OBJ_BLOCK_KIWI_SAPLING = TreeRegistry.registerBlock("kiwi_sapling", (str) -> new TreeSaplingBush(str, Trees.KIWI));
	public static final RegistryObject<Block> OBJ_BLOCK_PAPAYA_SAPLING = TreeRegistry.registerBlock("papaya_sapling", (str) -> new TreeSaplingBush(str, Trees.PAPAYA));
	public static final RegistryObject<Block> OBJ_BLOCK_MANGO_SAPLING = TreeRegistry.registerBlock("mango_sapling", (str) -> new TreeSaplingBush(str, Trees.MANGO));
	public static final RegistryObject<Block> OBJ_BLOCK_LEMON_SAPLING = TreeRegistry.registerBlock("lemon_sapling", (str) -> new TreeSaplingBush(str, Trees.LEMON));

	public static final RegistryObject<Item> OBJ_ITEM_ORANGE_LEAVE = TreeRegistry.registerItem("orange_leave", (str) -> new BlockItem(TreeRegistry.OBJ_BLOCK_ORANGE_LEAVE.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_KIWI_LEAVE = TreeRegistry.registerItem("kiwi_leave", (str) -> new BlockItem(TreeRegistry.OBJ_BLOCK_KIWI_LEAVE.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_PAPAYA_LEAVE = TreeRegistry.registerItem("papaya_leave", (str) -> new BlockItem(TreeRegistry.OBJ_BLOCK_PAPAYA_LEAVE.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_MANGO_LEAVE = TreeRegistry.registerItem("mango_leave", (str) -> new BlockItem(TreeRegistry.OBJ_BLOCK_MANGO_LEAVE.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_LEMON_LEAVE = TreeRegistry.registerItem("lemon_leave", (str) -> new BlockItem(TreeRegistry.OBJ_BLOCK_LEMON_LEAVE.get(), new Item.Properties().group(FPCreativeTabs.BLOCK_TAB)));

	public static final RegistryObject<Item> OBJ_ITEM_ORANGE_SAPLING = TreeRegistry.registerItem("orange_sapling", (str) -> new BlockItem(TreeRegistry.OBJ_BLOCK_ORANGE_SAPLING.get(), new Item.Properties().group(FPCreativeTabs.ITEM_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_KIWI_SAPLING = TreeRegistry.registerItem("kiwi_sapling", (str) -> new BlockItem(TreeRegistry.OBJ_BLOCK_KIWI_SAPLING.get(), new Item.Properties().group(FPCreativeTabs.ITEM_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_PAPAYA_SAPLING = TreeRegistry.registerItem("papaya_sapling", (str) -> new BlockItem(TreeRegistry.OBJ_BLOCK_PAPAYA_SAPLING.get(), new Item.Properties().group(FPCreativeTabs.ITEM_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_MANGO_SAPLING = TreeRegistry.registerItem("mango_sapling", (str) -> new BlockItem(TreeRegistry.OBJ_BLOCK_MANGO_SAPLING.get(), new Item.Properties().group(FPCreativeTabs.ITEM_TAB)));
	public static final RegistryObject<Item> OBJ_ITEM_LEMON_SAPLING = TreeRegistry.registerItem("lemon_sapling", (str) -> new BlockItem(TreeRegistry.OBJ_BLOCK_LEMON_SAPLING.get(), new Item.Properties().group(FPCreativeTabs.ITEM_TAB)));

	private static RegistryObject<Block> registerBlock(String name, Function<String, Block> fun){
		return TreeRegistry.BLOCK_REGISTER.register(name, () -> fun.apply(name));
	}
	private static RegistryObject<Item> registerItem(String name, Function<String, Item> fun){
		return TreeRegistry.ITEM_REGISTER.register(name, () -> fun.apply(name));
	}
}