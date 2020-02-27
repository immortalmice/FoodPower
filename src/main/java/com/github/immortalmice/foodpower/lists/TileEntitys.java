package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntityType;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.customclass.tileentity.MarketTileEntity;
import com.github.immortalmice.foodpower.customclass.tileentity.RecipeTableTileEntity;
import com.github.immortalmice.foodpower.lists.other.OtherItemBlocks;

public class TileEntitys{
	public static final List<? extends TileEntityBase> list = new ArrayList<TileEntityBase>();

	public static final TileEntityType.Builder<MarketTileEntity> MARKET = TileEntityType.Builder.create(MarketTileEntity::new, OtherItemBlocks.MARKET_BLOCK);
	public static final TileEntityType.Builder<RecipeTableTileEntity> RECIPE_TABLE = TileEntityType.Builder.create(RecipeTableTileEntity::new, OtherItemBlocks.RECIPE_TABLE);
}