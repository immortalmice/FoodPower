package com.github.immortalmice.foodpower.message;

import java.util.function.Supplier;

import com.github.immortalmice.foodpower.baseclass.MessageBase;
import com.github.immortalmice.foodpower.container.kitchenappliance.KitchenApplianceContainer;
import com.github.immortalmice.foodpower.tileentity.KitchenApplianceTileEntity;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class KitchenApplianceMessage extends MessageBase<KitchenApplianceMessage>{
	private int windowId;
	private String message;
	private BlockPos position;
	
	public KitchenApplianceMessage(){
		this(0, new BlockPos(0, 0, 0), "");
	}
	
	public KitchenApplianceMessage(int windowIdIn, BlockPos positionIn, String messageIn){
		this.windowId = windowIdIn;
		this.position = positionIn;
		this.message = messageIn;
	}
	
	public static void encode(KitchenApplianceMessage msg, PacketBuffer buf){
		buf.writeInt(msg.getWindowId());
		buf.writeBlockPos(msg.getPosition());
		buf.writeString(msg.getMessage());
	}

	public static KitchenApplianceMessage decode(PacketBuffer buf){
		return new KitchenApplianceMessage(buf.readInt(), buf.readBlockPos(), buf.readString(32767));
	}

	public static void handle(KitchenApplianceMessage msg, Supplier<NetworkEvent.Context> ctx){
		ServerPlayerEntity player = ctx.get().getSender();
		if(player != null){
			TileEntity t = player.world.getTileEntity(msg.getPosition());
			if(t instanceof KitchenApplianceTileEntity){
				KitchenApplianceTileEntity tileEntity = (KitchenApplianceTileEntity) t;
				tileEntity.getItemHandler().rollRequestIndex();
				if(player.openContainer instanceof KitchenApplianceContainer){
					((KitchenApplianceContainer) player.openContainer).updateSlot();
				}
			}
		}
		ctx.get().setPacketHandled(true);
	}

	public int getWindowId(){
		return windowId;
	}
	
	public BlockPos getPosition() {
		return position;
	}

	public String getMessage(){
		return message;
	}
}
