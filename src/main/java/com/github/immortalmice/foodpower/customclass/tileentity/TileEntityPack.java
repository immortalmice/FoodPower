package com.github.immortalmice.foodpower.customclass.tileentity;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.lists.TileEntitys;

public class TileEntityPack<T extends TileEntityBase>{
	private final TileEntityType<T> tileEntityType;

	public TileEntityPack(String nameIn, Supplier<T> factoryIn, Block... blocksIn){
		this.tileEntityType = TileEntityType.Builder.create(factoryIn, blocksIn).build(null);

		TileEntitys.list.add(this);
		TileEntitys.REGISTER.register(nameIn, () -> this.getTileEntityType());
	}

	public TileEntityType<T> getTileEntityType(){
		return this.tileEntityType;
	}
}