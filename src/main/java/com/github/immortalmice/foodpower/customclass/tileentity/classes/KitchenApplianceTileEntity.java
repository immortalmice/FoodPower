package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.lists.TileEntitys;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.registries.ForgeRegistries;

public class KitchenApplianceTileEntity extends TileEntityBase implements ITickableTileEntity{
	private KitchenAppliance block;
	private EnergyStorage energyStorage = new EnergyStorage(1000000);
	private int catchedEnergyStored = 0;

	private static final String ENERGY_NBT_KEY = "forge_energy";
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

	public int getEnergyStored(){
		return this.energyStorage.getEnergyStored();
	}

	@Override
	public void read(CompoundNBT nbt){
		super.read(nbt);

		if(nbt.contains(KitchenApplianceTileEntity.ENERGY_NBT_KEY)){
			CapabilityEnergy.ENERGY.readNBT(this.energyStorage, null, nbt.get(KitchenApplianceTileEntity.ENERGY_NBT_KEY));
		}
		
		if(nbt.contains(KitchenApplianceTileEntity.BLOCK_NBT_KEY)){
			Block nbtBlock = ForgeRegistries.BLOCKS.getValue(
				new ResourceLocation(nbt.getString(KitchenApplianceTileEntity.BLOCK_NBT_KEY)));
			
			if(nbtBlock != null && nbtBlock instanceof KitchenAppliance){
				this.block = (KitchenAppliance) nbtBlock;
			}
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt){
		nbt.put(KitchenApplianceTileEntity.ENERGY_NBT_KEY, CapabilityEnergy.ENERGY.writeNBT(this.energyStorage, null));
		if(this.block != null){
			nbt.putString(KitchenApplianceTileEntity.BLOCK_NBT_KEY, this.block.getRegistryName().toString());
		}
		return super.write(nbt);
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final @Nullable Direction side){
		if(cap == CapabilityEnergy.ENERGY && this.block != null && this.block.isElectrical()){
			return LazyOptional.of(() -> this.energyStorage).cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void tick() {
		if(this.hasWorld() && !this.world.isRemote){
			BlockState blockState = this.getBlockState();

			if(this.energyStorage.getEnergyStored() != this.catchedEnergyStored){
				
				this.world.notifyBlockUpdate(this.pos, blockState, blockState, Constants.BlockFlags.BLOCK_UPDATE);
				this.markDirty();

				this.catchedEnergyStored = this.energyStorage.getEnergyStored();
			}
		}
	}
}