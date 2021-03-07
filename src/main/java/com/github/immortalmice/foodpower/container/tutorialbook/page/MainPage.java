package com.github.immortalmice.foodpower.container.tutorialbook.page;

import java.util.function.Consumer;

import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer.Navigator;
import com.github.immortalmice.foodpower.container.util.IconButton;
import com.github.immortalmice.foodpower.container.util.IconButton.ButtonType;
import com.github.immortalmice.foodpower.util.Position2D;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;

public class MainPage extends AbstractTitlePage {
	public MainPage() {
		super("tutorial_book.foodpower.main_page.title");
	}
	
	@Override
	public void render(int mouseX, int mouseY, Screen screen, FontRenderer font, ItemRenderer itemRenderer) {
		super.render(mouseX, mouseY, screen, font, itemRenderer);
		
		String tutorial = I18n.format("tutorial_book.foodpower.main_page.tutorial");
		font.drawString(tutorial, (75 - font.getStringWidth(tutorial) / 2), 128, 0x404040);
		String ingredient = I18n.format("tutorial_book.foodpower.main_page.ingredient");
		font.drawString(ingredient, (184 - font.getStringWidth(ingredient) / 2), 128, 0x404040);
		String meal = I18n.format("tutorial_book.foodpower.main_page.meal");
		font.drawString(meal, (75 - font.getStringWidth(meal) / 2), 220, 0x404040);
		String boss = I18n.format("tutorial_book.foodpower.main_page.boss");
		font.drawString(boss, (184 - font.getStringWidth(boss) / 2), 220, 0x404040);
	}

	@Override
	public void init(Consumer<Widget> add, Navigator navigator, Position2D offset) {
		add.accept(new IconButton(offset.x + 42, offset.y + 60, ButtonType.TUTORIAL, button -> {
			navigator.tutorialPage();
		}));
		add.accept(new IconButton(offset.x + 148, offset.y + 60, ButtonType.INGREDIENT, button -> {
			navigator.ingredientsPage();
		}));
		add.accept(new IconButton(offset.x + 42, offset.y + 148, ButtonType.MEAL, button -> {
			navigator.patternsPage();
		}));
		add.accept(new IconButton(offset.x + 148, offset.y + 148, ButtonType.BOSS, button -> {
			navigator.bossesPage();
		}));
	}
}
