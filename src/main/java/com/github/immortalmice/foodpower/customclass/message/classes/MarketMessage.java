package com.github.immortalmice.foodpower.customclass.message.classes;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.IMessageBase;
import com.github.immortalmice.foodpower.customclass.container.classes.market.MarketContainer;

/* Used to trasfer MarketScreen data to update */
public class MarketMessage implements IMessageBase<MarketMessage>{
	private int windowId;
	private String message;

	public MarketMessage(){
		this(0, "");
	}

	public MarketMessage(int windowIdIn, String messageIn){
		this.windowId = windowIdIn;
		this.message = messageIn;
	}
	
	@Override
	public void encode(MarketMessage msg, PacketBuffer buf){
		buf.writeInt(msg.getWindowId());
		buf.writeString(msg.getMessage());
	}

	@Override
	public MarketMessage decode(PacketBuffer buf){
		this.windowId = buf.readInt();
		this.message = buf.readString(32767);
		return this;
	}

	/* Get the container and update data */
	@Override
	public void handle(MarketMessage msg, Supplier<NetworkEvent.Context> ctx){
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

	@Override
	public void registMessage(int i){
		FoodPower.NETWORK.registerMessage(
			i
			, MarketMessage.class
			, this::encode
			, this::decode
			, this::handle);
	}

	public String getMessage(){
		return message;
	}

	public int getWindowId(){
		return this.windowId;
	}
} 