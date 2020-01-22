package com.github.immortalmice.foodpower.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.lists.GUIs;

public class GuiHandler implements IGuiHandler{
	public GuiHandler(){
		NetworkRegistry.INSTANCE.registerGuiHandler(FoodPower.instance, this);
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		return GUIs.getContainerById(id, player);
	}
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		return GUIs.getGuiContainerById(id, player);
	}
}