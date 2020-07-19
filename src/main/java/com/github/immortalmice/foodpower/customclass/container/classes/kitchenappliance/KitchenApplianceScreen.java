package com.github.immortalmice.foodpower.customclass.container.classes.kitchenappliance;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.KitchenApplianceTileEntity;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class KitchenApplianceScreen extends ScreenBase<KitchenApplianceContainer>{
	private static final int ELECTRIC_SLOT_X = 18, ELECTRIC_SLOT_Y = 18;
	private static final int ELECTRIC_SLOT_WIDTH = 12, ELECTRIC_SLOT_HEIGHT = 202;
	
	private final boolean isElectricalCatch;

	public KitchenApplianceScreen(KitchenApplianceContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn){
		super(containerIn, inventoryIn, textIn);

		if(this.container.getBlock() != null){
			this.textureFileName = this.container.getBlock().getRegistryName().getPath();
		}
		this.isElectricalCatch = this.container.getBlock() == null ? false : this.container.getBlock().isElectrical();
		this.xSize = 256;
		this.ySize = 256;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		this.font.drawString(Integer.toString(this.container.getTileEntity().getEnergyStored()), 10, 10, 0x404040);
	}

	@Override
	public void render(int x, int y, float partialTick){
		super.render(x, y, partialTick);
		if(this.isElectricalCatch){
			int yEnd = ELECTRIC_SLOT_Y + this.guiTop + ELECTRIC_SLOT_HEIGHT;
			int yStart = yEnd - this.getElectricSlotFillHeight();
			
			int xStart = ELECTRIC_SLOT_X + this.guiLeft;
			int xEnd = xStart + ELECTRIC_SLOT_WIDTH;
			AbstractGui.fill(xStart, yStart
					, xEnd, yEnd, -8323200);
		}
	}

	private int getElectricSlotFillHeight(){
		KitchenApplianceTileEntity tileEntity = this.container.getTileEntity();
		if(tileEntity != null){
			float ratio = (float) tileEntity.getEnergyStored() / tileEntity.getMaxEnergyStored();
			return Math.round(KitchenApplianceScreen.ELECTRIC_SLOT_HEIGHT * ratio);
		}
		return 0;
	}
}