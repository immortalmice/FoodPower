package com.github.immortalmice.foodpower.container.tutorialbook.page;

import java.util.function.Consumer;

import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer.Navigator;
import com.github.immortalmice.foodpower.container.util.TextButton;
import com.github.immortalmice.foodpower.util.Position2D;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;

public class TutorialsPage extends AbstractHomeablePage {
	public TutorialsPage() {
		super("tutorial_book.foodpower.tutorial_main_page.title");
	}
	
	public void init(Consumer<Widget> add, Navigator navigator, Position2D offset) {
		super.init(add, navigator, offset);
		
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontRenderer = minecraft.fontRenderer;
		
		String tutorialsStr1 = I18n.format("tutorial_book.foodpower.tutorials.how_to_be_a_good_chef");
		add.accept(new TextButton(offset.x + (256 - fontRenderer.getStringWidth(tutorialsStr1)) / 2, offset.y + 60, fontRenderer, tutorialsStr1, button -> {
			navigator.tutorialPage(1);
		}));
		
		String tutorialsStr2 = I18n.format("tutorial_book.foodpower.tutorials.how_to_make_meals");
		add.accept(new TextButton(offset.x + (256 - fontRenderer.getStringWidth(tutorialsStr2)) / 2, offset.y + 100, fontRenderer, tutorialsStr2, button -> {
			navigator.tutorialPage(2);
		}));
		
		String tutorialsStr3 = I18n.format("tutorial_book.foodpower.tutorials.your_experience");
		add.accept(new TextButton(offset.x + (256 - fontRenderer.getStringWidth(tutorialsStr3)) / 2, offset.y + 140, fontRenderer, tutorialsStr3, button -> {
			navigator.tutorialPage(3);
		}));
		
		String tutorialsStr4 = I18n.format("tutorial_book.foodpower.tutorials.how_to_summon_boss");
		add.accept(new TextButton(offset.x + (256 - fontRenderer.getStringWidth(tutorialsStr4)) / 2, offset.y + 180, fontRenderer, tutorialsStr4, button -> {
			navigator.tutorialPage(4);
		}));
	}
}
