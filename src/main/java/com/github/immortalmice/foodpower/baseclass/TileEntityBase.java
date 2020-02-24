package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;

public class TileEntityBase extends TileEntity{
	public TileEntityBase(TileEntityType<?> tileEntityTypeIn){
		super(tileEntityTypeIn);
	}
	
	/** Sync NBT Tag between client & server */
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
}
