package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;

public class TileEntityBase extends TileEntity implements INamedContainerProvider{
	public TileEntityBase(TileEntityType<?> tileEntityTypeIn){
		super(tileEntityTypeIn);
	}
	
	/* Sync NBT Tag between client & server */
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
	    return new SUpdateTileEntityPacket(this.getPos(), 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager manager, SUpdateTileEntityPacket packet) {
	    this.read(packet.getNbtCompound());
	}

	@Override
	public CompoundNBT getUpdateTag(){
		CompoundNBT nbt = new CompoundNBT();
		return this.write(nbt);
	}

	/* No need to override IForgeTileEntity#handleUpdateTag, cause it works fine */

	@Override
	public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
		return null;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}
}
