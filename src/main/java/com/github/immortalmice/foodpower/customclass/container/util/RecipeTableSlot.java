package com.github.immortalmice.foodpower.customclass.container.util;

import java.lang.Math;

import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.customclass.container.classes.recipetable.RecipeTableContainer;
import com.github.immortalmice.foodpower.customclass.cooking.ICookingElement;

/* Custom Slot, Design for Slots in Recipe Table */
public class RecipeTableSlot extends SlotItemHandler{
	
	private ICookingElement slotElement;
	private RecipeTableContainer container;

	public RecipeTableSlot(ItemStackHandler itemStackHandlerIn, int idIn
							, int x, int y
							, ICookingElement elementIn
							, RecipeTableContainer containerIn){
		super(itemStackHandlerIn, idIn, x, y);

		this.container = containerIn;
		this.slotElement = elementIn;

		if(this.slotElement instanceof Ingredient){
			this.putStack(new ItemStack(((Ingredient) this.slotElement).asItem(), 1));
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
			switch(this.slotElement.getElementType()){
				case FOOD_TYPE:
					/* Clear */
					result = ItemStack.EMPTY;
					break;
				case INGREDIENT:
					/* Decrease stack size to 1 */
					result.setCount(1);
					break;
				default:
					
			}
		}

		Ingredient holdIngredient = Ingredients.getIngredientByItem(hold.getItem());

		if(holdIngredient != null && this.slotElement.isMatch(holdIngredient)){
			result = hold.copy();
			/* Max stack size is 3 */
			result.setCount(Math.min(result.getCount(), 3));
		}
		this.putStack(result);
	}
}