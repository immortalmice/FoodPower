package com.github.immortalmice.foodpower.container.util;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;

public class TextButton extends Button {
	private final FontRenderer fontRenderer;
	
	public TextButton(int x, int y, FontRenderer fontRendererIn, String text, IPressable onPress) {
		super(x, y, fontRendererIn.getStringWidth(text), 20, text, onPress);
		
		this.fontRenderer = fontRendererIn;
	}
	

	@Override
	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
		if(this.visible){
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

			if(this.isHovered()){
				this.fontRenderer.drawString(this.getMessage(), this.x, this.y, 0x0000FF);
			}else{
				this.fontRenderer.drawString(this.getMessage(), this.x, this.y, 0x404040);
			}
		}
	}
}
