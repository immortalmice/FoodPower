package com.github.immortalmice.foodpower.container.recipetable;

import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.cooking.ICookingElement;
import com.github.immortalmice.foodpower.cooking.ICookingElement.ElementType;
import com.github.immortalmice.foodpower.food.Ingredient;
import com.github.immortalmice.foodpower.lists.Ingredients;

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

	public ICookingElement getSlotElement(){
		return this.slotElement;
	}

	public void tryRegistIngrediant(ItemStack hold, int diff){
		ItemStack result = this.getStack();
		Ingredient holdIngredient = Ingredients.getIngredientByItem(hold.getItem());

		/* If click with not empty itemStack, regist it */
		if(!hold.isEmpty()
			&& !result.isItemEqual(hold)
			&& this.slotElement.getElementType() == ElementType.FOOD_TYPE
			&& holdIngredient != null
			&& this.slotElement.isMatch(holdIngredient)
		){
			result = new ItemStack(hold.getItem(), 0);
		}

		int finalAmount = Math.min(Math.max(result.getCount() + diff, 1), 3);
		result.setCount(finalAmount);
		this.putStack(result);
	}
}