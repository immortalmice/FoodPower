package com.github.immortalmice.foodpower.message;

import java.util.function.Supplier;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import com.github.immortalmice.foodpower.baseclass.MessageBase;
import com.github.immortalmice.foodpower.container.recipescroll.RecipeScrollContainer;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;
import com.github.immortalmice.foodpower.specialclass.RecipeScroll;

/* Used to trasfer RecipeScrollScreen data to update */
public class RecipeScrollMessage extends MessageBase<RecipeScrollMessage>{
	private String message;
	private int windowId;

	public RecipeScrollMessage(int windowIdIn, String messageIn){
		this.message = messageIn;
		this.windowId = windowIdIn;
	}

	public RecipeScrollMessage(){
		this(0, "");
	}

	public static void encode(RecipeScrollMessage msg, PacketBuffer buf){
		buf.writeInt(msg.getWindowId());
		buf.writeString(msg.getMessage());
	}

	public static RecipeScrollMessage decode(PacketBuffer buf){
		return new RecipeScrollMessage(buf.readInt(), buf.readString(32767));
	}

	/* Get the container and update data */
	public static void handle(RecipeScrollMessage msg, Supplier<NetworkEvent.Context> ctx){
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

	public int getWindowId(){
		return this.windowId;
	}

	public String getMessage(){
		return this.message;
	}
}