package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.mojang.blaze3d.systems.RenderSystem;

public class ScreenBase<T extends ContainerBase> extends ContainerScreen<T>{
	public static final ResourceLocation SLOT_TEXTURE = new ResourceLocation(FoodPower.MODID + ":textures/gui/container/ingredient_block.png");

	protected String textureFileName = "default";

	public ScreenBase(T containerIn, PlayerInventory inventoryIn, ITextComponent textIn){
		super(containerIn, inventoryIn, textIn);

		this.xSize = 176;
		this.ySize = 133;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.minecraft.getTextureManager().bindTexture(this.getTexture());

        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.blit(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks){
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	/* textureFileName will be overwrite in constructor of subClass */
	private ResourceLocation getTexture(){
		String path = FoodPower.MODID + ":textures/gui/container/" + this.textureFileName + ".png";
		return new ResourceLocation(path);
	}
}