package com.github.immortalmice.foodpower.customclass.container.classes.recipescroll;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.customclass.container.classes.recipescroll.RecipeScrollContainer;

public class RecipeScrollScreen extends ScreenBase<RecipeScrollContainer>{
	CompoundNBT scroll_nbt;
	public RecipeScrollScreen(RecipeScrollContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn){
		super(containerIn, inventoryIn, textIn);

		this.textureFileName = "recipe_scroll";
		this.xSize = 256;
		this.ySize = 256;
		this.scroll_nbt = containerIn.getScrollTag();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String scrollName = scroll_nbt.contains("displayName") && !scroll_nbt.getString("displayName").isEmpty()
			? scroll_nbt.getString("displayName")
			: I18n.format("general.foodpower.unknown_recipe");

		this.font.drawString(scrollName, (this.xSize - this.font.getStringWidth(scrollName)) / 2, 20, 0x404040);
	}
}