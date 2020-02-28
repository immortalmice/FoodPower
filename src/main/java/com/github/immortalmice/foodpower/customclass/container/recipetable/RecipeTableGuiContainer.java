package com.github.immortalmice.foodpower.customclass.gui.recipetable;

import java.io.IOException;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.customclass.message.classes.RecipeTableMessage;
import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.gui.ModGuiContainer;
import com.github.immortalmice.foodpower.customclass.gui.Button;
import com.github.immortalmice.foodpower.customclass.gui.recipetable.RecipeTableContainer;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.FoodTypes;

@SideOnly(Side.CLIENT)
public class RecipeTableGuiContainer extends ModGuiContainer{
	private final int BUTTON_LEFT = 0;
	private final int BUTTON_RIGHT = 1;

	private RecipeTableContainer container;
	private GuiTextField textBox;

	public RecipeTableGuiContainer(ModContainer inventorySlotsIn){
		super(inventorySlotsIn, new int[]{256, 256});

		if(inventorySlotsIn instanceof RecipeTableContainer){
			this.container = (RecipeTableContainer) inventorySlotsIn;
		}
		this.textureFileName = "recipe_table";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		List<Ingredient> currentIngredients = container.getIngredients();

		/* Make A Slot Circle With N Slots */
		int[][] slotPos = container.getSlotPos();	
		this.mc.getTextureManager().bindTexture(this.getSlotTexture());

		for(int i = 0; i <= currentIngredients.size()-1; i ++){
			Ingredient ingredient = currentIngredients.get(i); 
			int[] slotPosInTexture = this.getSlotPosInTexture(ingredient.isEmpty() ? ingredient.getFoodType() : FoodTypes.NONE);
			
			this.drawTexturedModalRect(
				offsetX + slotPos[i][0] - 9, offsetY + slotPos[i][1] - 9
				, slotPosInTexture[0], slotPosInTexture[1]
				, 18, 18);
		}	
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		/* Render name of the pattern */
		String patternName = I18n.format("pattern." + CookingPatterns.list.get(container.getIndex()).getName() + ".name");
		this.fontRenderer.drawString(patternName, (this.xSize - this.fontRenderer.getStringWidth(patternName)) / 2, 20, 0x404040);
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.textBox.drawTextBox();
	}
	@Override
	public void initGui(){
		super.initGui();

		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		this.buttonList.add(new Button(BUTTON_LEFT, offsetX + 20, offsetY + 15, 10, 15, "", 38, 19));
		this.buttonList.add(new Button(BUTTON_RIGHT, offsetX + this.xSize - 30, offsetY + 15, 10, 15, "", 38, 0));

		this.textBox = new GuiTextField(2, this.fontRenderer, offsetX + 130, offsetY + 120, 70, 15);
		this.textBox.setText(this.container.getInputText());
		//this.textBox.setEnableBackgroundDrawing(false);
	}
	/* Send Message To server on clicked */
	@Override
    protected void actionPerformed(GuiButton button) throws IOException{
    	if(this.inventorySlots instanceof RecipeTableContainer){
    		RecipeTableMessage message;
	    	switch(button.id){
	    		case BUTTON_LEFT:
	    			message = new RecipeTableMessage("Set Index", "Decrease", container.pos);
	    			break;
	    		case BUTTON_RIGHT:
	    			message = new RecipeTableMessage("Set Index", "Increase", container.pos);
	    			break;
	    		default:
	    			message = new RecipeTableMessage();
	    	}
	    	FoodPower.network.sendToServer(message);
    	}
    }
    /* Click on Text Box to focus it, otherwise cancel focus on it */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
    	int textBoxX = this.textBox.x, textBoxY = this.textBox.y;
    	int textBoxWidth = this.textBox.width, textBoxHeigth = this.textBox.height;
    	if(mouseX >= textBoxX && mouseX < textBoxX + textBoxWidth && mouseY >= textBoxY && mouseY < textBoxY + textBoxHeigth){
    		this.textBox.setFocused(true);
    	}else{
    		this.textBox.setFocused(false);
    	}
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    /* Set Full name typed in To Server, Server will refresh recipeScroll with name */
    @Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException{
		if(this.textBox.textboxKeyTyped(typedChar, keyCode)){
			FoodPower.network.sendToServer(new RecipeTableMessage("Set InputText", this.textBox.getText(), container.pos));
		}else{
			super.keyTyped(typedChar, keyCode);
		}
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