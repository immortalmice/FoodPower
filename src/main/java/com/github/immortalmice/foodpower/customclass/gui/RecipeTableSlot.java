package com.github.immortalmice.foodpower.customclass.gui;

import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.customclass.food.Ingredient;

/* Custom Slot, Design for Slots in Recipe Table */
public class RecipeTableSlot extends SlotItemHandler{
	private Ingredient ingredient;
	public RecipeTableSlot(ItemStackHandler itemStackHandlerIn, int idIn
							, int x, int y
							, Ingredient ingredientIn){
		super(itemStackHandlerIn, idIn, x, y);

		this.ingredient = ingredientIn;
	}
	@Override
	public boolean canTakeStack(EntityPlayer playerIn){
		return false;
	}
	public ItemStack tryRegistIngrediant(ItemStack hold){

		if(hold.getItem() instanceof Ingredient){
			Ingredient holdIngredient = (Ingredient) hold.getItem();
			if(ingredient.isEmpty()){
				if(!holdIngredient.isTypeEqual(ingredient)){
					return ItemStack.EMPTY;
				}
			}else{
				if(!holdIngredient.isEqual(ingredient)){
					return ItemStack.EMPTY;
				}
			}
			ItemStack result = hold;
			return result;
		}
		return ItemStack.EMPTY;
	}
}