package com.github.immortalmice.foodpower.container.tutorialbook.page;

import java.util.function.Consumer;

import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer.Navigator;
import com.github.immortalmice.foodpower.container.util.IconButton;
import com.github.immortalmice.foodpower.container.util.IconButton.ButtonType;
import com.github.immortalmice.foodpower.util.Position2D;

import net.minecraft.client.gui.widget.Widget;

public class AbstractHomeablePage extends AbstractTitlePage {
	public AbstractHomeablePage(String titleKeyIn) {
		super(titleKeyIn);
	}
	
	@Override
	public void init(Consumer<Widget> add, Navigator navigator, Position2D offset) {
		add.accept(new IconButton(offset.x + 120, offset.y + 220, ButtonType.SWITCH, button -> {
			navigator.homePage();
		}));
	}
}
