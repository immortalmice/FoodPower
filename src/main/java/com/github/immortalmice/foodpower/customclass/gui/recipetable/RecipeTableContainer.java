package com.github.immortalmice.foodpower.customclass.gui.recipetable;

import java.util.List;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.Ingredient;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.RecipeTableTileEntity;
import com.github.immortalmice.foodpower.lists.other.OtherItems;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class RecipeTableContainer extends ModContainer{

	protected World world; 
	protected BlockPos pos;
	protected ItemStackHandler scroll, ingredients;
	protected RecipeTableTileEntity tileEntity;
	private int lastIndex = 0;

	public RecipeTableContainer(EntityPlayer playerIn, World worldIn, BlockPos posIn){
		super(playerIn, new int[]{45, 145});
		
		this.world = worldIn;
		this.pos = posIn;
		this.tileEntity = (RecipeTableTileEntity)worldIn.getTileEntity(this.pos);

		this.scroll = new ItemStackHandler(1);

		this.addSlotToContainer(new SlotItemHandler(scroll, 0, 182, 71){
			/** Only Recipe Scroll Accepted Here */
			@Override
			public boolean isItemValid(ItemStack stack){
				return stack != null 
					&& stack.getItem() == OtherItems.RECIPE_SCROLL 
					&& super.isItemValid(stack);
			}
		});

		//this.updateSlot();
	}
	/** Dynamic ingredient slots */
	private void updateSlot(){
		this.inventorySlots = this.inventorySlots.subList(0, 37);

		List<Ingredient> ingredientList = this.getIngredients();
		this.ingredients = new ItemStackHandler(ingredientList.size());
		for(int i = 0; i <= ingredientList.size()-1; i ++){
			this.addSlotToContainer(new SlotItemHandler(ingredients, i, 10, 20 * i + 40));
		}
	}
	public void tryUpdateSlot(){
		if(this.getIndex() != this.lastIndex){
			this.lastIndex = this.getIndex();
			this.updateSlot();
		}
	}
	/** Get ingreidient list of current pattern */
	public List<Ingredient> getIngredients(){
		CookingPattern currentPattern = CookingPatterns.list.get(this.getIndex());
		return currentPattern.getIngredients();
	}
    public int getIndex(){
    	return tileEntity.getIndex();
    }
}