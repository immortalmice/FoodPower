package com.github.immortalmice.foodpower.container.tutorialbook.page;

import java.util.function.Consumer;

import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer.Navigator;
import com.github.immortalmice.foodpower.container.util.IconButton;
import com.github.immortalmice.foodpower.container.util.IconButton.ButtonType;

import net.minecraft.client.gui.widget.Widget;

public class MainPage extends AbstractTitlePage {
	public MainPage() {
		super("tutorial_book.foodpower.main_page.title");
	}

	@Override
	public void init(Consumer<Widget> add, Navigator navigator) {
		add.accept(new IconButton(20, 80, ButtonType.SWITCH, button -> {
			navigator.tutorialPage();
		}));
		add.accept(new IconButton(60, 80, ButtonType.SWITCH, button -> {
			navigator.ingredientsPage();
		}));
		add.accept(new IconButton(100, 80, ButtonType.SWITCH, button -> {
			navigator.patternsPage();
		}));
		add.accept(new IconButton(140, 80, ButtonType.SWITCH, button -> {
			navigator.bossesPage();
		}));
	}
}
