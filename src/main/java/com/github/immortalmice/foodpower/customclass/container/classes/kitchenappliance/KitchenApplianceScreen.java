package com.github.immortalmice.foodpower.customclass.container.classes.kitchenappliance;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class KitchenApplianceScreen extends ScreenBase<KitchenApplianceContainer>{
	public KitchenApplianceScreen(KitchenApplianceContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn){
		super(containerIn, inventoryIn, textIn);

		if(this.container.getBlock() != null){
			this.textureFileName = this.container.getBlock().getRegistryName().getPath();
		}
		this.xSize = 256;
		this.ySize = 256;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		this.font.drawString(Integer.toString(this.container.getTileEntity().getEnergyStored()), 10, 10, 0x404040);
	}
}