package com.github.immortalmice.foodpower.customclass.container.classes.kitchenappliance;

import java.util.List;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.customclass.client.TooltipUtil;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.KitchenApplianceTileEntity;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
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
	public void render(int x, int y, float partialTick){
		super.render(x, y, partialTick);
		if(this.isElectricalCatch){
			int yEnd = ELECTRIC_SLOT_Y + this.guiTop + ELECTRIC_SLOT_HEIGHT;
			int yStart = yEnd - this.getElectricSlotFillHeight();
			
			int xStart = ELECTRIC_SLOT_X + this.guiLeft;
			int xEnd = xStart + ELECTRIC_SLOT_WIDTH;
			AbstractGui.fill(xStart, yStart
					, xEnd, yEnd, -8323200);

			if(x >= xStart && x <= xEnd
				&& y >= yEnd - ELECTRIC_SLOT_HEIGHT && y <= yEnd){
				KitchenApplianceTileEntity tileEntity = this.container.getTileEntity();
				if(tileEntity != null){
					String tooltipStr = Integer.toString(tileEntity.getEnergyStored()) + "FE";
					tooltipStr += "/" + Integer.toString(tileEntity.getMaxEnergyStored()) + "FE";
					this.renderTooltip(tooltipStr, x, y);
				}
			}
		}
	}

	@Override
	protected void renderHoveredToolTip(int mouseX, int mouseY){
		if(this.minecraft.player.inventory.getItemStack().isEmpty()
			&& this.hoveredSlot != null
			&& this.hoveredSlot.getSlotIndex() == 0 // RecipeScroll Slot
			&& this.hoveredSlot.getHasStack()){

			ItemStack stack = this.hoveredSlot.getStack();
			FontRenderer font = stack.getItem().getFontRenderer(stack);

			List<String> text = this.getTooltipFromItem(stack);
			text.add(TooltipUtil.translate("message.foodpower.tooltip_remove_before_take"));

			this.renderTooltip(text, mouseX, mouseY, (font == null ? this.font : font));
		}else{
			super.renderHoveredToolTip(mouseX, mouseY);
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