package com.github.immortalmice.foodpower.customclass.container.classes.recipetable;

import java.util.List;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.container.util.FPButton;
import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.customclass.message.classes.RecipeTableMessage;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.FoodTypes;

@OnlyIn(Dist.CLIENT)
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
		List<Ingredient> currentIngredients = container.getIngredients();

		/* Make A Slot Circle With N Slots */
		int[][] slotPos = container.getSlotPos();	
		this.minecraft.getTextureManager().bindTexture(this.getSlotTexture());

		for(int i = 0; i <= currentIngredients.size()-1; i ++){
			Ingredient ingredient = currentIngredients.get(i); 
			int[] slotPosInTexture = this.getSlotPosInTexture(ingredient.isEmpty() ? ingredient.getFoodType() : FoodTypes.NONE);
			
			this.blit(
				offsetX + slotPos[i][0] - 9, offsetY + slotPos[i][1] - 9
				, slotPosInTexture[0], slotPosInTexture[1]
				, 18, 18);
		}	
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
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.textBox.renderButton(mouseX, mouseY, partialTicks);
	}
	@Override
	public void init(){
		super.init();

		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		this.addButton(new FPButton(offsetX + 20, offsetY + 15, 10, 15, 38, 19, "", (button) ->{
			/* Send Message To server on clicked */
			FoodPower.NETWORK.sendToServer(
				new RecipeTableMessage(this.container.getWindowId()
					, "Set Index"
					, "Decrease"));
		}));
		this.addButton(new FPButton(offsetX + this.xSize - 30, offsetY + 15, 10, 15, 38, 0, "", (button) ->{
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
		this.textBox.func_212954_a((str) -> 
			FoodPower.NETWORK.sendToServer(
				new RecipeTableMessage(this.container.getWindowId()
					, "Set InputText"
					, this.textBox.getText())
			));
		this.func_212928_a(this.textBox);
		//this.textBox.setEnableBackgroundDrawing(false);
	}

	/* Set Full name typed in To Server, Server will refresh recipeScroll with the name */
    @Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_){
		if(p_keyPressed_1_ == 256) {
			return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		}
		if(this.textBox.isFocused()){
			return this.textBox.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		}
		return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}

    private ResourceLocation getSlotTexture(){
    	String path = FoodPower.MODID + ":textures/gui/container/ingredient_block.png";
    	return new ResourceLocation(path);
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