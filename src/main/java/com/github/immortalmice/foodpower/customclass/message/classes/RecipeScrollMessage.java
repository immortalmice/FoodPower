package com.github.immortalmice.foodpower.customclass.message.classes;

import java.util.function.Supplier;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.IMessageBase;
import com.github.immortalmice.foodpower.customclass.container.classes.recipescroll.RecipeScrollContainer;

public class RecipeScrollMessage implements IMessageBase<RecipeScrollMessage>{
	private String message;
	private int windowId;

	public RecipeScrollMessage(int windowIdIn, String messageIn){
		this.message = messageIn;
		this.windowId = windowIdIn;
	}

	public RecipeScrollMessage(){
		this(0, "");
	}

	@Override
	public void encode(RecipeScrollMessage msg, PacketBuffer buf){
		buf.writeInt(msg.getWindowId());
		buf.writeString(msg.getMessage());
	}

	@Override
	public RecipeScrollMessage decode(PacketBuffer buf){
		this.windowId = buf.readInt();
		this.message = buf.readString(32767);
		return this;
	}

	@Override
	public void handle(RecipeScrollMessage msg, Supplier<NetworkEvent.Context> ctx){
		ServerPlayerEntity player = ctx.get().getSender();
		if(player.openContainer instanceof RecipeScrollContainer){
			RecipeScrollContainer container = (RecipeScrollContainer) player.openContainer;
			if(container.getWindowId() == msg.getWindowId()){

			}
		}
	}

	@Override
	public void registMessage(int i) {
		FoodPower.NETWORK.registerMessage(
			i
			, RecipeScrollMessage.class
			, this::encode
			, this::decode
			, this::handle);
	}

	public int getWindowId(){
		return this.windowId;
	}

	public String getMessage(){
		return this.message;
	}
}