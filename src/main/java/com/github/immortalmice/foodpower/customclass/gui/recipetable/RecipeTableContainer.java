package com.github.immortalmice.foodpower.customclass.gui.recipetable;

import java.util.List;
import java.lang.Math;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.customclass.gui.RecipeTableSlot;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.RecipeTableTileEntity;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class RecipeTableContainer extends ModContainer{

	protected World world; 
	protected BlockPos pos;
	protected ItemStackHandler scrollSlot, bookSlot, ingredientsSlot;
	protected RecipeTableTileEntity tileEntity;
	private int index = 0;

	private final int RADIUS = 40;
	private final int[] CENTER = {90, 80};

	public RecipeTableContainer(EntityPlayer playerIn, World worldIn, BlockPos posIn){
		super(playerIn, new int[]{45, 145});
		
		this.world = worldIn;
		this.pos = posIn;
		this.tileEntity = (RecipeTableTileEntity)worldIn.getTileEntity(this.pos);

		this.bookSlot = new ItemStackHandler(1);
		this.scrollSlot = new ItemStackHandler(1);

		this.addSlotToContainer(new SlotItemHandler(bookSlot, 0, 83, 72){
			/** Only Recipe Scroll Accepted Here */
			@Override
			public boolean isItemValid(ItemStack stack){
				return stack != null 
					&& stack.getItem() == Items.WRITABLE_BOOK 
					&& super.isItemValid(stack);
			}
		});

		this.addSlotToContainer(new SlotItemHandler(scrollSlot, 0, 182, 71){
			/** Only Recipe Scroll Accepted Here */
			@Override
			public boolean isItemValid(ItemStack stack){
				return false;
			}
		});

		this.updateSlot();
	}
	/** Dynamic ingredient slots */
	private void updateSlot(){
		this.inventorySlots = this.inventorySlots.subList(0, 38);

		List<Ingredient> ingredientList = this.getIngredients();
		this.ingredientsSlot = new ItemStackHandler(ingredientList.size());

		/* Make A Slot Circle With N Slots */
		int[][] slotPos = this.getSlotPos();
		for(int i = 0; i <= ingredientList.size()-1; i ++){
			this.addSlotToContainer(new RecipeTableSlot(ingredientsSlot, i
				, slotPos[i][0] - 8, slotPos[i][1] - 8
				, ingredientList.get(i)));
		}
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

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer playerIn){
		if(slotId <= 37){
			return super.slotClick(slotId, dragType, clickTypeIn, playerIn);
		}else{
			if(dragType != 0){
				Slot slot = this.getSlot(slotId);
				if(slot instanceof RecipeTableSlot){
					RecipeTableSlot currentSlot = (RecipeTableSlot) slot;
					ItemStack result = currentSlot.tryRegistIngrediant(playerIn.inventory.getItemStack());
					this.putStackInSlot(slotId, result);
				}
			}
		}
		return ItemStack.EMPTY;
	}

	/** Get ingreidient list of current pattern */
	public List<Ingredient> getIngredients(){
		CookingPattern currentPattern = CookingPatterns.list.get(this.index);
		return currentPattern.getIngredients();
	}
	/* Compute the coordinate list of circle */
	public int[][] getSlotPos(){
		int count = this.getIngredients().size();
		int[][] result = new int[count][2];

		float angle = 360 / count;
		for(int i = 0; i <= count-1; i ++){

			int[] slotPostInGui = {
				(int)(this.CENTER[0] + this.RADIUS * Math.cos((angle * i - 90) * Math.PI / 180)),
				(int)(this.CENTER[1] + this.RADIUS * Math.sin((angle * i - 90) * Math.PI / 180))
			};

			result[i] = slotPostInGui;
		}
		return result;
	}

	public int getIndex(){
    	return this.index;
    }
}