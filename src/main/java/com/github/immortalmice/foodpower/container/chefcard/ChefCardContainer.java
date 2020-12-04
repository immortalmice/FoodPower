package com.github.immortalmice.foodpower.container.chefcard;

import java.util.HashMap;
import java.util.Map;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.cooking.CookingPattern;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.types.FlavorType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;

public class ChefCardContainer extends ContainerBase {
	private Map<FlavorType, Integer> flavorExps = new HashMap<>();
	private Map<CookingPattern, Integer> patternExps = new HashMap<>();
	
	public ChefCardContainer(int id, PlayerInventory inventoryIn, PacketBuffer extraData) {
		this(id, inventoryIn, extraData.readCompoundTag());
	}
	
	public ChefCardContainer(int id, PlayerInventory inventoryIn, INBT nbtIn) {
		super(Containers.ContainerTypes.CHEF_CARD, id, new int[]{ -1, -1 }, inventoryIn);

		PlayerEntity player = inventoryIn.player;
		ChefCardContainer.readFromNBT(nbtIn, player);
		player.getCapability(Capabilities.FLAVOR_EXP_CAPABILITY).ifPresent(cap -> {
			this.flavorExps = cap.getAllExpLevel();
		});
		player.getCapability(Capabilities.PATTERN_EXP_CAPABILITY).ifPresent(cap -> {
			this.patternExps = cap.getAllExpLevel();
		});
	}
	
	public static INBT writeToNBT(PlayerEntity player) {
		CompoundNBT nbt = new CompoundNBT();
		player.getCapability(Capabilities.FLAVOR_EXP_CAPABILITY).ifPresent(cap -> {
			nbt.put("flavor", Capabilities.FLAVOR_EXP_CAPABILITY.getStorage().writeNBT(Capabilities.FLAVOR_EXP_CAPABILITY, cap, null));
		});
		player.getCapability(Capabilities.PATTERN_EXP_CAPABILITY).ifPresent(cap -> {
			nbt.put("pattern", Capabilities.PATTERN_EXP_CAPABILITY.getStorage().writeNBT(Capabilities.PATTERN_EXP_CAPABILITY, cap, null));
		});
		return nbt;
	}
	
	public static void readFromNBT(INBT nbtIn, PlayerEntity player) {
		CompoundNBT nbt = (CompoundNBT) nbtIn;
		player.getCapability(Capabilities.FLAVOR_EXP_CAPABILITY).ifPresent(cap -> {
			Capabilities.FLAVOR_EXP_CAPABILITY.getStorage().readNBT(Capabilities.FLAVOR_EXP_CAPABILITY, cap, null, nbt.get("flavor"));
		});
		player.getCapability(Capabilities.PATTERN_EXP_CAPABILITY).ifPresent(cap -> {
			Capabilities.PATTERN_EXP_CAPABILITY.getStorage().readNBT(Capabilities.PATTERN_EXP_CAPABILITY, cap, null, nbt.get("pattern"));
		});
	}
	
	public Map<FlavorType, Integer> getFlavorExp() {
		return this.flavorExps;
	}
	
	public Map<CookingPattern, Integer> getPatternExps() {
		return this.patternExps;
	}
}
