package com.github.immortalmice.foodpower.container.tutorialbook.page;

import java.util.function.Consumer;

import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer.Navigator;
import com.github.immortalmice.foodpower.util.Position2D;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.ItemRenderer;

public interface IPage {
	default void render(int mouseX, int mouseY, Screen screen, FontRenderer font, ItemRenderer itemRenderer) {}
	default void init(Consumer<Widget> add, Navigator navigator, Position2D offset) {}
}
