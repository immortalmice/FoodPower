package com.github.immortalmice.foodpower.customclass.container.classes.recipescroll;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.customclass.container.classes.recipescroll.RecipeScrollContainer;
import com.github.immortalmice.foodpower.customclass.container.util.FPButton;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe.ItemStackRequest;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe.StepRequest;
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
		this.font.drawString(scrollName, (this.xSize - this.font.getStringWidth(scrollName)) / 2, 20, 0x404040);

		List<String> stepStrings = this.getStepStrings();
		List<Integer> heights = stepStrings.stream().map((str) -> this.font.getWordWrappedHeight(str, 180)).collect(Collectors.toList());
		
		int gap = Math.max((170 - heights.stream().reduce(0, Integer::sum)) / (stepStrings.size() + 1), 3);
		int cursor = 36 + gap;
		
		for(int i = 0; i <= stepStrings.size()-1; i ++){
			this.font.drawSplitString(stepStrings.get(i), 38, cursor, 180, 0x404040);
			cursor += gap + heights.get(i);
		}

		String amount = Integer.toString(this.getContainer().getAmount());
		this.font.drawString(amount, 70 - this.font.getStringWidth(amount) / 2, 215, 0x00AA00);
		
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks){
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
	}

	@Override
	public void init(){
		super.init();

		FPButton.ButtonType buttonTypeLeft, buttonTypeRight;
		switch(this.container.getRarity()){
			case 1:
				buttonTypeLeft = FPButton.ButtonType.IRON_LEFT;
				buttonTypeRight = FPButton.ButtonType.IRON_RIGHT;
				break;
			case 2:
				buttonTypeLeft = FPButton.ButtonType.GOLD_LEFT;
				buttonTypeRight = FPButton.ButtonType.GOLD_RIGHT;
				break;
			case 3:
				buttonTypeLeft = FPButton.ButtonType.DIAMOND_LEFT;
				buttonTypeRight = FPButton.ButtonType.DIAMOND_RIGHT;
				break;
			case 0:
			default:
				buttonTypeLeft = FPButton.ButtonType.WOOD_LEFT;
				buttonTypeRight = FPButton.ButtonType.WOOD_RIGHT;
				break;
		}

		int offsetX = (this.width - this.xSize) / 2;
		int offsetY = (this.height - this.ySize) / 2;
		this.addButton(new FPButton(offsetX + 40, offsetY + 210, buttonTypeLeft, (button) ->{
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new RecipeScrollMessage(this.container.getWindowId()
					, "Set Amount Minus"));
		}));

		this.addButton(new FPButton(offsetX + 90, offsetY + 210, buttonTypeRight, (button) ->{
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new RecipeScrollMessage(this.container.getWindowId()
					, "Set Amount Add"));
		}));
	}

	private List<String> getStepStrings(){
		List<StepRequest> stepRequests = this.container.getStepReqests();
		List<String> returnStrings = new ArrayList<>();
		String preTranslationKey = "recipe_scroll.foodpower." + this.container.getPatternName() + ".step";

		for(int i = 0; i <= stepRequests.size()-1; i ++){
			StepRequest stepRequest = stepRequests.get(i);
			List<ItemStackRequest> requires = stepRequest.getRequires();

			List<Object> args = new ArrayList<>();
			requires.forEach((require) -> {
				args.add(require.getAmount());
				args.add(I18n.format(require.getItem().getTranslationKey()));
			});

			if(i == stepRequests.size()-1){
				args.add(this.container.getAmount());
			}

			returnStrings.add(I18n.format(preTranslationKey + String.valueOf(i), args.toArray()));
		}

		return returnStrings;
	}
}