package com.github.immortalmice.foodpower;

import net.minecraftforge.fml.common.Mod;

import com.github.immortalmice.foodpower.bus.ModEventHandlers;

@Mod(FoodPower.MODID)
public class FoodPower{
    public static final String MODID = "foodpower";
    public static final String NAME = "Food Power";
    public static final String VERSION = "0.1";

    public static final FoodPower INSTANCE = new FoodPower();

    private FoodPower(){
    	ModEventHandlers.registAllEvent();
    }
}
