package com.github.immortalmice.foodpower.customclass.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.common.util.Constants;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;

import com.github.immortalmice.foodpower.customclass.tileentity.classes.RecipeTableTileEntity;
import com.github.immortalmice.foodpower.baseclass.MessageHandlerBase;

public class RecipeTableMessage implements IMessage{
	private String message;
	private BlockPos pos;

	public RecipeTableMessage(String messageIn, BlockPos posIn){
		this.message = messageIn;
		this.pos = posIn;
	}
	public RecipeTableMessage(){

	}

	@Override
	public void fromBytes(ByteBuf buf){
		int x = ByteBufUtils.readVarInt(buf, 5);
		int y = ByteBufUtils.readVarInt(buf, 5);
		int z = ByteBufUtils.readVarInt(buf, 5);
		this.pos = new BlockPos(x, y, z);
		this.message = ByteBufUtils.readUTF8String(buf);
	}
	@Override
	public void toBytes(ByteBuf buf){
		ByteBufUtils.writeVarInt(buf, this.pos.getX(), 5);
		ByteBufUtils.writeVarInt(buf, this.pos.getY(), 5);
		ByteBufUtils.writeVarInt(buf, this.pos.getZ(), 5);
		ByteBufUtils.writeUTF8String(buf, this.message);
	}

	public String getMessage(){
		return message;
	}

	public static class Handler extends MessageHandlerBase implements IMessageHandler<RecipeTableMessage, IMessage>{
		@Override
		public IMessage onMessage(RecipeTableMessage message, MessageContext ctx){
			World world = Handler.getWorld(ctx);
			if(world != null){
				TileEntity tile = world.getTileEntity(message.pos);
				if(tile instanceof RecipeTableTileEntity){
					switch(message.getMessage()){
						case "Decrease Index":
							((RecipeTableTileEntity) tile).decreaseIndex();
							break;
						case "Increase Index":
							((RecipeTableTileEntity) tile).increaseIndex();
							break;
						default:
					}
				}
				IBlockState blockState = world.getBlockState(message.pos);
				world.notifyBlockUpdate(message.pos, blockState, blockState, Constants.BlockFlags.SEND_TO_CLIENTS);
			}
			return null;
		}
	}
}