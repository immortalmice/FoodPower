package com.github.immortalmice.foodpower.customclass;

import net.minecraft.item.ItemFood;

import com.github.immortalmice.foodpower.lists.FPCreativeTabs;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class Ingredient extends ItemFood{
	public Ingredient(String name, int amount, float saturation){
		/** Can wolf eat my sweetie? NO WAY!*/
		super(amount, saturation, false);

		this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(FPCreativeTabs.ingredientTab);

        /** Add to ingredient list, and regist it later */
        Ingredients.list.add(this);
	}
}