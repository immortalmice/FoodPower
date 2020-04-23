package com.github.immortalmice.foodpower.customclass.container.classes.market;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.customclass.container.util.FPButton;
import com.github.immortalmice.foodpower.customclass.container.classes.market.MarketContainer;
import com.github.immortalmice.foodpower.customclass.message.classes.MarketMessage;

@OnlyIn(Dist.CLIENT)
public class MarketScreen extends ScreenBase<MarketContainer>{

	public MarketScreen(MarketContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn){
		super(containerIn, inventoryIn, textIn);

		this.textureFileName = "market";
		this.xSize = 176;
		this.ySize = 133;
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
		this.itemRenderer.renderItemAndEffectIntoGUI(item, 35, 15);
		String itemName = I18n.format(item.getItem().getTranslationKey());
		this.font.drawString(itemName, 45 - (this.font.getStringWidth(itemName) / 2), 35, 0x404040);
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
		this.addButton(new FPButton(offsetX + 20, offsetY + 15, 10, 15, 38, 19, "", (button) -> {
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new MarketMessage(this.getContainer().getWindowId(), "Decrease Index")
			);
		}));
		this.addButton(new FPButton(offsetX + 60, offsetY + 15, 10, 15, 38, 0, "", (button) -> {
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new MarketMessage(this.getContainer().getWindowId(), "Increase Index")
			);
		}));
	}
}