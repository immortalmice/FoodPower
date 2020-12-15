package com.github.immortalmice.foodpower.container.recipetable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.container.util.FPButton;
import com.github.immortalmice.foodpower.cooking.ICookingElement;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.FoodTypes;
import com.github.immortalmice.foodpower.message.RecipeTableMessage;
import com.github.immortalmice.foodpower.types.FoodType;
import com.github.immortalmice.foodpower.util.TooltipUtil;
import com.github.immortalmice.foodpower.util.PosProvider.RecipeTableSlotPos;
import com.github.immortalmice.foodpower.util.Position2D;

public class RecipeTableScreen extends ScreenBase<RecipeTableContainer>{
	private TextFieldWidget textBox;

	public RecipeTableScreen(RecipeTableContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn){
		super(containerIn, inventoryIn, textIn);

		this.textureFileName = "recipe_table";
		this.xSize = 256;
		this.ySize = 256;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		List<ICookingElement> currentElements = container.getCurrentRootElements();

		/* Make A Slot Circle With N Slots */
		List<Position2D> slotPos = RecipeTableSlotPos.provide(currentElements.size()).stream().map((pos) -> pos.translate(-1)).collect(Collectors.toList());	
		this.minecraft.getTextureManager().bindTexture(ScreenBase.UI_KIT_TEXTURE);

		for(int i = 0; i <= currentElements.size()-1; i ++){
			ICookingElement element = currentElements.get(i); 
			int[] slotPosInTexture = this.getSlotPosInTexture(element instanceof FoodType ? (FoodType) element : FoodTypes.NONE);
			
			this.blit(
				offsetX + slotPos.get(i).x, offsetY + slotPos.get(i).y
				, slotPosInTexture[0], slotPosInTexture[1]
				, 18, 18);
		}
		this.textBox.renderButton(mouseX, mouseY, partialTicks);
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		/* Render name of the pattern */
		String patternName = I18n.format("pattern.foodpower." + CookingPatterns.list.get(this.container.getIndex()).getName());
		this.font.drawString(patternName, (this.xSize - this.font.getStringWidth(patternName)) / 2, 20, 0x404040);
	}
	@Override
	public void render(int mouseX, int mouseY, float partialTicks){
		super.render(mouseX, mouseY, partialTicks);

		if(this.hoveredSlot instanceof RecipeTableSlot){
			ICookingElement element = ((RecipeTableSlot) this.hoveredSlot).getSlotElement();
			if(element instanceof FoodType && !this.hoveredSlot.getHasStack()){
				ArrayList<String> tooltipStr = new ArrayList<String>();

				tooltipStr.add(I18n.format("general.foodpower.food_type") 
					+ " : " 
					+ I18n.format("food_type.foodpower." + ((FoodType)element).getName()));
				this.renderTooltip(tooltipStr, mouseX, mouseY);
			}
		}
	}
	
	@Override
	protected void renderHoveredToolTip(int mouseX, int mouseY){
		if(this.hoveredSlot instanceof RecipeTableSlot && this.hoveredSlot.getHasStack()){
			ItemStack stack = this.hoveredSlot.getStack();
			FontRenderer font = stack.getItem().getFontRenderer(stack);
			List<String> text = this.getTooltipFromItem(stack);
			
			text.add("");
			text.add(TooltipUtil.translate("message.foodpower.tooltip_recipe_table_operations"));
			this.renderTooltip(text, mouseX, mouseY, (font == null ? this.font : font));
			return;
		}
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	public void init(){
		super.init();

		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		this.addButton(new FPButton(offsetX + 20, offsetY + 15, FPButton.ButtonType.WOOD_LEFT, (button) ->{
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new RecipeTableMessage(this.container.getWindowId()
					, "Set Index"
					, "Decrease"));
		}));
		this.addButton(new FPButton(offsetX + this.xSize - 30, offsetY + 15, FPButton.ButtonType.WOOD_RIGHT, (button) ->{
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new RecipeTableMessage(this.container.getWindowId()
					, "Set Index"
					, "Increase"));
		}));

		this.textBox = new TextFieldWidget(this.font
			, offsetX + 130, offsetY + 120
			, 70, 15, I18n.format("general.foodpower.recipe_name"));
		this.textBox.setCanLoseFocus(true);
		this.textBox.changeFocus(true);
		this.children.add(this.textBox);
		this.textBox.setResponder((str) -> onTextChanged());
		this.addButton(this.textBox);
		//this.textBox.setEnableBackgroundDrawing(false);

		/* If player is creative, show this button, server will valid again when message recieved */
		if(this.container.isPlayerCreative()){
			String giveFoodString =  I18n.format("message.foodpower.give_food");
			int strWidth = this.font.getStringWidth(giveFoodString);

			this.addButton(new Button(offsetX - strWidth - 20, offsetY + 20
				, strWidth + 20, 10
				, giveFoodString, (button) ->{
					/* Send Message To server on clicked */
					FoodPower.NETWORK.sendToServer(
						new RecipeTableMessage(this.container.getWindowId()
							, "Try Give Meal"
							, ""));
			}));
		}
	}

	/* Set Full name typed in To Server, Server will refresh recipeScroll with the name */
    @Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_){
		if(p_keyPressed_1_ == 256) {
			return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		}
		if(this.textBox.isFocused()){
			boolean returnBool = this.textBox.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
			return returnBool;
		}
		return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}
	
	private void onTextChanged(){
		this.getContainer().setInputText(this.textBox.getText());
		FoodPower.NETWORK.sendToServer(
			new RecipeTableMessage(this.container.getWindowId()
				, "Set InputText"
				, this.textBox.getText())
		);
	}

    /* Used in getting the position of texture with the food type */
    private int[] getSlotPosInTexture(FoodType foodType){
    	String name = foodType.getName();

    	switch(name){
    		case "fruit":
    			return new int[]{20, 20};
    		case "meat":
    			return new int[]{20, 0};
    		case "vegetable":
    			return new int[]{0, 0};
    		case "sweet":
    			return new int[]{0, 20};
    		case "seasoning":
    			return new int[]{0, 40};
    	}
    	return new int[]{20, 40};
    }
}