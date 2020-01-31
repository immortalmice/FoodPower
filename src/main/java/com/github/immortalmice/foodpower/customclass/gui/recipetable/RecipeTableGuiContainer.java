package com.github.immortalmice.foodpower.customclass.gui.recipetable;

import java.io.IOException;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.GuiButton;

import com.github.immortalmice.foodpower.FoodPower;
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

	public RecipeTableGuiContainer(ModContainer inventorySlotsIn){
		super(inventorySlotsIn, new int[]{176, 220});

		this.textureFileName = "recipe_table";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		/** Render name of the pattern */
		String patternName = I18n.format("pattern." + CookingPatterns.list.get(this.getIndex()).getName() + ".name");
		this.fontRenderer.drawString(patternName, (this.xSize - this.fontRenderer.getStringWidth(patternName)) / 2, 20, 0x404040);
	}
	@Override
	public void initGui(){
		super.initGui();

		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		this.buttonList.add(new Button(BUTTON_LEFT, offsetX + 20, offsetY + 15, 10, 15, "", 38, 19));
		this.buttonList.add(new Button(BUTTON_RIGHT, offsetX + this.xSize - 30, offsetY + 15, 10, 15, "", 38, 0));	
	}
	@Override
    protected void actionPerformed(GuiButton button) throws IOException{
    	if(this.inventorySlots instanceof RecipeTableContainer){
    		RecipeTableMessage message;
	    	switch(button.id){
	    		case BUTTON_LEFT:
	    			message = new RecipeTableMessage("Decrease Index", ((RecipeTableContainer)this.inventorySlots).pos);
	    			break;
	    		case BUTTON_RIGHT:
	    			message = new RecipeTableMessage("Increase Index", ((RecipeTableContainer)this.inventorySlots).pos);
	    			break;
	    		default:
	    			message = new RecipeTableMessage();
	    	}
	    	FoodPower.network.sendToServer(message);
    	}
    }

    public int getIndex(){
    	if(this.inventorySlots instanceof RecipeTableContainer){
    		return ((RecipeTableContainer)this.inventorySlots).getIndex();
    	}
    	return 0;
    }
}