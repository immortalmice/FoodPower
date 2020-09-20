package com.github.immortalmice.foodpower.customclass.container.classes.recipescroll;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntReferenceHolder;

import java.util.Collections;
import java.util.List;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe.StepRequest;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;

public class RecipeScrollContainer extends ContainerBase{
	private final CookingRecipe recipe;
	private List<StepRequest> stepRequestsCache;

	public RecipeScrollContainer(int windowIdIn, PlayerInventory playerInventory, PacketBuffer extraData){
		this(windowIdIn, playerInventory, extraData.readCompoundTag());
	}
	public RecipeScrollContainer(int windowIdIn, PlayerInventory playerInventory, CompoundNBT nbtIn){
		super(Containers.ContainerTypes.RECIPE_SCROLL, windowIdIn, new int[]{-1, -1}, playerInventory);

		this.recipe = CookingRecipe.read(nbtIn);
		this.stepRequestsCache = this.recipe != null ? this.recipe.getStepReqests() : null;

		/* nbt output_amount be dirty when server end change it  */
		this.trackInt(new IntReferenceHolder(){
			@Override
			public int get(){
				return RecipeScrollContainer.this.getAmount();
			}
			@Override
			public void set(int value){
				RecipeScrollContainer.this.setAmount(value);
			}
		});
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn){
		if(playerIn.isServerWorld()){
			ItemStack stack = playerIn.getHeldItemMainhand();
			if(stack.getItem() == Items.RECIPE_SCROLL){
				RecipeScroll.writeCookingRecipe(stack, this.recipe);
			}
		}
	}

	public int getRarity(){
		return this.recipe != null ? this.recipe.getRarity() : 0;
	}

	public String getScrollName(){
		return this.recipe != null ? this.recipe.getDisplayName().getUnformattedComponentText() : I18n.format("general.foodpower.unknown_recipe");
	}

	public int getAmount(){
		return this.recipe != null ? this.recipe.getOutputAmount() : 1;
	}

	public String getPatternName(){
		return this.recipe != null ? this.recipe.getPattern().getName() : "";
	}

	public void setAmount(int amountIn){
		if(this.recipe != null){
			this.recipe.setOutputAmount(amountIn);
			this.stepRequestsCache = this.recipe.getStepReqests();
		}
	}

	public List<StepRequest> getStepReqests(){
		if(this.stepRequestsCache != null) return this.stepRequestsCache;
		return Collections.emptyList();
	}

	public int getWindowId(){
		return this.windowId;
	}
}