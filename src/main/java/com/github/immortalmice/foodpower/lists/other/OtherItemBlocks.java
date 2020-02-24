package com.github.immortalmice.foodpower.lists.other;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.immortalmice.foodpower.customclass.specialclass.Market;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeTable;

/** All the other itemblocks in mod need to be registed will list below */
public class OtherItemBlocks{
	public static final List<Block> list = new ArrayList<Block>();

	public static final Market MARKET_BLOCK = new Market();
	public static final RecipeTable RECIPE_TABLE = new RecipeTable();

	@SideOnly(Side.CLIENT)
	public static void registModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(MARKET_BLOCK), 0
			, new ModelResourceLocation(Item.getItemFromBlock(MARKET_BLOCK).getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RECIPE_TABLE), 0
			, new ModelResourceLocation(Item.getItemFromBlock(RECIPE_TABLE).getRegistryName(), "inventory"));
	}
}