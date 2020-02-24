package com.github.immortalmice.foodpower.customclass.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.util.ResourceLocation;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.lists.TileEntitys;
import com.github.immortalmice.foodpower.baseclass.TileEntityBase;

public class TileEntityPack{
	private Class<? extends TileEntity> tile;
	private String name;

	public TileEntityPack(Class<? extends TileEntity> tileIn, String nameIn){
		this.tile = tileIn;
		this.name = nameIn;

		TileEntitys.list.add(this);
	}

	public TileEntity getTileEntity(){
		try{
			return tile.getDeclaredConstructor(new Class<?>[]{}).newInstance();
		}catch(Exception e){
			return new TileEntityBase();
		}
	}

	public void registThis(){
		GameRegistry.registerTileEntity(tile, new ResourceLocation(FoodPower.MODID, name));
	}
}