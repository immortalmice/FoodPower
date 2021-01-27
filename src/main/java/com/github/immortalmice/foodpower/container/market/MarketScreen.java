package com.github.immortalmice.foodpower.container.market;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.container.util.IconButton;
import com.github.immortalmice.foodpower.message.MarketMessage;

public class MarketScreen extends ScreenBase<MarketContainer>{

	public MarketScreen(MarketContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn){
		super(containerIn, inventoryIn, textIn);

		this.textureFileName = "market";
		this.xSize = 256;
		this.ySize = 212;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		/* Draw Icon & name with good */
		ItemStack item = new ItemStack(this.getContainer().getItem());
		this.itemRenderer.renderItemAndEffectIntoGUI(item, 60, 35);
		String itemName = I18n.format(item.getItem().getTranslationKey());
		this.font.drawString(itemName, 70 - (this.font.getStringWidth(itemName) / 2), 60, 0x404040);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks){
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
	}

	@Override
	public void init(){
		super.init();

		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		this.addButton(new IconButton(offsetX + 35, offsetY + 35, IconButton.ButtonType.WOOD_LEFT, (button) -> {
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new MarketMessage(this.getContainer().getWindowId(), "Decrease Index")
			);
		}));
		this.addButton(new IconButton(offsetX + 90, offsetY + 35, IconButton.ButtonType.WOOD_RIGHT, (button) -> {
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new MarketMessage(this.getContainer().getWindowId(), "Increase Index")
			);
		}));
	}
}