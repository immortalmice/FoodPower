package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.lists.TileEntitys;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class KitchenApplianceTileEntity extends TileEntityBase implements ITickableTileEntity{
	private KitchenAppliance block;
	private KitchenApplanceItemHandler itemHandler = new KitchenApplanceItemHandler();
	private EnergyStorage energyStorage = new EnergyStorage(1000000);
	private int catchedEnergyStored = 0;

	private static final String ENERGY_NBT_KEY = "forge_energy";
	private static final String BLOCK_NBT_KEY = "kitchen_appliance_block";
	private static final String ITEM_NBT_KEY = "item_handler";

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

	public int getMaxEnergyStored(){
		return this.energyStorage.getMaxEnergyStored();
	}

	public boolean hasScroll(){
		return !this.itemHandler.getScroll().isEmpty();
	}

	@Override
	public void read(CompoundNBT nbt){
		super.read(nbt);

		if(nbt.contains(KitchenApplianceTileEntity.ENERGY_NBT_KEY)){
			CapabilityEnergy.ENERGY.readNBT(this.energyStorage, null, nbt.get(KitchenApplianceTileEntity.ENERGY_NBT_KEY));
		}

		if(nbt.contains(KitchenApplianceTileEntity.ITEM_NBT_KEY)){
			CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(this.itemHandler, null, nbt.get(KitchenApplianceTileEntity.ITEM_NBT_KEY));
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
		nbt.put(KitchenApplianceTileEntity.ITEM_NBT_KEY, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(this.itemHandler, null));
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
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.block != null){
			return LazyOptional.of(() -> this.itemHandler).cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void tick(){
		if(this.hasWorld() && !this.world.isRemote){
			BlockState blockState = this.getBlockState();

			if(this.energyStorage.getEnergyStored() != this.catchedEnergyStored){
				
				this.world.notifyBlockUpdate(this.pos, blockState, blockState, Constants.BlockFlags.BLOCK_UPDATE);
				this.markDirty();

				this.catchedEnergyStored = this.energyStorage.getEnergyStored();
			}
		}
	}

	public KitchenApplanceItemHandler getItemHandler(){
		return this.itemHandler;
	}

	public class KitchenApplanceItemHandler implements IItemHandler, IItemHandlerModifiable{
		private ItemStackHandler scrollHandler = new ItemStackHandler();
		private ItemStackHandler outputHandler = new ItemStackHandler();
		private ItemStackHandler ingredientHandler = new ItemStackHandler();

		private List<ItemStack> requiredItemStacks;

		private KitchenApplanceItemHandler(){

		}

		private ItemStack getScroll(){
			ItemStack stack = this.scrollHandler.getStackInSlot(0);
			if(!stack.isEmpty() && stack.getItem() instanceof RecipeScroll){
				return stack;
			}
			return ItemStack.EMPTY;
		}

		@Override
		public int getSlots(){
			// Cause scroll & output slot always exist. 
			return 2 + ingredientHandler.getSlots();
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			if(slot >= 0 && slot <= this.getSlots()-1){
				switch(slot){
					case 0:
						return this.scrollHandler.getStackInSlot(0);
					case 1:
						return this.outputHandler.getStackInSlot(0);
					default:
						return this.ingredientHandler.getStackInSlot(slot - 2);
				}
			}
			return ItemStack.EMPTY;
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
			KitchenApplianceTileEntity.this.markDirty();
			return ItemStack.EMPTY;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate){
			ItemStack stack = this.getStackInSlot(slot);
			if(!stack.isEmpty()){
				ItemStack extractStack = stack.copy();

				int extractCount = Math.min(stack.getCount(), amount);
				extractStack.setCount(extractCount);

				if(!simulate){
					stack.setCount(stack.getCount() - extractCount);
					KitchenApplianceTileEntity.this.markDirty();
				}
				return extractStack;
			}
			return ItemStack.EMPTY;
		}

		@Override
		public int getSlotLimit(int slot) {
			if(slot < 0 || slot > this.getSlots()-1){
				return 0;
			}else{
				switch(slot){
					case 0:
						return 1;
					case 1:
						return 64;
					default:
						return 1024;
				}
			}
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			if(!stack.isEmpty()){
				if(slot < 0 || slot > this.getSlots()-1){
					return false;
				}else{
					switch(slot){
						case 0:
							return stack.getItem() instanceof RecipeScroll
								&& KitchenApplianceTileEntity.this.getBlock() != null
								&& RecipeScroll.getPattern(stack) != null
								&& !RecipeScroll.getPattern(stack).getSteps(KitchenApplianceTileEntity.this.getBlock()).isEmpty();
						case 1:
							return stack.getItem() instanceof CookedFood;
						default:
							if(RecipeScroll.getRequiredItemStacks(this.getScroll()).get(slot - 2).getItem() == stack.getItem()){
								return true;
							}
							return false;
					}
				}
			}
			return false;
		}

		@Override
		public void setStackInSlot(int slot, ItemStack stack) {
			if(slot >= 0 && slot <= this.getSlots()-1){
				switch(slot){
					case 0:
						this.scrollHandler.setStackInSlot(0, stack);
						this.updateScrollInfo();
						break;
					case 1:
						this.outputHandler.setStackInSlot(0, stack);
						break;
					default:
						this.ingredientHandler.setStackInSlot(slot - 2, stack);
				}
				KitchenApplianceTileEntity.this.markDirty();
			}
		}
		
		public List<ItemStack> getItems(){
			List<ItemStack> list = new ArrayList<>();
			for(int i = 0; i <= this.getSlots()-1; i ++){
				ItemStack stack = this.getStackInSlot(i);
				if(!stack.isEmpty()){
					list.add(stack);
				}
			}
			return list;
		}

		private void updateScrollInfo(){
			ItemStack scroll = this.getScroll();
			if(scroll != null){
				this.requiredItemStacks = RecipeScroll.getRequiredItemStacks(scroll, KitchenApplianceTileEntity.this.getBlock());
				this.ingredientHandler.setSize(this.requiredItemStacks.size());
			}
		}
	}
}