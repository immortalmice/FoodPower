package com.github.immortalmice.foodpower.customclass.gui.market;

import java.io.IOException;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.tileentity.TileEntity;

import com.github.immortalmice.foodpower.customclass.gui.ModGuiContainer;
import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.gui.Button;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.MarketTileEntity;

@SideOnly(Side.CLIENT)
public class MarketGuiContainer extends ModGuiContainer{
	private final int BUTTON_LEFT = 0;
	private final int BUTTON_RIGHT = 1;

	public MarketGuiContainer(ModContainer inventorySlotsIn){
		super(inventorySlotsIn);

		this.textureFileName = "market";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		if(this.inventorySlots instanceof MarketContainer){
			ItemStack item = new ItemStack(((MarketContainer)inventorySlots).getItem());
			this.itemRender.renderItemAndEffectIntoGUI(item, 35, 20);
		}
	}
	@Override
	public void initGui(){
		super.initGui();

		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
		this.buttonList.add(new Button(BUTTON_LEFT, offsetX + 20, offsetY + 20, 10, 15, "", 38, 19));
		this.buttonList.add(new Button(BUTTON_RIGHT, offsetX + 60, offsetY + 20, 10, 15, "", 38, 0));
	}
	@Override
    protected void actionPerformed(GuiButton button) throws IOException{
    	if(this.inventorySlots instanceof MarketContainer){
    		MarketContainer container = (MarketContainer)inventorySlots;
    		TileEntity tile = container.world.getTileEntity(container.pos);
    		if(tile instanceof MarketTileEntity){
    			switch(button.id){
		    		case BUTTON_LEFT:
		    			((MarketTileEntity)tile).decreaseIndex();
		    			break;
		    		case BUTTON_RIGHT:
		    			((MarketTileEntity)tile).increaseIndex();
		    			break;
		    	}
    		}
    		
	    	container.emeraldSlot.onSlotChanged();
    	}
    }
}