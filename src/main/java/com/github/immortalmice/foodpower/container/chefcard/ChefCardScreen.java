package com.github.immortalmice.foodpower.container.chefcard;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ChefCardScreen extends ScreenBase<ChefCardContainer> {
	public ChefCardScreen(ChefCardContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn) {
		super(containerIn, inventoryIn, textIn);
		
		this.textureFileName = "chef_card";
		this.xSize = 256;
		this.ySize = 128;
	}
}
