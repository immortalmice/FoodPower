package com.github.immortalmice.foodpower.customclass.message.classes;

import java.util.function.Supplier;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.IMessageBase;
import com.github.immortalmice.foodpower.customclass.container.classes.recipetable.RecipeTableContainer;

/* Used to trasfer RecipeTable data to update */
public class RecipeTableMessage implements IMessageBase<RecipeTableMessage>{
	private String action, message;
	private int windowId;

	public RecipeTableMessage(int windowidIn, String actionIn, String messageIn){
		this.windowId = windowidIn;
		this.action = actionIn;
		this.message = messageIn;
	}
	public RecipeTableMessage(){
		this(0, "", "");
	}

	@Override
	public void encode(RecipeTableMessage msg, PacketBuffer buf){
		buf.writeInt(msg.getWindowId());
		buf.writeString(msg.getAction());
		buf.writeString(msg.getMessage());
	}

	@Override
	public RecipeTableMessage decode(PacketBuffer buf){
		this.windowId = buf.readInt();
		this.action = buf.readString(32767);
		this.message = buf.readString(32767);
		return this;
	}

	@Override
	public void handle(RecipeTableMessage msg, Supplier<NetworkEvent.Context> ctx){
		ServerPlayerEntity player = ctx.get().getSender();
		if(player.openContainer instanceof RecipeTableContainer){
			RecipeTableContainer container = (RecipeTableContainer) player.openContainer;
			if(container.getWindowId() == msg.getWindowId()){
				switch(msg.getAction()){
					case "Set Index":
						switch(msg.getMessage()){
							case "Decrease":
								container.decreaseIndex();
								break;
							case "Increase":
								container.increaseIndex();
								break;
							default:
						}
						break;
					case "Set InputText":
						container.setInputText(msg.getMessage());
						break;
				}
			}
		}
		ctx.get().setPacketHandled(true);
	}

	@Override
	public void registMessage(int i) {
		FoodPower.NETWORK.registerMessage(
			i
			, RecipeTableMessage.class
			, this::encode
			, this::decode
			, this::handle);
	}

	public int getWindowId(){
		return this.windowId;
	}

	public String getAction(){
		return this.action;
	}

	public String getMessage(){
		return this.message;
	}
}