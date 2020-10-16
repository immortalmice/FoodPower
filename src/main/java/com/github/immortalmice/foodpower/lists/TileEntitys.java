package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.DeferredRegister;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.tileentity.KitchenApplianceTileEntity;
import com.github.immortalmice.foodpower.tileentity.TileEntityPack;

public class TileEntitys{
	public static final DeferredRegister<TileEntityType<?>> REGISTER = new DeferredRegister<TileEntityType<?>>(ForgeRegistries.TILE_ENTITIES, FoodPower.MODID);
	public static final List<TileEntityPack<? extends TileEntityBase>> list = new ArrayList<TileEntityPack<? extends TileEntityBase>>();

	public static final TileEntityPack<KitchenApplianceTileEntity> KITCHEN_APPLIANCE_PACK = new TileEntityPack<KitchenApplianceTileEntity>("kitchen_appliance", KitchenApplianceTileEntity::new, KitchenAppliances.list);

	@ObjectHolder(FoodPower.MODID)
	public static class TileEntityTypes{
		public static final TileEntityType<KitchenApplianceTileEntity> KITCHEN_APPLIANCE = null;
	}
}