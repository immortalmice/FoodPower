package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.immortalmice.foodpower.customclass.gui.GuiPack;
import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.gui.ModGuiContainer;
import com.github.immortalmice.foodpower.customclass.gui.market.MarketContainer;
import com.github.immortalmice.foodpower.customclass.gui.market.MarketGuiContainer;

public class GUIs{
	public static List<GuiPack> list = new ArrayList<GuiPack>();

	public static GuiPack MARKET = new GuiPack(MarketContainer.class);

	/** Use static statement to add GuiPacks to list and get id without hard code */
	static{
		try{
			GUIs.setModGuiContainer();
		}catch(NoSuchMethodError e){
			/** Is on server, skip setModGuiContainer */
		}
		/** Add to list */
		GUIs.add(GUIs.MARKET);
	}
	/** Add to list and assign id without hard code */
	private static void add(GuiPack pack){
		pack.id = list.size();
		list.add(pack);
	}
	@SideOnly(Side.CLIENT)
	private static void setModGuiContainer(){
		MARKET.setModGuiContainer(MarketGuiContainer.class);
	}

	/** Get a new Container instance by id */
	public static ModContainer getContainerById(int idIn, EntityPlayer player){
		return list.get(idIn).getContainer(player);
	}
	/** Get a new GuiContainer instance by id */
	public static ModGuiContainer getGuiContainerById(int idIn, EntityPlayer player){
		return list.get(idIn).getGuiContainer(player);
	}
}