package com.github.immortalmice.foodpower.container.chefcard;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.lists.Containers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class ChefCardContainer extends ContainerBase {
	public ChefCardContainer(int id, PlayerInventory inventoryIn, PacketBuffer extraData) {
		super(Containers.ContainerTypes.CHEF_CARD, id, new int[]{ -1, -1 }, inventoryIn);
	}
	
	public ChefCardContainer(int id, PlayerInventory inventoryIn) {
		this(id, inventoryIn, null);
	}
}
