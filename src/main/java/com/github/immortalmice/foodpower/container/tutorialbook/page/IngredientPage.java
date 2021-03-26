package com.github.immortalmice.foodpower.container.tutorialbook.page;

import com.github.immortalmice.foodpower.food.Ingredient;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;

public class IngredientPage extends AbstractLayoutPage {
	private final Ingredient ingredient;
	
	public IngredientPage(Ingredient ingredientIn) {
		super(ingredientIn.asItem().getTranslationKey());
		
		this.ingredient = ingredientIn;
	}
	
	@Override
	public void render(int mouseX, int mouseY, Screen screen, FontRenderer font, ItemRenderer itemRenderer) {
		super.render(mouseX, mouseY, screen, font, itemRenderer);

		font.drawString(I18n.format("general.foodpower.food_type") + ": " + I18n.format("food_type.foodpower." + this.ingredient.getFoodType().getName()), 40, 54, 0);
		font.drawString(I18n.format("general.foodpower.flavor_type") + ": " + I18n.format("flavor_type.foodpower." + this.ingredient.getFlavorType().getName()), 40, 68, 0);
		
		int y = 88;
		for(int i = 1; i <= 3; i ++) {
			String str = I18n.format("general.foodpower.level");
			str += String.valueOf(i) + ": ";
			font.drawString(str, 40, y, 0);
			
			y += 14;
			str = I18n.format("tutorial_book.foodpower.ingredients." + ingredient.asItem().getRegistryName().getPath() + ".level" + String.valueOf(i));
			font.drawSplitString(str, 50, y, 170, 0x404040);
			
			y += font.getWordWrappedHeight(str, 160) + 4;
		}
		
		String str = I18n.format("tutorial_book.foodpower.ingredients." + ingredient.asItem().getRegistryName().getPath() + ".comment");
		font.drawString(str, (256 - font.getStringWidth(str)) / 2, 198, 0x642100);
	}
}
