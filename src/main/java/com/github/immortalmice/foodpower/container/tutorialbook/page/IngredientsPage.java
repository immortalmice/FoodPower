package com.github.immortalmice.foodpower.container.tutorialbook.page;

import java.util.function.Consumer;

import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer.Navigator;
import com.github.immortalmice.foodpower.container.util.ItemButton;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.util.Position2D;

import net.minecraft.client.gui.widget.Widget;

public class IngredientsPage extends AbstractLayoutPage {
	public IngredientsPage() {
		super("tutorial_book.foodpower.ingredient_main_page.title");
	}
	
	@Override
	public void init(Consumer<Widget> add, Navigator navigator, Position2D offset) {
		super.init(add, navigator, offset);
		
		int amount = Ingredients.list.size();
		final int ITEM_PER_ROW = 10;
		for(int i = 0; i <= 10; i ++) {
			for(int j = 0; j <= ITEM_PER_ROW - 1; j ++) {
				int current = i * ITEM_PER_ROW + j;
				if(current >= amount) return;
				add.accept(new ItemButton(offset.x + 20 * j + 30, offset.y + 20 * i + 40, Ingredients.list.get(current).asItem(), button -> {
					navigator.ingredientsPage(current + 1);
				}));
			}
		}
	}
}
