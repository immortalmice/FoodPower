package com.github.immortalmice.foodpower.customclass.container.classes.recipescroll;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.customclass.container.classes.recipescroll.RecipeScrollContainer;
import com.github.immortalmice.foodpower.customclass.container.util.FPButton;
import com.github.immortalmice.foodpower.customclass.message.classes.RecipeScrollMessage;

public class RecipeScrollScreen extends ScreenBase<RecipeScrollContainer>{
	public RecipeScrollScreen(RecipeScrollContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn){
		super(containerIn, inventoryIn, textIn);

		this.textureFileName = "recipe_scroll";
		switch(this.container.getRarity()){
			case 1:
				this.textureFileName += "_iron";
				break;
			case 2:
				this.textureFileName += "_gold";
				break;
			case 3:
				this.textureFileName += "_diamond";
				break;
			case 0:
			default:
				this.textureFileName += "_wood";
				break;
		}
		this.xSize = 256;
		this.ySize = 256;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String scrollName = this.getContainer().getScrollName();
		String amount = Integer.toString(this.getContainer().getAmount());
		this.font.drawString(scrollName, (this.xSize - this.font.getStringWidth(scrollName)) / 2, 20, 0x404040);
		this.font.drawString(amount, (this.xSize - this.font.getStringWidth(amount)) / 2, 45, 0x404040);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks){
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
	}

	@Override
	public void init(){
		super.init();

		int offsetY = (this.height - this.ySize) / 2;
		int centerX = this.width / 2;
		this.addButton(new FPButton(centerX - 30, offsetY + 40, FPButton.ButtonType.STONE_LEFT, (button) ->{
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new RecipeScrollMessage(this.container.getWindowId()
					, "Set Amount Minus"));
		}));

		this.addButton(new FPButton(centerX + 20, offsetY + 40, FPButton.ButtonType.STONE_RIGHT, (button) ->{
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new RecipeScrollMessage(this.container.getWindowId()
					, "Set Amount Add"));
		}));
	}
}