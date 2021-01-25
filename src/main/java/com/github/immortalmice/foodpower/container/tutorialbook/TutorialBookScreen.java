package com.github.immortalmice.foodpower.container.tutorialbook;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.container.tutorialbook.page.IPage;
import com.github.immortalmice.foodpower.container.tutorialbook.page.MainPage;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class TutorialBookScreen extends ScreenBase<TutorialBookContainer> {
	private int pageIndexCache = -1;
	private static List<IPage> PAGES = new ArrayList<>();
	
	public TutorialBookScreen(TutorialBookContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn) {
		super(containerIn, inventoryIn, textIn);
		
		this.textureFileName = "tutorial_book";
		this.xSize = 256;
		this.ySize = 256;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		if(this.pageIndexCache != this.container.getPage()) {
			this.buttons.clear();
			this.children.clear();
			this.pageIndexCache = this.container.getPage();
			
			if(this.pageIndexCache >= 0 && this.pageIndexCache <= PAGES.size() - 1) {
				TutorialBookScreen.PAGES.get(this.pageIndexCache).init(this::addButton);
			}
		}
		
		if(this.pageIndexCache >= 0 && this.pageIndexCache <= PAGES.size() - 1) {
			TutorialBookScreen.PAGES.get(this.pageIndexCache).render(mouseX, mouseY, this, this.font, this.itemRenderer);
		}
	}
	
	static {
		TutorialBookScreen.PAGES.add(new MainPage());
	}
}
