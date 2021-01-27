package com.github.immortalmice.foodpower.container.tutorialbook.page;

import java.util.function.Consumer;

import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer.Navigator;
import com.github.immortalmice.foodpower.container.util.FPButton;
import com.github.immortalmice.foodpower.container.util.FPButton.ButtonType;

import net.minecraft.client.gui.widget.Widget;

public class MainPage extends AbstractTitlePage {
	public MainPage() {
		super("tutorial_book.foodpower.main_page.title");
	}

	@Override
	public void init(Consumer<Widget> add, Navigator navigator) {
		add.accept(new FPButton(20, 80, ButtonType.SWITCH, button -> {
			navigator.tutorialPage();
		}));
		add.accept(new FPButton(60, 80, ButtonType.SWITCH, button -> {
			navigator.ingredientsPage();
		}));
		add.accept(new FPButton(100, 80, ButtonType.SWITCH, button -> {
			navigator.patternsPage();
		}));
		add.accept(new FPButton(140, 80, ButtonType.SWITCH, button -> {
			navigator.bossesPage();
		}));
	}
}
