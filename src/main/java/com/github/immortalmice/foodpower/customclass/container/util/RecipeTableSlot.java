package com.github.immortalmice.foodpower.customclass.container.util;

import java.lang.Math;

import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.customclass.container.classes.recipetable.RecipeTableContainer;

/* Custom Slot, Design for Slots in Recipe Table */
public class RecipeTableSlot extends SlotItemHandler{
	private Ingredient ingredient;
	private RecipeTableContainer container;
	public RecipeTableSlot(ItemStackHandler itemStackHandlerIn, int idIn
							, int x, int y
							, Ingredient ingredientIn
							, RecipeTableContainer containerIn){
		super(itemStackHandlerIn, idIn, x, y);

		this.ingredient = ingredientIn;
		this.container = containerIn;

		if(!this.ingredient.isEmpty()){
			this.putStack(new ItemStack(this.ingredient, 1));
		}
	}
	@Override
	public boolean canTakeStack(PlayerEntity playerIn){
		return false;
	}

	@Override
	public void onSlotChanged(){
		container.refreshScroll();
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

		Ingredient holdIngredient = Ingredients.getIngredientByItem(hold.getItem());
		if(!holdIngredient.isEmpty()){
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