package com.github.immortalmice.foodpower.lists.other;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.immortalmice.foodpower.customclass.specialclass.DirtyFood;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;

/** All the other items in mod need to be registed will list below */
public class OtherItems{
	public static final List<Item> list = new ArrayList<Item>();

	public static final DirtyFood DIRTY_FOOD = new DirtyFood();
	public static final RecipeScroll RECIPE_SCROLL = new RecipeScroll();

	@SideOnly(Side.CLIENT)
	public static void registModel(){
		ModelLoader.setCustomModelResourceLocation(DIRTY_FOOD, 0
			, new ModelResourceLocation(DIRTY_FOOD.getRegistryName(), "inventory"));
	}
}