package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import net.minecraft.nbt.NBTTagCompound;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class RecipeTableTileEntity extends TileEntityBase{
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

	/** Increase and cycle index */
	public void increaseIndex(){
		this.index++;

		if(this.index > CookingPatterns.list.size() - 1){
			this.index -= CookingPatterns.list.size();
		}
		
		this.markDirty();
	}
	/** Decrease and cycle index */
	public void decreaseIndex(){
		this.index--;

		if(this.index < 0){
			this.index += CookingPatterns.list.size();
		}
		
		this.markDirty();
	}
	public int getIndex(){
		return this.index;
	}
}