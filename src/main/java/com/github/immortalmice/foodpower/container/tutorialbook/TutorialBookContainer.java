package com.github.immortalmice.foodpower.container.tutorialbook;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.lists.Containers.ContainerTypes;
import com.github.immortalmice.foodpower.specialclass.TutorialBook;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntReferenceHolder;

public class TutorialBookContainer extends ContainerBase {
	private int pageIndex = 0;
	
	public TutorialBookContainer(int windowIdIn, PlayerInventory playerInventory, PacketBuffer extraData){
		this(windowIdIn, playerInventory, extraData.readInt());
	}
	
	public TutorialBookContainer(int windowIdIn, PlayerInventory inventoryIn, int pageIndexIn) {
		super(ContainerTypes.TUTORIAL_BOOK, windowIdIn, new int[] { -1, -1 }, inventoryIn);
		
		this.pageIndex = pageIndexIn;
		
		this.trackInt(new IntReferenceHolder() {	
			@Override
			public void set(int index) {
				TutorialBookContainer.this.setPage(index);
			}
			@Override
			public int get() {
				return TutorialBookContainer.this.pageIndex;
			}
		});
	}
	
	public void setPage(int index) {
		this.pageIndex = index;
	}
	
	public int getPage() {
		return this.pageIndex;
	}


	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		
		ItemStack stack = playerIn.inventory.getCurrentItem();
		if(stack.getItem() instanceof TutorialBook && stack.hasTag()) {
			stack.getTag().putInt("page", this.pageIndex);
		}
	}
}
