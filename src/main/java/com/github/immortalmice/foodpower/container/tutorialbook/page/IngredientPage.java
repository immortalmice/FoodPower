package com.github.immortalmice.foodpower.container.tutorialbook.page;

import java.util.function.Consumer;

import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer.Navigator;
import com.github.immortalmice.foodpower.container.util.IconButton;
import com.github.immortalmice.foodpower.container.util.IconButton.ButtonType;
import com.github.immortalmice.foodpower.food.Ingredient;
import com.github.immortalmice.foodpower.util.Position2D;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;

public class IngredientPage extends AbstractLayoutPage {
	private final Ingredient ingredient;
	private int currentLevel = 1;
	
	public IngredientPage(Ingredient ingredientIn) {
		super(ingredientIn.asItem().getTranslationKey());
		
		this.ingredient = ingredientIn;
	}
	
	@Override
	public void init(Consumer<Widget> add, Navigator navigator, Position2D offset) {
		super.init(add, navigator, offset);
		add.accept(new IconButton(offset.x + 30, offset.y + 120, ButtonType.IRON_LEFT, button -> {
			this.currentLevel = Math.max(this.currentLevel - 1, 1);
		}));
		add.accept(new IconButton(offset.x + 230, offset.y + 120, ButtonType.IRON_RIGHT, button -> {
			this.currentLevel = Math.min(this.currentLevel + 1, 3);
		}));
	}
	
	@Override
	public void render(int mouseX, int mouseY, Screen screen, FontRenderer font, ItemRenderer itemRenderer) {
		super.render(mouseX, mouseY, screen, font, itemRenderer);

		font.drawString(I18n.format("general.foodpower.food_type") + ": " + I18n.format("food_type.foodpower." + this.ingredient.getFoodType().getName()), 40, 38, 0);
		font.drawString(I18n.format("general.foodpower.flavor_type") + ": " + I18n.format("flavor_type.foodpower." + this.ingredient.getFlavorType().getName()), 40, 50, 0);
		
		String level = I18n.format("general.foodpower.level") + String.valueOf(this.currentLevel) + ": ";
		String description = I18n.format("tutorial_book.foodpower.ingredients." + ingredient.asItem().getRegistryName().getPath() + ".level" + String.valueOf(this.currentLevel));
		
		int totalHeight = font.getWordWrappedHeight(description, 160) + 22;
		int y = 80 + (110 - totalHeight) / 2;
		font.drawString(level, 50, y, 0x002288);
		font.drawSplitString(description, 60, y + 14, 160, 0x404040);
		
		String comment = I18n.format("tutorial_book.foodpower.ingredients." + ingredient.asItem().getRegistryName().getPath() + ".comment");
		font.drawSplitString(comment, (256 - Math.min(font.getStringWidth(comment), 200)) / 2, 184, 200, 0x642100);
	}
}
