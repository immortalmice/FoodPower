package com.github.immortalmice.foodpower.lists;

import java.util.function.Function;

import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.crop.CropBlock;
import com.github.immortalmice.foodpower.crop.CropSeed;
import com.github.immortalmice.foodpower.util.ReflectList;

public class Crops{
	public static final ReflectList<CropBlock, Blocks> blockList = new ReflectList<CropBlock, Blocks>(CropBlock.class, Blocks.class);
	public static final ReflectList<CropSeed, Items> itemList = new ReflectList<CropSeed, Items>(CropSeed.class, Items.class);

	@ObjectHolder(FoodPower.MODID)
	public static class Blocks{
		public static final CropBlock MINT_CROP = null;
		public static final CropBlock TOMATO_CROP = null;
		public static final CropBlock RICE_CROP = null;
		public static final CropBlock CHILI_CROP = null;
		public static final CropBlock SPINACH_CROP = null;
		public static final CropBlock CABBAGE_CROP = null;
		public static final CropBlock CORN_CROP = null;
	}

	@ObjectHolder(FoodPower.MODID)
	public static class Items{
		public static final CropSeed MINT_SEED = null;
		public static final CropSeed TOMATO_SEED = null;
		public static final CropSeed RICE_SEED = null;
		public static final CropSeed CHILI_SEED = null;
		public static final CropSeed SPINACH_SEED = null;
		public static final CropSeed CABBAGE_SEED = null;
		public static final CropSeed CORN_SEED = null;
	}

	public static DeferredRegister<Block> getBlockRegister(){
		return CropRegistry.BLOCK_REGISTER;
	}

	public static DeferredRegister<Item> getItemRegister(){
		return CropRegistry.ITEM_REGISTER;
	}
}

class CropRegistry{
	public static final DeferredRegister<Block> BLOCK_REGISTER = new DeferredRegister<Block>(ForgeRegistries.BLOCKS, FoodPower.MODID);
	public static final DeferredRegister<Item> ITEM_REGISTER = new DeferredRegister<Item>(ForgeRegistries.ITEMS, FoodPower.MODID);

	public static final RegistryObject<Block> OBJ_MINT_CROP = CropRegistry.registerBlock("mint_crop", (str) -> new CropBlock(str));
	public static final RegistryObject<Block> OBJ_TOMATO_CROP = CropRegistry.registerBlock("tomato_crop", (str) -> new CropBlock(str));
	public static final RegistryObject<Block> OBJ_RICE_CROP = CropRegistry.registerBlock("rice_crop", (str) -> new CropBlock(str));
	public static final RegistryObject<Block> OBJ_CHILI_CROP = CropRegistry.registerBlock("chili_crop", (str) -> new CropBlock(str));
	public static final RegistryObject<Block> OBJ_SPINACH_CROP = CropRegistry.registerBlock("spinach_crop", (str) -> new CropBlock(str));
	public static final RegistryObject<Block> OBJ_CABBAGE_CROP = CropRegistry.registerBlock("cabbage_crop", (str) -> new CropBlock(str));
	public static final RegistryObject<Block> OBJ_CORN_CROP = CropRegistry.registerBlock("corn_crop", (str) -> new CropBlock(str));

	public static final RegistryObject<Item> OBJ_MINT_SEED = CropRegistry.registerItem("mint_seed", (str) -> new CropSeed(str, (CropBlock) CropRegistry.OBJ_MINT_CROP.get()));
	public static final RegistryObject<Item> OBJ_TOMATO_SEED = CropRegistry.registerItem("tomato_seed", (str) -> new CropSeed(str, (CropBlock) CropRegistry.OBJ_TOMATO_CROP.get()));
	public static final RegistryObject<Item> OBJ_RICE_SEED = CropRegistry.registerItem("rice_seed", (str) -> new CropSeed(str, (CropBlock) CropRegistry.OBJ_RICE_CROP.get()));
	public static final RegistryObject<Item> OBJ_CHILI_SEED = CropRegistry.registerItem("chili_seed", (str) -> new CropSeed(str, (CropBlock) CropRegistry.OBJ_CHILI_CROP.get()));
	public static final RegistryObject<Item> OBJ_SPINACH_SEED = CropRegistry.registerItem("spinach_seed", (str) -> new CropSeed(str, (CropBlock) CropRegistry.OBJ_SPINACH_CROP.get()));
	public static final RegistryObject<Item> OBJ_CABBAGE_SEED = CropRegistry.registerItem("cabbage_seed", (str) -> new CropSeed(str, (CropBlock) CropRegistry.OBJ_CABBAGE_CROP.get()));
	public static final RegistryObject<Item> OBJ_CORN_SEED = CropRegistry.registerItem("corn_seed", (str) -> new CropSeed(str, (CropBlock) CropRegistry.OBJ_CORN_CROP.get()));

	private static RegistryObject<Block> registerBlock(String name, Function<String, Block> fun){
		return CropRegistry.BLOCK_REGISTER.register(name, () -> fun.apply(name));
	}
	private static RegistryObject<Item> registerItem(String name, Function<String, Item> fun){
		return CropRegistry.ITEM_REGISTER.register(name, () -> fun.apply(name));
	}
}