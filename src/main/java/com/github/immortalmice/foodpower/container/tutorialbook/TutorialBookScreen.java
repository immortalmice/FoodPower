package com.github.immortalmice.foodpower.container.tutorialbook;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class TutorialBookScreen extends ScreenBase<TutorialBookContainer> {
	private int pageIndexCache = -1;
	
	public TutorialBookScreen(TutorialBookContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn) {
		super(containerIn, inventoryIn, textIn);
		
		this.textureFileName = "tutorial_book";
		this.xSize = 256;
		this.ySize = 256;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		if(this.pageIndexCache != this.container.navigator.getPage()) {
			this.buttons.clear();
			this.children.clear();
			this.pageIndexCache = this.container.navigator.getPage();
			
			if(this.pageIndexCache >= 0 && this.pageIndexCache <= TutorialBookContainer.PAGES.size() - 1) {
				TutorialBookContainer.PAGES.get(this.pageIndexCache).init(this::addButton, this.container.navigator);
			}
		}
		
		if(this.pageIndexCache >= 0 && this.pageIndexCache <= TutorialBookContainer.PAGES.size() - 1) {
			TutorialBookContainer.PAGES.get(this.pageIndexCache).render(mouseX, mouseY, this, this.font, this.itemRenderer);
		}
	}
}
