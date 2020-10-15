package com.github.immortalmice.foodpower.customclass.tileentity.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.baseclass.TileEntityBase;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe.ItemStackRequest;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe.StepRequest;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.customclass.util.ItemStackNBT;
import com.github.immortalmice.foodpower.lists.TileEntitys;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class KitchenApplianceTileEntity extends TileEntityBase implements ITickableTileEntity{
	private KitchenAppliance block;
	private KitchenApplanceItemHandler itemHandler = new KitchenApplanceItemHandler();
	private EnergyStorage energyStorage = new EnergyStorage(1000000){
		@Override
		public int receiveEnergy(int maxReceive, boolean simulate){
			if(!simulate){
				KitchenApplianceTileEntity.this.markDirty();
			}
			return super.receiveEnergy(maxReceive, simulate);
		}
		@Override
		public int extractEnergy(int maxExtract, boolean simulate){
			if(!simulate){
				KitchenApplianceTileEntity.this.markDirty();
			}
			return super.extractEnergy(maxExtract, simulate);
		}
	};
	
	private int progress = 0;

	private static final String ENERGY_NBT_KEY = "forge_energy";
	private static final String BLOCK_NBT_KEY = "kitchen_appliance_block";
	private static final String ITEM_NBT_KEY = "item_handler";
	private static final String PROGRESS_NBT_KEY = "progress";
	
	public static final Function<Integer, Integer> requiredTicksFun = amount -> 180 + amount * 30;
	public static final int energyPerTick = 500;

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

	public EnergyStorage getEnergyStorage(){
		return this.energyStorage;
	}
	
	public int getProgress(){
		return this.progress;
	}

	public KitchenApplanceItemHandler getItemHandler(){
		return this.itemHandler;
	}

	@Override
	public void read(CompoundNBT nbt){
		super.read(nbt);

		if(nbt.contains(KitchenApplianceTileEntity.BLOCK_NBT_KEY)){
			Block nbtBlock = ForgeRegistries.BLOCKS.getValue(
				new ResourceLocation(nbt.getString(KitchenApplianceTileEntity.BLOCK_NBT_KEY)));
			
			if(nbtBlock != null && nbtBlock instanceof KitchenAppliance){
				this.block = (KitchenAppliance) nbtBlock;
			}
		}
		
		if(nbt.contains(KitchenApplianceTileEntity.ENERGY_NBT_KEY)){
			CapabilityEnergy.ENERGY.readNBT(this.energyStorage, null, nbt.get(KitchenApplianceTileEntity.ENERGY_NBT_KEY));
		}

		if(nbt.contains(KitchenApplianceTileEntity.ITEM_NBT_KEY)){
			this.itemHandler.deserializeNBT((CompoundNBT) nbt.get(KitchenApplianceTileEntity.ITEM_NBT_KEY));
		}
		
		if(nbt.contains(KitchenApplianceTileEntity.PROGRESS_NBT_KEY)){
			this.progress = nbt.getInt(KitchenApplianceTileEntity.PROGRESS_NBT_KEY);
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt){
		if(this.block != null){
			nbt.putString(KitchenApplianceTileEntity.BLOCK_NBT_KEY, this.block.getRegistryName().toString());
		}
		nbt.put(KitchenApplianceTileEntity.ENERGY_NBT_KEY, CapabilityEnergy.ENERGY.writeNBT(this.energyStorage, null));
		nbt.put(KitchenApplianceTileEntity.ITEM_NBT_KEY, this.itemHandler.serializeNBT());
		nbt.putInt(KitchenApplianceTileEntity.PROGRESS_NBT_KEY, this.progress);
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
		if(this.world.isRemote) return;

		boolean isModified = false;
		
		if(this.itemHandler.isCurrentRequestSatisfied() && this.itemHandler.getStackInSlot(1).isEmpty() && this.block != null){
			if(this.block.isElectrical() && this.energyStorage.getEnergyStored() >= KitchenApplianceTileEntity.energyPerTick){
				this.energyStorage.extractEnergy(KitchenApplianceTileEntity.energyPerTick, false);
				this.progress ++;
				isModified = true;
			}else if(!this.block.isElectrical()){
				this.progress ++;
				isModified = true;
			}
		}else{
			if(this.progress != 0){
				this.progress = 0;
				isModified = true;
			}
		}
		
		StepRequest stepRequest = this.itemHandler.getCurrentStepRequest();
		if(stepRequest != null && this.progress >= KitchenApplianceTileEntity.requiredTicksFun.apply(stepRequest.getOutputAmount())){
			this.progress = 0;
			this.itemHandler.ingredients = NonNullList.withSize(stepRequest.getRequires().size(), ItemStack.EMPTY);
			ItemStack result = stepRequest.getResult() instanceof Meal ? Meal.create(this.itemHandler.cacheScroll) : CookedFood.create(stepRequest);
			this.itemHandler.setStackInSlot(1, result);
			isModified = true;
		}
		
		if(isModified) this.markDirty();
	}

	public class KitchenApplanceItemHandler extends ItemStackHandler{
		private List<ItemStack> ingredients = new ArrayList<>();

		private List<StepRequest> requests = new ArrayList<>();
		private int requestIndex = 0;
		
		private ItemStack cacheScroll = ItemStack.EMPTY;
		private boolean isSatisfiedCache = false;
		private boolean isSatisfiedCacheModified = true;

		private KitchenApplanceItemHandler(){
			// 0: scroll slot
			// 1: output slot
			// 2~: ingredient slot(s)
			super(2);
		}

		@Nullable
		private CookingRecipe getRecipe(){
			return RecipeScroll.readCookingRecipe(this.getStackInSlot(0));
		}

		public boolean isCurrentRequestSatisfied(){
			if(!this.isSatisfiedCacheModified) return this.isSatisfiedCache;

			StepRequest request = this.getCurrentStepRequest();
			this.isSatisfiedCacheModified = false;
			if(request != null){
				List<ItemStackRequest> requires = request.getRequires();
				for(int i = 0; i <= requires.size()-1; i ++){
					if(i > this.ingredients.size()-1 ||
						!requires.get(i).isSatisfied(this.ingredients.get(i))){
						this.isSatisfiedCache = false;
						return false;
					}
				}
				this.isSatisfiedCache = true;
				return true;
			}
			this.isSatisfiedCache = false;
			return false;
		}

		@Nullable
		public StepRequest getCurrentStepRequest(){
			if(this.requestIndex >= 0 && this.requestIndex <= this.requests.size()-1){
				return this.requests.get(this.requestIndex);
			}
			return null;
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack){
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
							return false;
						default:
							StepRequest stepRequest = this.getCurrentStepRequest();
							if(stepRequest != null){
								ItemStackRequest request = stepRequest.getRequires().get(slot - 2);
								return request.isMatched(stack);
							}else if(slot == 2){
								return true;
							}
					}
				}
			}
			return true;
		}

		public List<ItemStack> getItems(){
			List<ItemStack> list = new ArrayList<>();
			for(int i = 0; i <= 1; i ++){
				ItemStack stack = this.getStackInSlot(i);
				if(!stack.isEmpty()){
					list.add(stack);
				}
			}
			return list;
		}

		@Override
		protected void onContentsChanged(int slot){
			this.isSatisfiedCacheModified = true;
			if(slot == 0 && !ItemStack.areItemStacksEqual(this.cacheScroll, this.getStackInSlot(0))){
				this.cacheScroll = this.getStackInSlot(0);
				CookingRecipe recipe = this.getRecipe();
				if(recipe != null && KitchenApplianceTileEntity.this.getBlock() != null){
					this.requests = recipe.getStepReqests(KitchenApplianceTileEntity.this.getBlock());
					this.requestIndex = 0;

					StepRequest stepRequest = this.getCurrentStepRequest();
					if(stepRequest != null){
						List<ItemStackRequest> requests = stepRequest.getRequires();
						this.setSize(requests.size() + 2);
						this.ingredients = NonNullList.withSize(requests.size(), ItemStack.EMPTY);
						for(int i = 2; i <= this.stacks.size() - 1; i ++){
							this.stacks.set(i, new ItemStack(requests.get(i - 2).getItem()));
						}
						return;
					}
				}
				this.requests.clear();
				this.requestIndex = 0;
				this.setSize(2);
			}
		}

		@Override
		public void setSize(int size){
			if(size >= 0){
				NonNullList<ItemStack> newStacks = NonNullList.withSize(size, ItemStack.EMPTY);
				int end = Math.min(newStacks.size() - 1, this.stacks.size() - 1);
				for(int i = 0; i <= end; i ++){
					newStacks.set(i, this.stacks.get(i));
				}
				this.stacks = newStacks;
			}
		}

		@Override
		@Nonnull
		public ItemStack getStackInSlot(int slot){
			if(slot >= 0 && slot <= this.stacks.size() - 1){
				return this.stacks.get(slot);
			}
			return ItemStack.EMPTY;
		}

		public ItemStack getRealStack(int slot){
			if(slot <= 1){
				return this.getStackInSlot(slot);
			}
			return this.ingredients.get(slot - 2);
		}

		@Override
	    @Nonnull
	    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
	    	if(slot <= 1 || slot > this.stacks.size() - 1) return super.insertItem(slot, stack, simulate);
	    	if(!isItemValid(slot, stack)) return stack;

	    	StepRequest step = this.getCurrentStepRequest();
	    	if(step != null){
	    		ItemStackRequest request = step.getRequires().get(slot - 2);
	    		int requiredAmount = request.getAmount();
		    	int acceptAmount = Math.min(stack.getCount(), requiredAmount - this.getRealStack(slot).getCount());

		    	ItemStack remain = stack.copy();
		    	remain.setCount(stack.getCount() - acceptAmount);
		    	if(!simulate){
		    		this.ingredients.set(slot - 2, new ItemStack(request.getItem(), this.ingredients.get(slot - 2).getCount() + acceptAmount));
		    		this.isSatisfiedCacheModified = true;
		    		KitchenApplianceTileEntity.this.markDirty();
		    	}
	    		return remain;
	    	}
	    	return stack;
	    }

	    @Override
	    @Nonnull
	    public ItemStack extractItem(int slot, int amount, boolean simulate){
	    	// Extract scroll when ingredient exist IS NOT ALLOWED
	    	if(slot == 0
	    		&& amount > 0
	    		&& this.ingredients.stream().filter(stack -> !stack.isEmpty()).findFirst().isPresent()
	    	) return ItemStack.EMPTY;

	    	if(slot <= 1 || slot > this.stacks.size() - 1) return super.extractItem(slot, amount, simulate);

	    	ItemStack currentStack = this.getRealStack(slot);
	    	ItemStack extractStack = new ItemStack(currentStack.getItem(), Math.min(currentStack.getCount(), amount));
	    	if(!simulate){
	    		this.ingredients.set(slot - 2, new ItemStack(currentStack.getItem(), currentStack.getCount() - extractStack.getCount()));
	    		this.isSatisfiedCacheModified = true;
	    		KitchenApplianceTileEntity.this.markDirty();
	    	}
	    	return extractStack;
	    }

	    @Override
    	public CompoundNBT serializeNBT(){
    		CompoundNBT nbt = super.serializeNBT();
    		ListNBT ingredientsNBT = new ListNBT();
    		this.ingredients.forEach(stack -> {
    			ingredientsNBT.add(ItemStackNBT.write(stack));
    		});
    		nbt.put("ingredients", ingredientsNBT);
    		nbt.putInt("request_index", this.requestIndex);
    		return nbt;
    	}

    	@Override
    	public void deserializeNBT(CompoundNBT nbt){
    		super.deserializeNBT(nbt);
    		ListNBT ingredientsNBT = (ListNBT) nbt.get("ingredients");
    		if(ingredientsNBT != null){
    			this.ingredients = new ArrayList<>();
    			for(int i = 0; i <= ingredientsNBT.size()-1; i ++){
    				this.ingredients.add(ItemStackNBT.read(ingredientsNBT.getCompound(i)));
    			}
    		}
    		this.requestIndex = nbt.getInt("request_index");
    		
    		this.cacheScroll = this.getStackInSlot(0);
    		CookingRecipe recipe = this.getRecipe();
    		if(recipe != null && KitchenApplianceTileEntity.this.getBlock() != null){
    			this.requests = recipe.getStepReqests(KitchenApplianceTileEntity.this.getBlock());
    		}
    	}
	}
}