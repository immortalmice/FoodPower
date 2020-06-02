package com.github.immortalmice.foodpower.customclass.container.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.FoodPower;
import com.mojang.blaze3d.systems.RenderSystem;

public class FPButton extends Button{
	private int textureOffsetX, textureOffsetY;
	public FPButton(int x, int y
			, int widthIn, int heightIn
			, int textureOffsetXIn, int textureOffsetYIn
			, String buttonText, Button.IPressable onPress){

		super(x, y, widthIn, heightIn, buttonText, onPress);
		textureOffsetX = textureOffsetXIn;
		textureOffsetY = textureOffsetYIn;
	}
	@Override
	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
		if(this.visible){
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			String path = FoodPower.MODID + ":textures/gui/container/button.png";
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(path));
			this.blit(this.x, this.y, this.textureOffsetX, this.textureOffsetY, this.width, this.height);
		}
	}
}