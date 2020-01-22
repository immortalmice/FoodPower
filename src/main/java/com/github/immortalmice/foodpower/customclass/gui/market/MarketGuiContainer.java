package com.github.immortalmice.foodpower.customclass.gui.market;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.immortalmice.foodpower.customclass.gui.ModGuiContainer;
import com.github.immortalmice.foodpower.customclass.gui.ModContainer;

@SideOnly(Side.CLIENT)
public class MarketGuiContainer extends ModGuiContainer{
	public MarketGuiContainer(ModContainer inventorySlotsIn){
		super(inventorySlotsIn);

		this.textureFileName = "market";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}
}