package com.github.immortalmice.foodpower.customclass.gui.recipetable;

import java.util.List;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.RecipeTableTileEntity;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class RecipeTableContainer extends ModContainer{

	protected World world; 
	protected BlockPos pos;
	protected ItemStackHandler scrollSlot, bookSlot, ingredients;
	protected RecipeTableTileEntity tileEntity;
	private int index = 0;

	public RecipeTableContainer(EntityPlayer playerIn, World worldIn, BlockPos posIn){
		super(playerIn, new int[]{45, 145});
		
		this.world = worldIn;
		this.pos = posIn;
		this.tileEntity = (RecipeTableTileEntity)worldIn.getTileEntity(this.pos);

		this.scrollSlot = new ItemStackHandler(1);

		this.addSlotToContainer(new SlotItemHandler(scrollSlot, 0, 182, 71){
			/** Only Recipe Scroll Accepted Here */
			@Override
			public boolean isItemValid(ItemStack stack){
				return false;
			}
		});
		this.addSlotToContainer(new SlotItemHandler(scrollSlot, 0, 60, 60){
			/** Only Recipe Scroll Accepted Here */
			@Override
			public boolean isItemValid(ItemStack stack){
				return stack != null 
					&& stack.getItem() == Items.WRITABLE_BOOK 
					&& super.isItemValid(stack);
			}
		});

		this.updateSlot();
	}
	/** Dynamic ingredient slots */
	private void updateSlot(){
		this.inventorySlots = this.inventorySlots.subList(0, 38);

		List<Ingredient> ingredientList = this.getIngredients();
		/*
		 * To Do
		 *
		 */
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
			this.updateSlot();
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
    			this.updateSlot();
    			break;
    	}
    }
    @Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int fromSlot){
		return ItemStack.EMPTY;
	}

	/** Get ingreidient list of current pattern */
	public List<Ingredient> getIngredients(){
		CookingPattern currentPattern = CookingPatterns.list.get(this.index);
		return currentPattern.getIngredients();
	}

	public int getIndex(){
    	return this.index;
    }
}