package com.github.immortalmice.foodpower.customclass.gui;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.gui.ModGuiContainer;
import com.github.immortalmice.foodpower.lists.GUIs;

public class GuiPack{
	private Class<? extends ModContainer> mc = null;
	private Class<? extends ModGuiContainer> mgc = null;
	private Class<?>[] mcArg = {EntityPlayer.class};
	private Class<?>[] mgcArg = {ModContainer.class};
	private int id;

	public GuiPack(Class<? extends ModContainer> mcIn,@Nullable Class<? extends ModGuiContainer> mgcIn){
		mc = mcIn;
		mgc = mgcIn;
		/** Add to GUIs list */
		id = GUIs.list.size();
		GUIs.list.add(this);
	}

	public int getId(){
		return id;
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