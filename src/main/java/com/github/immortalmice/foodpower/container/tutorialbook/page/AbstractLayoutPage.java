package com.github.immortalmice.foodpower.container.tutorialbook.page;

import java.util.function.Consumer;

import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer.Navigator;
import com.github.immortalmice.foodpower.container.util.IconButton;
import com.github.immortalmice.foodpower.container.util.IconButton.ButtonType;
import com.github.immortalmice.foodpower.util.Position2D;

import net.minecraft.client.gui.widget.Widget;

public abstract class AbstractLayoutPage extends AbstractHomeablePage {
	public AbstractLayoutPage(String titleKeyIn) {
		super(titleKeyIn);
	}
	
	@Override
	public void init(Consumer<Widget> add, Navigator navigator, Position2D offset) {
		super.init(add, navigator, offset);
		add.accept(new IconButton(offset.x + 105, offset.y + 220, ButtonType.WOOD_LEFT, button -> {
			navigator.previousPage();
		}));
		add.accept(new IconButton(offset.x + 144, offset.y + 220, ButtonType.WOOD_RIGHT, button -> {
			navigator.nextPage();
		}));
	}
}
