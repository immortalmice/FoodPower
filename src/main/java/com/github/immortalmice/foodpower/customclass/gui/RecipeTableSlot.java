package com.github.immortalmice.foodpower.customclass.gui;

import java.lang.Math;

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

		if(!this.ingredient.isEmpty()){
			this.putStack(new ItemStack(this.ingredient, 1));
		}
	}
	@Override
	public boolean canTakeStack(EntityPlayer playerIn){
		return false;
	}

	public void tryRegistIngrediant(ItemStack hold){
		ItemStack result = this.getStack();

		/* If click with empty itemStack */
		if(hold.isEmpty()){
			/* If it's custom ingrediant */
			if(this.ingredient.isEmpty()){
				/* Clear */
				result = ItemStack.EMPTY;
			}else{
				/* Decrease stack size to 1 */
				result.setCount(1);
			}
		}

		if(hold.getItem() instanceof Ingredient){
			Ingredient holdIngredient = (Ingredient) hold.getItem();

			/* If it's custom ingredient, only check food type */
			if(ingredient.isEmpty()){
				if(holdIngredient.isTypeEqual(ingredient)){
					result = hold.copy();
				}
			}else{
				if(holdIngredient.isEqual(ingredient)){
					result = hold.copy();
				}
			}
			/* Max stack size is 3 */
			result.setCount(Math.min(result.getCount(), 3));
		}
		this.putStack(result);
	}
}