package com.github.immortalmice.foodpower.container.tutorialbook.page;

import java.util.function.Consumer;

import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer.Navigator;
import com.github.immortalmice.foodpower.container.util.IconButton;
import com.github.immortalmice.foodpower.container.util.IconButton.ButtonType;
import com.github.immortalmice.foodpower.util.Position2D;

import net.minecraft.client.gui.widget.Widget;

public class MainPage extends AbstractTitlePage {
	public MainPage() {
		super("tutorial_book.foodpower.main_page.title");
	}

	@Override
	public void init(Consumer<Widget> add, Navigator navigator, Position2D offset) {
		add.accept(new IconButton(offset.x + 42, offset.y + 60, ButtonType.SWITCH, button -> {
			navigator.tutorialPage();
		}));
		add.accept(new IconButton(offset.x + 148, offset.y + 60, ButtonType.SWITCH, button -> {
			navigator.ingredientsPage();
		}));
		add.accept(new IconButton(offset.x + 42, offset.y + 168, ButtonType.SWITCH, button -> {
			navigator.patternsPage();
		}));
		add.accept(new IconButton(offset.x + 148, offset.y + 168, ButtonType.SWITCH, button -> {
			navigator.bossesPage();
		}));
	}
}
