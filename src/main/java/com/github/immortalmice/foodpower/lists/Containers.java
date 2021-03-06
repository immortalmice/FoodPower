package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ObjectHolder;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.container.ContainerPack;
import com.github.immortalmice.foodpower.container.chefcard.ChefCardContainer;
import com.github.immortalmice.foodpower.container.chefcard.ChefCardScreen;
import com.github.immortalmice.foodpower.container.kitchenappliance.KitchenApplianceContainer;
import com.github.immortalmice.foodpower.container.kitchenappliance.KitchenApplianceScreen;
import com.github.immortalmice.foodpower.container.market.MarketContainer;
import com.github.immortalmice.foodpower.container.market.MarketScreen;
import com.github.immortalmice.foodpower.container.recipescroll.RecipeScrollContainer;
import com.github.immortalmice.foodpower.container.recipescroll.RecipeScrollScreen;
import com.github.immortalmice.foodpower.container.recipetable.RecipeTableContainer;
import com.github.immortalmice.foodpower.container.recipetable.RecipeTableScreen;
import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer;
import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookScreen;

public class Containers{
	public static final DeferredRegister<ContainerType<?>> REGISTER = new DeferredRegister<ContainerType<?>>(ForgeRegistries.CONTAINERS, FoodPower.MODID);
	public static final List<ContainerPack<?>> list = new ArrayList<ContainerPack<?>>();

	public static final ContainerPack<MarketContainer> MARKET_PACK = new ContainerPack<MarketContainer>("market", MarketContainer::new);
	public static final ContainerPack<RecipeTableContainer> RECIPE_TABLE_PACK = new ContainerPack<RecipeTableContainer>("recipe_table", RecipeTableContainer::new);
	public static final ContainerPack<RecipeScrollContainer> RECIPE_SCROLL_PACK = new ContainerPack<RecipeScrollContainer>("recipe_scroll", RecipeScrollContainer::new);
	public static final ContainerPack<KitchenApplianceContainer> KITCHEN_APPLIANCE_PACK = new ContainerPack<KitchenApplianceContainer>("kitchen_appliance", KitchenApplianceContainer::new);
	public static final ContainerPack<ChefCardContainer> CHEF_CARD_PACK = new ContainerPack<>("chef_card", ChefCardContainer::new);
	public static final ContainerPack<TutorialBookContainer> TUTORIAL_BOOK_PACK = new ContainerPack<>("tutorial_book", TutorialBookContainer::new);

	@ObjectHolder(FoodPower.MODID)
	public static class ContainerTypes{
		public static final ContainerType<MarketContainer> MARKET = null;
		public static final ContainerType<RecipeTableContainer> RECIPE_TABLE = null;
		public static final ContainerType<RecipeScrollContainer> RECIPE_SCROLL = null;
		public static final ContainerType<KitchenApplianceContainer> KITCHEN_APPLIANCE = null;
		public static final ContainerType<ChefCardContainer> CHEF_CARD = null;
		public static final ContainerType<TutorialBookContainer> TUTORIAL_BOOK = null;
	}

	public static void registAllScreen(){
		ScreenManager.registerFactory(Containers.ContainerTypes.MARKET, MarketScreen::new);
		ScreenManager.registerFactory(Containers.ContainerTypes.RECIPE_TABLE, RecipeTableScreen::new);
		ScreenManager.registerFactory(Containers.ContainerTypes.RECIPE_SCROLL, RecipeScrollScreen::new);
		ScreenManager.registerFactory(Containers.ContainerTypes.KITCHEN_APPLIANCE, KitchenApplianceScreen::new);
		ScreenManager.registerFactory(Containers.ContainerTypes.CHEF_CARD, ChefCardScreen::new);
		ScreenManager.registerFactory(Containers.ContainerTypes.TUTORIAL_BOOK, TutorialBookScreen::new);
	}
}