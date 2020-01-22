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
	public ModGuiContainer(ModContainer inventorySlotsIn){
		super(inventorySlotsIn);
		
		this.xSize = 176;
        this.ySize = 133;
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

	private ResourceLocation getTexture(){
		String path = FoodPower.MODID + ":textures/gui/container/" + this.textureFileName + ".png";
		return new ResourceLocation(path);
	}
}