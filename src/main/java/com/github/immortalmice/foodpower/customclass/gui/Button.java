package com.github.immortalmice.foodpower.customclass.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import com.github.immortalmice.foodpower.FoodPower;

public class Button extends GuiButton{
	private int textureOffsetX, textureOffsetY;
	public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int textureOffsetXIn, int textureOffsetYIn){
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		textureOffsetX = textureOffsetXIn;
		textureOffsetY = textureOffsetYIn;
	}
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
		if(this.visible){
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			String path = FoodPower.MODID + ":textures/gui/container/button.png";
			mc.getTextureManager().bindTexture(new ResourceLocation(path));
			this.drawTexturedModalRect(this.x, this.y, this.textureOffsetX, this.textureOffsetY, this.width, this.height);
		}
	}
}