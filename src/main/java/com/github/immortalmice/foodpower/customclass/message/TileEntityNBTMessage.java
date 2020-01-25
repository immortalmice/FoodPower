package com.github.immortalmice.foodpower.customclass.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.math.BlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;

/** Used to trasfer NBT data to update */
public class TileEntityNBTMessage implements IMessage{
	private BlockPos pos;
	private NBTTagCompound nbt;

	public TileEntityNBTMessage(){

	}
	public TileEntityNBTMessage(BlockPos posIn, NBTTagCompound nbtIn){
		this.pos = posIn;
		this.nbt = nbtIn;
	}

	/** X, Y, Z, NBT */
	@Override
	public void fromBytes(ByteBuf buf){
		int x = ByteBufUtils.readVarInt(buf, 5);
		int y = ByteBufUtils.readVarInt(buf, 5);
		int z = ByteBufUtils.readVarInt(buf, 5);
		this.pos = new BlockPos(x, y, z);
		this.nbt = ByteBufUtils.readTag(buf);
	}
	@Override
	public void toBytes(ByteBuf buf){
		ByteBufUtils.writeVarInt(buf, this.pos.getX(), 5);
		ByteBufUtils.writeVarInt(buf, this.pos.getY(), 5);
		ByteBufUtils.writeVarInt(buf, this.pos.getZ(), 5);
		ByteBufUtils.writeTag(buf, this.nbt);
	}

	public static class Handler implements IMessageHandler<TileEntityNBTMessage, IMessage>{
		/** Update block's NBT at the position */
		@Override
		public IMessage onMessage(TileEntityNBTMessage message, MessageContext ctx){
			World world;
			switch(ctx.side){
				case SERVER:
					world = TileEntityNBTMessage.getServerWorld(ctx);
					break;
				case CLIENT:
					world = TileEntityNBTMessage.getClientWorld();
					break;
				default:
					world = null;
			}
			if(world != null){
				TileEntity tile = world.getTileEntity(message.pos);
				NBTTagCompound tileNBT = new NBTTagCompound();
				tileNBT = message.nbt;
				tile.readFromNBT(tileNBT);
			}
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