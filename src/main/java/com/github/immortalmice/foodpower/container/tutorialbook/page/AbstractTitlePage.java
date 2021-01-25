package com.github.immortalmice.foodpower.container.tutorialbook.page;	

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;

public class AbstractTitlePage implements IPage {
	private String titleKey;
	
	public AbstractTitlePage(String titleKeyIn) {
		super();
		
		this.titleKey = titleKeyIn;
	}
	
	@Override
	public void render(int mouseX, int mouseY, Screen screen, FontRenderer font, ItemRenderer itemRenderer) {
		font.drawString(I18n.format(this.titleKey), (256 - font.getStringWidth(I18n.format(this.titleKey))) / 2, 20, 0x404040);
	}
}
