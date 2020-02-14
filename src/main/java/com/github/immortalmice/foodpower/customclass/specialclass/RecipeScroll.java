package com.github.immortalmice.foodpower.customclass.specialclass;

import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.lists.other.OtherItems;

public class RecipeScroll extends ItemBase{

	public RecipeScroll(){
		super("recipe_scroll");

		this.setMaxStackSize(1);

		OtherItems.list.add(this);
	}
}