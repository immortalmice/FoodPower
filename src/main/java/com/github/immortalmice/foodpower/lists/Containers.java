package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.container.ContainerPack;
import com.github.immortalmice.foodpower.customclass.container.classes.market.MarketContainer;
import com.github.immortalmice.foodpower.customclass.container.classes.market.MarketScreen;
import com.github.immortalmice.foodpower.customclass.container.classes.recipescroll.RecipeScrollContainer;
import com.github.immortalmice.foodpower.customclass.container.classes.recipescroll.RecipeScrollScreen;
import com.github.immortalmice.foodpower.customclass.container.classes.recipetable.RecipeTableContainer;
import com.github.immortalmice.foodpower.customclass.container.classes.recipetable.RecipeTableScreen;

public class Containers{
	public static final DeferredRegister<ContainerType<?>> REGISTER = new DeferredRegister<ContainerType<?>>(ForgeRegistries.CONTAINERS, FoodPower.MODID);
	public static final List<ContainerPack<?>> list = new ArrayList<ContainerPack<?>>();

	public static final ContainerPack<MarketContainer> MARKET_PACK = new ContainerPack<MarketContainer>("market", MarketContainer::new);
	public static final ContainerPack<RecipeTableContainer> RECIPE_TABLE_PACK = new ContainerPack<RecipeTableContainer>("recipe_table", RecipeTableContainer::new);
	public static final ContainerPack<RecipeScrollContainer> RECIPE_SCROLL_PACK = new ContainerPack<RecipeScrollContainer>("recipe_scroll", RecipeScrollContainer::new);

	@ObjectHolder("foodpower")
	public static class ContainerTypes{
		public static final ContainerType<MarketContainer> MARKET = null;
		public static final ContainerType<RecipeTableContainer> RECIPE_TABLE = null;
		public static final ContainerType<RecipeScrollContainer> RECIPE_SCROLL = null;
	}

	public static void registAllScreen(){
		ScreenManager.registerFactory(Containers.ContainerTypes.MARKET, MarketScreen::new);
		ScreenManager.registerFactory(Containers.ContainerTypes.RECIPE_TABLE, RecipeTableScreen::new);
		ScreenManager.registerFactory(Containers.ContainerTypes.RECIPE_SCROLL, RecipeScrollScreen::new);
	}
}