package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.customclass.tileentity.TileEntityPack;

public class TileEntitys{
	public static final DeferredRegister<TileEntityType<?>> REGISTER = new DeferredRegister<TileEntityType<?>>(ForgeRegistries.TILE_ENTITIES, FoodPower.MODID);
	public static final List<TileEntityPack<? extends TileEntityBase>> list = new ArrayList<TileEntityPack<? extends TileEntityBase>>();
}