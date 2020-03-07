package com.github.immortalmice.foodpower.customclass.container.classes.recipescroll;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.customclass.container.classes.recipescroll.RecipeScrollContainer;
import com.github.immortalmice.foodpower.customclass.container.util.FPButton;
import com.github.immortalmice.foodpower.customclass.message.classes.RecipeScrollMessage;

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

	@Override
	public void init(){
		super.init();

		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		this.addButton(new FPButton(offsetX + 20, offsetY + 15, 10, 15, 38, 19, "", (button) ->{
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new RecipeScrollMessage(this.container.getWindowId()
					, "Set Amount"));
		}));

		this.addButton(new FPButton(offsetX + 20, offsetY + 15, 10, 15, 38, 19, "", (button) ->{
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new RecipeScrollMessage(this.container.getWindowId()
					, "Set Amount"));
		}));
	}
}