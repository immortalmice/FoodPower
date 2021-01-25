package com.github.immortalmice.foodpower.container.tutorialbook;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class TutorialBookScreen extends ScreenBase<TutorialBookContainer> {
	public TutorialBookScreen(TutorialBookContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn) {
		super(containerIn, inventoryIn, textIn);
		
		this.textureFileName = "tutorial_book";
		this.xSize = 256;
		this.ySize = 256;
	}
}
