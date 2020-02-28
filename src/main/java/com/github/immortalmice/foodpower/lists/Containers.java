package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.customclass.container.ContainerPack;
import com.github.immortalmice.foodpower.customclass.container.market.MarketContainer;
import com.github.immortalmice.foodpower.customclass.container.recipetable.RecipeTableContainer;

public class Containers{
	public static final DeferredRegister<ContainerType<?>> REGISTER = new DeferredRegister<ContainerType<?>>(ForgeRegistries.CONTAINERS, FoodPower.MODID);
	public static final List<ContainerPack<?>> list = new ArrayList<ContainerPack<?>>();

	public static final ContainerPack<ContainerBase> BASE = new ContainerPack<ContainerBase>("base", ContainerBase::new);
	public static final ContainerPack<MarketContainer> MARKET = new ContainerPack<MarketContainer>("market", MarketContainer::new);
	public static final ContainerPack<RecipeTableContainer> RECIPE_TABLE = new ContainerPack<RecipeTableContainer>("recipe_table", RecipeTableContainer::new);
	//public static final ContainerPack<> RECIPE_SCROLL = new ContainerPack();

	@OnlyIn(Dist.CLIENT)
	public static void registAllScreen(){
		for(ContainerPack<?> container : list){
			container.registScreen();
		}
	}
}