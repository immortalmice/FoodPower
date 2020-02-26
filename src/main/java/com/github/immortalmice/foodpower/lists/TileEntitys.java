package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntityType;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.customclass.tileentity.MarketTileEntity;
import com.github.immortalmice.foodpower.customclass.tileentity.RecipeTableTileEntity;

public class TileEntitys{
	public static final List<? extends TileEntityBase> list = new ArrayList<TileEntityBase>();

	public static final TileEntityType<MarketTileEntity> MARKET;
	public static final TileEntityType<RecipeTableTileEntity> RECIPE_TABLE;
}