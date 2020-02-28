package com.github.immortalmice.foodpower.customclass.tileentity;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistryEntry;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.lists.TileEntitys;

public class TileEntityPack<T extends TileEntityBase> extends ForgeRegistryEntry<TileEntityType<?>>{
	private final TileEntityType<T> tileEntityType;
	private final String fpName;

	public TileEntityPack(String nameIn, Supplier<T> factoryIn, Block... blocksIn){
		this.tileEntityType = TileEntityType.Builder.create(factoryIn, blocksIn).build(null);
		this.fpName = nameIn;

		TileEntitys.list.add(this);
		TileEntitys.REGISTER.register(this.getFPName(), () -> this.getTileEntityType());
	}

	public TileEntityType<T> getTileEntityType(){
		return this.tileEntityType;
	}

	public String getFPName(){
		return this.fpName;
	}
}