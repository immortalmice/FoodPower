package com.github.immortalmice.foodpower.customclass.gui.market;

import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.Crops;

public class MarketContainer extends ModContainer{

	protected World world; 
	protected BlockPos pos;
	protected SlotItemHandler emeraldSlot;

	public MarketContainer(EntityPlayer playerIn, World worldIn, BlockPos posIn){
		super(playerIn, 51);

		world = worldIn;
		pos = posIn;

		items = new ItemStackHandler(2);
		this.addSlotToContainer(emeraldSlot = new SlotItemHandler(items, 0, 89, 20){
			/** Can trade with nether star? Hey, I only want EMERALD */
			@Override
			public boolean isItemValid(ItemStack stack){
				return stack != null && stack.getItem() == Items.EMERALD && super.isItemValid(stack);
			}
			@Override
			public void onSlotChanged(){
				MarketContainer.this.refreshGood();
			}
		});
		this.addSlotToContainer(new SlotItemHandler(items, 1, 137, 20){
			@Override
			public boolean isItemValid(ItemStack stack){
				return false;
			}
			/** One Emerald, One Sapling */
			@Override
			public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack){
				ItemStack emeraldItemStack = MarketContainer.this.items.getStackInSlot(0);
				emeraldItemStack.setCount(emeraldItemStack.getCount() - 1);
				MarketContainer.this.refreshGood();
				return stack;
			}
			/** I REALLY don't want implement ITickable on this simple, troublesome market block.  */
			@Override
			public ItemStack getStack(){
				MarketContainer.this.refreshGood();
				return super.getStack();
			}
		});
	}
	/** No this override, market will be conscienceless, you can try :) */
	@Override
	public void onContainerClosed(EntityPlayer playerIn){
		super.onContainerClosed(playerIn);
		if (playerIn.isServerWorld()){
			if(playerIn.inventory.getFirstEmptyStack() != -1){
				playerIn.inventory.addItemStackToInventory(items.getStackInSlot(0));
			}else{
				playerIn.dropItem(items.getStackInSlot(0), false);
			}
		}
	}
	/** Hope not crash again PLEASE.... */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int fromSlot){
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if(slot != null && slot.getHasStack() && !slot.getStack().isEmpty()){
			ItemStack current = slot.getStack();
			previous = current.copy();
			// Custom behaviour //
			if(fromSlot < 36){
				// From TE Inventory to Player Inventory
				if (!this.mergeItemStack(current, 36, 38, true))
				    return ItemStack.EMPTY;
			}else if(fromSlot == 37){
				Slot emerald = this.inventorySlots.get(37);
				if(emerald != null && slot.getHasStack()){
					int amount = emerald.getStack().getCount();
					current.setCount(amount);
					if(!this.mergeItemStack(current, 0, 36, false))
						return ItemStack.EMPTY;
				}
			}else{
					// From Player Inventory to TE Inventory
				if (!this.mergeItemStack(current, 0, 36, false))
				    return ItemStack.EMPTY;
			}
			// Custom behaviour //
			if (current.isEmpty() && current.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (current.getCount() == previous.getCount())
				return ItemStack.EMPTY;
			slot.onTake(entityPlayer, current);
		}
		return previous;
	}
	/** Get Index Form NBT, And Return Right Item */
	public Item getItem(){
		TileEntity tile = this.world.getTileEntity(pos);
		int index = tile.getUpdateTag().getInteger("index");

		int treeSize = Trees.saplingBushList.size();
		int cropSize = Crops.seedList.size();

		if(index >= 0 && index <= treeSize -1){
			return Item.getItemFromBlock(Trees.saplingBushList.get(index));
		}else if(index >= treeSize && index <= treeSize + cropSize -1){
			return Crops.seedList.get(index - treeSize);
		}else{
			return Items.AIR;
		}
	}
	/** No Emerald, No Sapling */
	public void refreshGood(){
		ItemStack stack = emeraldSlot.getStack();
		if(!stack.isEmpty()){
			MarketContainer.this.items.setStackInSlot(1, new ItemStack(getItem()));
		}else{
			MarketContainer.this.items.setStackInSlot(1, ItemStack.EMPTY);
		}
	}
}