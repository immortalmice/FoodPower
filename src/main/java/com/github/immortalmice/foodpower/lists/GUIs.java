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

public class GUIs{
	public static List<GuiPack> list = new ArrayList<GuiPack>();

	public static GuiPack MARKET = new GuiPack(MarketContainer.class, GUIs.checkClassExist("MarketGuiContainer", "market"));

	/** If ClassNotFound, It may on ther serverside */
	@SuppressWarnings("unchecked")
	private static Class<? extends ModGuiContainer> checkClassExist(String className, String folderName){
		try{
			String str = "com.github.immortalmice.foodpower.customclass.gui." + folderName + "." + className;
			return (Class<? extends ModGuiContainer>) Class.forName(str);
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
}