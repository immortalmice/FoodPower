package com.github.immortalmice.foodpower.tileentity;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;
import com.github.immortalmice.foodpower.lists.TileEntitys;
import com.github.immortalmice.foodpower.specialclass.KitchenAppliance;
import com.github.immortalmice.foodpower.util.ReflectList;

public class TileEntityPack<T extends TileEntityBase>{
	private TileEntityType<T> tileEntityType;

	private final Supplier<T> factory;
	private final Supplier<Block[]> blocks;

	public TileEntityPack(String nameIn, Supplier<T> factoryIn, Supplier<Block[]> blocksIn){
		this.factory = factoryIn;
		this.blocks = blocksIn;

		TileEntitys.list.add(this);
		TileEntitys.REGISTER.register(nameIn, () -> this.getTileEntityType());
	}

	public TileEntityPack(String nameIn, Supplier<T> factoryIn, Block... blocksIn){
		this(nameIn, factoryIn, () -> blocksIn);
	}

	public <F extends Block> TileEntityPack(String nameIn, Supplier<T> factoryIn, ReflectList<F, ?> blocksIn){
		this(nameIn, factoryIn, () -> blocksIn.toArray(new KitchenAppliance[KitchenAppliances.list.size()]));
	}

	public TileEntityType<T> getTileEntityType(){
		if(this.tileEntityType == null){
			this.tileEntityType = TileEntityType.Builder.create(this.factory, this.blocks.get()).build(null);
		}
		return this.tileEntityType;
	}
}