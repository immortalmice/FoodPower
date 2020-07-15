package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;
import com.github.immortalmice.foodpower.lists.TileEntitys;

import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;

public class KitchenApplianceTileEntity extends TileEntityBase{
	private KitchenAppliance block;

	public KitchenApplianceTileEntity(){
		this(KitchenAppliances.Blocks.OVEN);
	}
	public KitchenApplianceTileEntity(KitchenAppliance blockIn){
		super(TileEntitys.TileEntityTypes.KITCHEN_APPLIANCE);
	}

	public KitchenAppliance getBlock(){
		return this.block;
	}

	@Override
	public void read(CompoundNBT nbt){
		super.read(nbt);
		Block block = this.world.getBlockState(this.pos).getBlock();
		if(block instanceof KitchenAppliance){
			this.block = (KitchenAppliance) block;
		}
	}
}