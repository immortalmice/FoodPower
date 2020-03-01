package com.github.immortalmice.foodpower.customclass.gui.recipescroll;

import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.gui.ModGuiContainer;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;

public class RecipeScrollGuiContainer extends ModGuiContainer{
	ItemStack scroll;
	public RecipeScrollGuiContainer(ModContainer inventorySlotsIn){
		super(inventorySlotsIn, new int[]{256, 256});

		ItemStack holdItemStack = inventorySlotsIn.getPlayer().getHeldEquipment().iterator().next();
		if(holdItemStack.getItem() instanceof RecipeScroll){
			scroll = holdItemStack;
		}
		this.textureFileName = "recipe_scroll";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String scrollName = scroll.getItem().getItemStackDisplayName(scroll);
		this.fontRenderer.drawString(scrollName, (this.xSize - this.fontRenderer.getStringWidth(scrollName)) / 2, 20, 0x404040);
	}
}