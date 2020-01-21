package com.github.immortalmice.foodpower.customclass.gui;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import com.github.immortalmice.foodpower.lists.GUIs;
import com.github.immortalmice.foodpower.FoodPower;

/** A Class Describe What This GUI Should Be */
public class GuiData{
	private int id, slotCount;
	private String name;
	private List<int[]> slotPos;

	public GuiData(String nameIn, int slotCountIn, List<int[]> slotPosIn){
		GUIs.list.add(this);

		id = GUIs.list.size()-1;
		name = nameIn;
		slotCount = slotCountIn;
		slotPos = slotPosIn;
	}

	/** 'Get' Functions */
	public int getId(){
		return id;
	}
	public int getSlotCount(){
		return slotCount;
	}
	public ResourceLocation getTexture(){
		String path = FoodPower.MODID + ":textures/gui/container/" + name + ".png";
		return new ResourceLocation(path);
	}
	public List<int[]> getSlotPos(){
		return slotPos;
	}
}