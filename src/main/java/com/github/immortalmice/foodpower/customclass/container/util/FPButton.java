package com.github.immortalmice.foodpower.customclass.container.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

import com.github.immortalmice.foodpower.FoodPower;
import com.mojang.blaze3d.systems.RenderSystem;

public class FPButton extends Button{
	public enum ButtonType{
		WOOD_UP (18, 10, 84, 0, 84, 12),
		WOOD_DOWN (18, 10, 64, 0, 64, 12),
		WOOD_RIGHT (10, 18, 104, 0, 116, 0),
		WOOD_LEFT (10, 18, 104, 20, 116, 20),

		STONE_UP (15, 10, 0, 0, 0, 12),
		STONE_DOWN (15, 10, 19, 0, 19, 12),
		STONE_RIGHT (10, 15, 38, 0, 50, 0),
		STONE_LEFT (10, 15, 38, 19, 50, 19);

		public final int width, height;
		public final int textureOffsetX, textureOffsetY;
		public final int hoverTextrureOffsetX, hoverTextrureOffsetY;

		ButtonType(int widthIn, int heightIn, int textureOffsetXIn, int textureOffsetYIn){
			this(widthIn, heightIn, textureOffsetXIn, textureOffsetYIn, textureOffsetXIn, textureOffsetYIn);
		}

		ButtonType(int widthIn, int heightIn, int textureOffsetXIn, int textureOffsetYIn, int hoverTextrureOffsetXIn, int hoverTextrureOffsetYIn){

			this.width = widthIn;
			this.height = heightIn;
			this.textureOffsetX = textureOffsetXIn;
			this.textureOffsetY = textureOffsetYIn;
			this.hoverTextrureOffsetX = hoverTextrureOffsetXIn;
			this.hoverTextrureOffsetY = hoverTextrureOffsetYIn;
		}
	};

	private final FPButton.ButtonType buttonType;
	private static final ResourceLocation BUTTONS_TEXTURE_LOCATION = new ResourceLocation(FoodPower.MODID + ":textures/gui/container/button.png");

	public FPButton(int x, int y, FPButton.ButtonType buttonTypeIn, Button.IPressable onPress){
		super(x, y, buttonTypeIn.width, buttonTypeIn.height, "", onPress);

		this.buttonType = buttonTypeIn;
	}

	@Override
	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
		if(this.visible){
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getInstance().getTextureManager().bindTexture(FPButton.BUTTONS_TEXTURE_LOCATION);

			if(this.isHovered()){
				this.blit(this.x, this.y, this.buttonType.hoverTextrureOffsetX, this.buttonType.hoverTextrureOffsetY, this.width, this.height);
			}else{
				this.blit(this.x, this.y, this.buttonType.textureOffsetX, this.buttonType.textureOffsetY, this.width, this.height);
			}
		}
	}
}