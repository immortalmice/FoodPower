package com.github.immortalmice.foodpower.customclass.specialclass;

import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.lists.other.OtherItems;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;

public class RecipeScroll extends ItemBase{
	private int count = 0;

	private CookingPattern pattern;
	private int id;
	private String recipeName;

	public RecipeScroll(String nameIn, CookingPattern patternIn, ItemStack[] ingredientIn){
		super("recipe_scroll");

		this.setMaxStackSize(1);
		
		count ++;
		this.pattern = patternIn;
		this.recipeName = nameIn;
		this.id = count;
	}

	public RecipeScroll(){
		this("Unknown Recipe", new CookingPattern(), new ItemStack[0]);

		OtherItems.list.add(this);
	}

	public String getItemStackDisplayName(ItemStack stack){
		return this.recipeName;
	}

	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.recipeName;
	}
}