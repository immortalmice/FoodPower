package com.github.immortalmice.foodpower.customclass.container.classes.kitchenappliance;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.lists.Containers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.registries.ForgeRegistries;

public class KitchenApplianceContainer extends ContainerBase{
	private KitchenAppliance block;

	public KitchenApplianceContainer(int windowId, PlayerInventory playerInventory, PacketBuffer extraData){
		this(windowId, playerInventory, ForgeRegistries.BLOCKS.getValue(extraData.readResourceLocation()));
	}
	public KitchenApplianceContainer(int windowId, PlayerInventory playerInventory, Block blockIn){
		super(Containers.ContainerTypes.KITCHEN_APPLIANCE, windowId, new int[]{45, 145}, playerInventory);

		if(blockIn instanceof KitchenAppliance){
			this.block = (KitchenAppliance) blockIn;
		}
	}

	public KitchenAppliance getBlock(){
		return this.block;
	}
}