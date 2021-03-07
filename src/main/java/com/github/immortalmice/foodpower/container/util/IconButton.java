package com.github.immortalmice.foodpower.container.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

import com.github.immortalmice.foodpower.FoodPower;
import com.mojang.blaze3d.systems.RenderSystem;

public class IconButton extends Button{
	public enum ButtonType{
		WOOD_UP (18, 10, 20, 0, 20, 12),
		WOOD_DOWN (18, 10, 0, 0, 0, 12),
		WOOD_RIGHT (10, 18, 40, 0, 52, 0),
		WOOD_LEFT (10, 18, 40, 20, 52, 20),

		IRON_UP (18, 10, 84, 0, 84, 12),
		IRON_DOWN (18, 10, 64, 0, 64, 12),
		IRON_RIGHT (10, 18, 104, 0, 116, 0),
		IRON_LEFT (10, 18, 104, 20, 116, 20),

		GOLD_UP (18, 10, 148, 0, 148, 12),
		GOLD_DOWN (18, 10, 128, 0, 128, 12),
		GOLD_RIGHT (10, 18, 168, 0, 180, 0),
		GOLD_LEFT (10, 18, 168, 20, 180, 20),

		DIAMOND_UP (18, 10, 212, 0, 212, 12),
		DIAMOND_DOWN (18, 10, 192, 0, 192, 12),
		DIAMOND_RIGHT (10, 18, 232, 0, 244, 0),
		DIAMOND_LEFT (10, 18, 232, 20, 244, 20),
		
		SWITCH(19, 18, 0, 32, 0, 52),
		HOME(18, 18, 0, 72, 20, 72);

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

	private final IconButton.ButtonType buttonType;
	private static final ResourceLocation BUTTONS_TEXTURE_LOCATION = new ResourceLocation(FoodPower.MODID + ":textures/gui/container/button.png");

	public IconButton(int x, int y, Button.IPressable onPress){
		this(x, y, IconButton.ButtonType.WOOD_LEFT, onPress);
	}

	public IconButton(int x, int y, IconButton.ButtonType buttonTypeIn, Button.IPressable onPress){
		super(x, y, buttonTypeIn.width, buttonTypeIn.height, "", onPress);

		this.buttonType = buttonTypeIn;
	}

	@Override
	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
		if(this.visible){
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getInstance().getTextureManager().bindTexture(IconButton.BUTTONS_TEXTURE_LOCATION);

			if(this.isHovered()){
				this.blit(this.x, this.y, this.buttonType.hoverTextrureOffsetX, this.buttonType.hoverTextrureOffsetY, this.width, this.height);
			}else{
				this.blit(this.x, this.y, this.buttonType.textureOffsetX, this.buttonType.textureOffsetY, this.width, this.height);
			}
		}
	}
}