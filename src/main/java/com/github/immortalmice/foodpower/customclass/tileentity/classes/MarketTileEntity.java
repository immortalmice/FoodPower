package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import net.minecraft.nbt.NBTTagCompound;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.Crops;

public class MarketTileEntity extends TileEntityBase{
	private int index = 0;

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
	@Override
	public NBTTagCompound getUpdateTag(){
		NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
	}
	@Override
	public void handleUpdateTag(NBTTagCompound tag){
		this.readFromNBT(tag);
	}

	public void increaseIndex(){
		this.index++;

		int treeSize = Trees.saplingBushList.size();
		int cropSize = Crops.seedList.size();
		if(this.index > treeSize + cropSize - 1){
			this.index -= treeSize + cropSize;
		}
	}

	public void decreaseIndex(){
		this.index--;

		int treeSize = Trees.saplingBushList.size();
		int cropSize = Crops.seedList.size();
		if(this.index < 0){
			this.index += treeSize + cropSize;
		}
	}

	public int getIndex(){
		return this.index;
	}
}