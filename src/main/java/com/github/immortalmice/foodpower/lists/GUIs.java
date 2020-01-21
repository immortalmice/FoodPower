package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.github.immortalmice.foodpower.customclass.gui.GuiData;

public class GUIs{
	public static final List<GuiData> list = new ArrayList<GuiData>();

	/** Initialized GUI Data */
	/** Parameter (name, slotCount, slotPos) */
	public static final GuiData MARKET = new GuiData("market", 2, 
		Arrays.asList(
			new int[]{89, 20},
			new int[]{137, 20}
		));
}