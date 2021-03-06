package com.github.immortalmice.foodpower.container.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemButton extends Button {
	private final ItemStack stack;
	
	public ItemButton(int x, int y, Item item, IPressable onPress) {
		this(x, y, new ItemStack(item), onPress);
	}
	
	public ItemButton(int x, int y, ItemStack stackIn, IPressable onPress) {
		super(x, y, 18, 18, "", onPress);
		
		this.stack = stackIn;
	}
	
	@Override
	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
		if(this.visible){
			Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(this.stack, this.x, this.y);
		}
	}
}
