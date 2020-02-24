package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.tileentity.TileEntityPack;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.MarketTileEntity;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.RecipeTableTileEntity;

public class TileEntitys{
	public static final List<TileEntityPack> list = new ArrayList<TileEntityPack>();

	public static final TileEntityPack MARKET = new TileEntityPack(MarketTileEntity.class, "market_block");
	public static final TileEntityPack RECIPE_TABLE = new TileEntityPack(RecipeTableTileEntity.class, "recipe_table");
}