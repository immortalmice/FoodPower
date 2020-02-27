package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import net.minecraft.nbt.NBTTagCompound;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.Crops;

public class MarketTileEntity extends TileEntityBase{
	private int index = 0;

	public MarketTileEntity(){
		super();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		this.index = tag.getInteger("index");
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag){
		tag.setInteger("index", this.index);
		return super.writeToNBT(tag);
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