package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.DeferredRegister;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.tileentity.TileEntityPack;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.KitchenApplianceTileEntity;

public class TileEntitys{
	public static final DeferredRegister<TileEntityType<?>> REGISTER = new DeferredRegister<TileEntityType<?>>(ForgeRegistries.TILE_ENTITIES, FoodPower.MODID);
	public static final List<TileEntityPack<? extends TileEntityBase>> list = new ArrayList<TileEntityPack<? extends TileEntityBase>>();

	public static final TileEntityPack<KitchenApplianceTileEntity> KITCHEN_APPLIANCE_PACK = new TileEntityPack<KitchenApplianceTileEntity>("kitchen_appliance", KitchenApplianceTileEntity::new, KitchenAppliances.list.toArray(new KitchenAppliance[KitchenAppliances.list.size()]));

	@ObjectHolder(FoodPower.MODID)
	public static class TileEntityTypes{
		public static final TileEntityType<KitchenApplianceTileEntity> KITCHEN_APPLIANCE = null;
	}
}