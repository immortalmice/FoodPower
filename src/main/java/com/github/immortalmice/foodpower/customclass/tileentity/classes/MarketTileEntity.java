package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import net.minecraft.nbt.CompoundNBT;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.Crops;
import com.github.immortalmice.foodpower.lists.TileEntitys;

public class MarketTileEntity extends TileEntityBase{
	private int index = 0;

	public MarketTileEntity(){
		super(TileEntitys.MARKET.getTileEntityType());
	}

	@Override
	public void read(CompoundNBT tag){
		super.read(tag);
		this.index = tag.getInt("index");
	}
	@Override
	public CompoundNBT write(CompoundNBT tag){
		tag.putInt("index", this.index);
		return super.write(tag);
	}
	/* Increase and cycle index */
	public void increaseIndex(){
		this.index++;

		int treeSize = Trees.saplingBushList.size();
		int cropSize = Crops.seedList.size();
		if(this.index > treeSize + cropSize - 1){
			this.index -= treeSize + cropSize;
		}

		this.markDirty();
	}
	/* Decrease and cycle index */
	public void decreaseIndex(){
		this.index--;

		int treeSize = Trees.saplingBushList.size();
		int cropSize = Crops.seedList.size();
		if(this.index < 0){
			this.index += treeSize + cropSize;
		}

		this.markDirty();
	}
	public int getIndex(){
		return this.index;
	}
}