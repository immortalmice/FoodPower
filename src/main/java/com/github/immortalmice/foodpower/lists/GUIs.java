package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;

import com.github.immortalmice.foodpower.customclass.gui.GuiPack;
import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.gui.ModGuiContainer;
import com.github.immortalmice.foodpower.customclass.gui.market.MarketContainer;
import com.github.immortalmice.foodpower.customclass.gui.recipetable.RecipeTableContainer;

public class GUIs{
	public static List<GuiPack> list = new ArrayList<GuiPack>();
	public static List<ModContainer> containerLoadedList = new ArrayList<ModContainer>();

	public static GuiPack MARKET = new GuiPack(MarketContainer.class, GUIs.checkClassExist("MarketGuiContainer", "market"));
	public static GuiPack RECIPE_TABLE = new GuiPack(RecipeTableContainer.class, GUIs.checkClassExist("RecipeTableGuiContainer", "recipetable"));

	/** If ClassNotFound, It may on the serverside */
	@SuppressWarnings("unchecked")
	private static Class<ModGuiContainer> checkClassExist(String className, String folderName){
		try{
			String str = "com.github.immortalmice.foodpower.customclass.gui." + folderName + "." + className;
			return (Class<ModGuiContainer>) Class.forName(str);
		}catch(ClassNotFoundException e){
			return null;
		}
	}

	/** Get a new Container instance by id */
	public static ModContainer getContainerById(int idIn, EntityPlayer playerIn, World worldIn, BlockPos pos){
		return list.get(idIn).getContainer(playerIn, worldIn, pos);
	}
	/** Get a new GuiContainer instance by id */
	public static ModGuiContainer getGuiContainerById(int idIn, EntityPlayer playerIn, World worldIn, BlockPos pos){
		return list.get(idIn).getGuiContainer(playerIn, worldIn, pos);
	}

	public static void closeAllLoadedContainer(){
		while(!containerLoadedList.isEmpty()){
			if(containerLoadedList.get(0) != null){
				containerLoadedList.get(0).onContainerClosed();
			}else{
				containerLoadedList.remove(0);
			}
		}
	}

	public static void closePlayerContainer(EntityPlayer playerIn){
		for(int i = containerLoadedList.size()-1; i >= 0; i --){
			ModContainer el = containerLoadedList.get(i);
			if(el.getPlayer().getName() == playerIn.getName()){
				el.onContainerClosed();
			}
		}
	}
}