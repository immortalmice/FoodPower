package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.tileentity.TileEntityPack;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.MarketTileEntity;

public class TileEntitys{
	public static final List<TileEntityPack> list = new ArrayList<TileEntityPack>();

	public static final TileEntityPack MARKET = new TileEntityPack(MarketTileEntity.class, "market_block");
}