package com.github.immortalmice.foodpower.container.tutorialbook.page;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;

public class TutorialPage extends AbstractHomeablePage {
	private final String section;
	
	public TutorialPage(String sectionIn) {
		super("tutorial_book.foodpower.tutorials." + sectionIn);
		
		this.section = sectionIn;
	}
	
	public void render(int mouseX, int mouseY, Screen screen, FontRenderer font, ItemRenderer itemRenderer) {
		super.render(mouseX, mouseY, screen, font, itemRenderer);
		
		font.drawSplitString(I18n.format("tutorial_book.foodpower.tutorials." + this.section + ".content"), 28, 50, 200, 0x404040);
	}
}
