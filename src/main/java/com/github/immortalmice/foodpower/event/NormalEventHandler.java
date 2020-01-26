package com.github.immortalmice.foodpower.event;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.common.MinecraftForge;

import com.github.immortalmice.foodpower.lists.GUIs;

public class NormalEventHandler{

	public NormalEventHandler(){
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onLogout(PlayerEvent.PlayerLoggedOutEvent event){
        GUIs.closePlayerContainer(event.player);
    }
}