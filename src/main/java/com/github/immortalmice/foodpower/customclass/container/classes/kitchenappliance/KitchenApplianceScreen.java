package com.github.immortalmice.foodpower.customclass.container.classes.kitchenappliance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.customclass.client.TooltipUtil;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe.ItemStackRequest;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.KitchenApplianceTileEntity;
import com.github.immortalmice.foodpower.customclass.util.SlotPosProvider.KitchenApplianceSlotPos;
import com.github.immortalmice.foodpower.customclass.util.SlotPosProvider.Position2D;

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
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		List<ItemStackRequest> itemRequests = this.container.getCurrentItemRequests();
		List<Position2D> slotPos = KitchenApplianceSlotPos.provide(itemRequests.size()).stream().map((pos) -> pos.translate(-1)).collect(Collectors.toList());
		
		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		this.minecraft.getTextureManager().bindTexture(ScreenBase.SLOT_TEXTURE);

		for(int i = 0; i <= itemRequests.size()-1; i ++){
			this.blit(
				offsetX + slotPos.get(i).x, offsetY + slotPos.get(i).y
				, 20, 40
				, 18, 18);
		}
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
		if(this.hoveredSlot != null){

			FontRenderer font = null;
			List<String> text = new ArrayList<>();

			if(this.hoveredSlot.getHasStack()){
				ItemStack stack = this.hoveredSlot.getStack();
				font = stack.getItem().getFontRenderer(stack);
				text = this.getTooltipFromItem(stack);
			}

			if(this.minecraft.player.inventory.getItemStack().isEmpty()
				&& this.hoveredSlot.getSlotIndex() == 0 /* RecipeScroll Slot */){
				
				text.add(TooltipUtil.translate("message.foodpower.tooltip_remove_before_take"));
			}else if(this.hoveredSlot instanceof KitchenApplianceSlot){

				KitchenApplianceSlot slot = (KitchenApplianceSlot) this.hoveredSlot;
				String itemStr = TooltipUtil.translate(slot.getRequest().getItem().getTranslationKey());

				text.add(0, TooltipUtil.translate("message.foodpower.tooltip_required", itemStr, slot.getRequest().getAmount()));
			}

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