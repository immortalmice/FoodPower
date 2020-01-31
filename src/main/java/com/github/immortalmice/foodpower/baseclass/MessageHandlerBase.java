package com.github.immortalmice.foodpower.baseclass;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;

public class MessageHandlerBase{
	public static World getWorld(MessageContext ctx){
		switch(ctx.side){
			case SERVER:
				return MessageHandlerBase.getServerWorld(ctx);
			case CLIENT:
				return MessageHandlerBase.getClientWorld();
			default:
				return null;
		}
	}

	@SideOnly(Side.CLIENT)
	private static World getClientWorld(){
		return Minecraft.getMinecraft().world;
	}
	private static World getServerWorld(MessageContext ctx){
		return ctx.getServerHandler().player.getServerWorld();
	}
}