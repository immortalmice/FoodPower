package com.github.immortalmice.foodpower.customclass.message.classes;

import java.util.function.Supplier;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.IMessageBase;
import com.github.immortalmice.foodpower.customclass.container.classes.recipescroll.RecipeScrollContainer;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;

/* Used to trasfer RecipeScrollScreen data to update */
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

	/* Get the container and update data */
	@Override
	public void handle(RecipeScrollMessage msg, Supplier<NetworkEvent.Context> ctx){
		ServerPlayerEntity player = ctx.get().getSender();
		if(player.openContainer instanceof RecipeScrollContainer){
			RecipeScrollContainer container = (RecipeScrollContainer) player.openContainer;
			if(container.getWindowId() == msg.getWindowId()){
				if(player.getHeldEquipment().iterator().next().getItem() == Items.RECIPE_SCROLL){
					ItemStack scroll = player.getHeldEquipment().iterator().next();
					int finalAmount = 1;
					switch(msg.getMessage()){
						case "Set Amount Minus":
							finalAmount = RecipeScroll.addOutputAmount(scroll, -1);
							break;
						case "Set Amount Add":
							finalAmount = RecipeScroll.addOutputAmount(scroll, 1);
							break;
						default:
					}
					container.setAmount(finalAmount);
				}
			}
		}
		ctx.get().setPacketHandled(true);
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