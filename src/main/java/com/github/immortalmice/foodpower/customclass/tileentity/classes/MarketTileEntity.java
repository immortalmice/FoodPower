package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.customclass.container.market.MarketContainer;
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

	@Override
	public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player){
		return new MarketContainer(windowId, playerInventory, player, this.getWorld(), this.getPos());
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("block.foodpower.market");
	}
}