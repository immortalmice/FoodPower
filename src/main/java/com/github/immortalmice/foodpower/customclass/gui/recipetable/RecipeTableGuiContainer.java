package com.github.immortalmice.foodpower.customclass.gui.recipetable;

import java.io.IOException;
import java.util.List;
import java.lang.Math;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.customclass.message.RecipeTableMessage;
import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.gui.ModGuiContainer;
import com.github.immortalmice.foodpower.customclass.gui.Button;
import com.github.immortalmice.foodpower.customclass.gui.recipetable.RecipeTableContainer;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

@SideOnly(Side.CLIENT)
public class RecipeTableGuiContainer extends ModGuiContainer{
	private final int BUTTON_LEFT = 0;
	private final int BUTTON_RIGHT = 1;

	private RecipeTableContainer container;

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
		float angle = 360 / currentIngredients.size();
		for(int i = 0; i <= currentIngredients.size()-1; i ++){
			Ingredient ingredient = currentIngredients.get(i);

			this.mc.getTextureManager().bindTexture(this.getSlotTexture(ingredient.getFoodType()));

			int[] slotPosInTexture = this.getSlotPosInTexture(ingredient.getFoodType());
			float[] slotPostInGui = {
				(float)(container.getCenter()[0] + container.getRadius() * Math.cos((angle * i - 90) * Math.PI / 180)),
				(float)(container.getCenter()[1] + container.getRadius() * Math.sin((angle * i - 90) * Math.PI / 180))
			};
			
			this.drawTexturedModalRect(
				offsetX + slotPostInGui[0] - 9, offsetY + slotPostInGui[1] - 9
				, slotPosInTexture[0], slotPosInTexture[1]
				, 18, 18);
		}
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		/** Render name of the pattern */
		String patternName = I18n.format("pattern." + CookingPatterns.list.get(container.getIndex()).getName() + ".name");
		this.fontRenderer.drawString(patternName, (this.xSize - this.fontRenderer.getStringWidth(patternName)) / 2, 20, 0x404040);
	}
	@Override
	public void initGui(){
		super.initGui();

		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		this.buttonList.add(new Button(BUTTON_LEFT, offsetX + 20, offsetY + 15, 10, 15, "", 38, 19));
		this.buttonList.add(new Button(BUTTON_RIGHT, offsetX + this.xSize - 30, offsetY + 15, 10, 15, "", 38, 0));	
	}
	/** Send Message To server on clicked */
	@Override
    protected void actionPerformed(GuiButton button) throws IOException{
    	if(this.inventorySlots instanceof RecipeTableContainer){
    		RecipeTableMessage message;
	    	switch(button.id){
	    		case BUTTON_LEFT:
	    			message = new RecipeTableMessage("Decrease Index", container.pos);
	    			break;
	    		case BUTTON_RIGHT:
	    			message = new RecipeTableMessage("Increase Index", container.pos);
	    			break;
	    		default:
	    			message = new RecipeTableMessage();
	    	}
	    	FoodPower.network.sendToServer(message);
    	}
    }

    private ResourceLocation getSlotTexture(FoodType foodType){
    	String path = FoodPower.MODID + ":textures/gui/container/ingredient_block.png";
    	return new ResourceLocation(path);
    }
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