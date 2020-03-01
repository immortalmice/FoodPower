package com.github.immortalmice.foodpower.baseclass;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public interface IMessageBase<T extends IMessageBase<?>>{
	public void encode(T msg, PacketBuffer buf);
	public T decode(PacketBuffer buf);
	public void handle(T msg, Supplier<NetworkEvent.Context> ctx);
	public void registMessage(int i);
}