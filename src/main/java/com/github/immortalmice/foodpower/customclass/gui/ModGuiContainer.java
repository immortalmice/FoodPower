package com.github.immortalmice.foodpower.customclass.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.gui.ModContainer;

@SideOnly(Side.CLIENT)
public class ModGuiContainer extends GuiContainer{
	protected String textureFileName = "default";
	public ModGuiContainer(ModContainer inventorySlotsIn, int[] sizeIn){
		super(inventorySlotsIn);
		
		this.xSize = sizeIn[0];
        this.ySize = sizeIn[1];
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(this.getTexture());

        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	/** textureFileName will be overwrite in constructor of subClass */
	private ResourceLocation getTexture(){
		String path = FoodPower.MODID + ":textures/gui/container/" + this.textureFileName + ".png";
		return new ResourceLocation(path);
	}
}