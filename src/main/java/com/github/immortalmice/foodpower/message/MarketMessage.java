package com.github.immortalmice.foodpower.message;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import com.github.immortalmice.foodpower.baseclass.MessageBase;
import com.github.immortalmice.foodpower.container.market.MarketContainer;

/* Used to trasfer MarketScreen data to update */
public class MarketMessage extends MessageBase<MarketMessage>{
	private int windowId;
	private String message;

	public MarketMessage(){
		this(0, "");
	}

	public MarketMessage(int windowIdIn, String messageIn){
		this.windowId = windowIdIn;
		this.message = messageIn;
	}
	
	public static void encode(MarketMessage msg, PacketBuffer buf){
		buf.writeInt(msg.getWindowId());
		buf.writeString(msg.getMessage());
	}

	public static MarketMessage decode(PacketBuffer buf){
		return new MarketMessage(buf.readInt(), buf.readString(32767));
	}

	/* Get the container and update data */
	public static void handle(MarketMessage msg, Supplier<NetworkEvent.Context> ctx){
		ServerPlayerEntity player = ctx.get().getSender();
		if(player.openContainer instanceof MarketContainer){
			MarketContainer container = (MarketContainer) player.openContainer;
			if(container.getWindowId() == msg.getWindowId()){
				switch(msg.getMessage()){
					case "Decrease Index":
						container.decreaseIndex();
						break;
					case "Increase Index":
						container.increaseIndex();
						break;
					default:
				}
			}
		}
		ctx.get().setPacketHandled(true);
	}

	public String getMessage(){
		return message;
	}

	public int getWindowId(){
		return this.windowId;
	}
} 