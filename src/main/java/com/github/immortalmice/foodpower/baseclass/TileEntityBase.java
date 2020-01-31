package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.NetworkManager;

public class TileEntityBase extends TileEntity{
	public TileEntityBase(){
		super();
	}
	
	/** Sync NBT Tag between client & server */
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
	    return new SPacketUpdateTileEntity(this.getPos(), 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
	    this.readFromNBT(packet.getNbtCompound());
	}
}
