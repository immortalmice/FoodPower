package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import net.minecraft.nbt.CompoundNBT;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.TileEntitys;

public class RecipeTableTileEntity extends TileEntityBase{
	private int index = 0;
	private String inputText = "Unknown Recipe";

	public RecipeTableTileEntity(){
		super(TileEntitys.RECIPE_TABLE.getTileEntityType());
	}

	@Override
	public void read(CompoundNBT tag){
		super.read(tag);
		this.index = tag.getInt("index");
		this.inputText = tag.getString("inputText");
	}
	@Override
	public CompoundNBT write(CompoundNBT tag){
		tag.putInt("index", this.index);
		tag.putString("inputText", this.inputText);
		return super.write(tag);
	}

	/* Increase and cycle index */
	public void increaseIndex(){
		this.index++;

		if(this.index > CookingPatterns.list.size() - 1){
			this.index -= CookingPatterns.list.size();
		}
		
		this.markDirty();
	}
	/* Decrease and cycle index */
	public void decreaseIndex(){
		this.index--;

		if(this.index < 0){
			this.index += CookingPatterns.list.size();
		}
		
		this.markDirty();
	}

	public void setInputText(String str){
		this.inputText = str;

		this.markDirty();
	}

	public int getIndex(){
		return this.index;
	}

	public String getInputText(){
		return this.inputText;
	}
}