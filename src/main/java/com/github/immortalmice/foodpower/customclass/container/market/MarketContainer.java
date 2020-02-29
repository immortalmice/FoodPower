package com.github.immortalmice.foodpower.customclass.container.market;

import javax.annotation.Nullable;

import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import io.netty.buffer.Unpooled;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.customclass.container.market.MarketScreen;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.MarketTileEntity;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.Crops;

public class MarketContainer extends ContainerBase{

	protected ItemStackHandler items;
	protected SlotItemHandler emeraldSlot;
	protected MarketTileEntity tileEntity;
	private static final ContainerType<MarketContainer> TYPE = Containers.MARKET.getContainerType();

	private IntReferenceHolder index = IntReferenceHolder.single();

	public MarketContainer(int windowId, PlayerInventory inv, PacketBuffer extraData){
		super(MarketContainer.TYPE, windowId, new int[]{8, 51}, inv);
	}

	public MarketContainer(int windowId, PlayerInventory playerInventory
		, PlayerEntity playerIn, World worldIn, BlockPos posIn){

		this(windowId, playerInventory, new PacketBuffer(Unpooled.EMPTY_BUFFER));

		this.tileEntity = (MarketTileEntity)worldIn.getTileEntity(posIn);

		items = new ItemStackHandler(2);
		this.addSlot(emeraldSlot = new SlotItemHandler(items, 0, 89, 20){
			/* Can trade with nether star? Hey, I only want EMERALD */
			@Override
			public boolean isItemValid(ItemStack stack){
				return stack != null 
					&& stack.getItem() == Items.EMERALD 
					&& super.isItemValid(stack);
			}
			@Override
			public void onSlotChanged(){
				MarketContainer.this.refreshGood();
			}
		});
		this.addSlot(new SlotItemHandler(items, 1, 137, 20){
			@Override
			public boolean isItemValid(ItemStack stack){
				return false;
			}
			/* One Emerald, One Sapling */
			@Override
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack){
				ItemStack emeraldItemStack = MarketContainer.this.items.getStackInSlot(0);
				emeraldItemStack.setCount(emeraldItemStack.getCount() - 1);
				MarketContainer.this.refreshGood();
				return stack;
			}
		});

		this.trackInt(this.index).set(0);
	}
	/* Detect Index Change Or Not */
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();

		int tileIndex = this.tileEntity.getIndex();
		if(this.index != tileIndex){
			for(int i = 0; i <= this.listeners.size()-1; i ++){
				this.listeners.get(i).sendWindowProperty(this, 0, tileIndex);
			}
			this.index = tileIndex;
			this.refreshGood();
		}
	}
	/* Update Index On Server Message */
	@SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data){
    	super.updateProgressBar(id, data);

    	switch(id){
    		case 0:
    			this.index = data;
    			this.refreshGood();
    			break;
    	}
    }
	/* No this override, market will be conscienceless, you can try :) */
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
	/* Hope not crash again PLEASE.... */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int fromSlot){
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if(slot != null && slot.getHasStack() && !slot.getStack().isEmpty()){
			ItemStack current = slot.getStack();
			previous = current.copy();
			// Custom behaviour
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
			// Custom behaviour
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
	@Override
	@OnlyIn(Dist.CLIENT)
	public static void registScreen(ContainerType<? extends ContainerBase> containerTypeIn){
    	ScreenManager.registerFactory(containerTypeIn, MarketScreen::new);
    }
	/* Get Index Form NBT, And Return Right Item */
	public Item getItem(){
		int treeSize = Trees.saplingBushList.size();
		int cropSize = Crops.seedList.size();

		if(this.index >= 0 && this.index <= treeSize -1){
			return Item.getItemFromBlock(Trees.saplingBushList.get(this.index));
		}else if(this.index >= treeSize && this.index <= treeSize + cropSize -1){
			return Crops.seedList.get(this.index - treeSize);
		}else{
			return Items.AIR;
		}
	}
	/* No Emerald, No Sapling */
	public void refreshGood(){
		ItemStack stack = emeraldSlot.getStack();
		if(!stack.isEmpty()){
			MarketContainer.this.items.setStackInSlot(1, new ItemStack(getItem()));
		}else{
			MarketContainer.this.items.setStackInSlot(1, ItemStack.EMPTY);
		}
	}
}