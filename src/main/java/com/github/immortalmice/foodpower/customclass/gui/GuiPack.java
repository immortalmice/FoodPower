package com.github.immortalmice.foodpower.customclass.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.gui.ModGuiContainer;

public class GuiPack{
	private Class<? extends ModContainer> mc;
	@SideOnly(Side.CLIENT)
		private Class<? extends ModGuiContainer> mgc;
	private Class<?>[] mcArg = {EntityPlayer.class};
	private Class<?>[] mgcArg = {ModContainer.class};
	public int id;

	public GuiPack(Class<? extends ModContainer> mcIn){
		id = 0;
		mc = mcIn;
	}

	@SideOnly(Side.CLIENT)
	public void setModGuiContainer(Class<? extends ModGuiContainer> mgcIn){
		mgc = mgcIn;
	}

	/** Instant a new Container and return */
	public ModContainer getContainer(EntityPlayer player){
		try{
			return mc.getDeclaredConstructor(mcArg).newInstance(player);
		}catch(Exception e){
			return new ModContainer(player);
		}
	}
	/** Instant a new GuiContainer and return */
	@SideOnly(Side.CLIENT)
	public ModGuiContainer getGuiContainer(EntityPlayer player){
		try{
			return mgc.getDeclaredConstructor(mgcArg).newInstance(mc.getDeclaredConstructor(mcArg).newInstance(player));
		}catch(Exception e){
			return new ModGuiContainer(new ModContainer(player));
		}
	}
}