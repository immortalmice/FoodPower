package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.lists.TileEntitys;

import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class KitchenApplianceTileEntity extends TileEntityBase{
	private KitchenAppliance block;
	private static final String BLOCK_NBT_KEY = "kitchen_appliance_block";

	public KitchenApplianceTileEntity(){
		this(null);
	}
	public KitchenApplianceTileEntity(KitchenAppliance blockIn){
		super(TileEntitys.TileEntityTypes.KITCHEN_APPLIANCE);
		
		this.block = blockIn;
	}

	@Nullable
	public KitchenAppliance getBlock(){
		return this.block;
	}

	@Override
	public void read(CompoundNBT nbt){
		super.read(nbt);
		if(nbt.contains(KitchenApplianceTileEntity.BLOCK_NBT_KEY)){
			Block nbtBlock = ForgeRegistries.BLOCKS.getValue(
				new ResourceLocation(nbt.getString(KitchenApplianceTileEntity.BLOCK_NBT_KEY)));
			
			if(nbtBlock != null && nbtBlock instanceof KitchenAppliance){
				this.block = (KitchenAppliance) block;
			}
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt){
		if(this.block != null){
			nbt.putString(KitchenApplianceTileEntity.BLOCK_NBT_KEY, this.block.getRegistryName().toString());
		}
		return super.write(nbt);
	}
}