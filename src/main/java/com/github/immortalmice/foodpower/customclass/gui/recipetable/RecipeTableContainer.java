package com.github.immortalmice.foodpower.customclass.gui.recipetable;

import java.util.ArrayList;
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
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.customclass.tileentity.classes.RecipeTableTileEntity;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class RecipeTableContainer extends ModContainer{

	protected World world; 
	protected BlockPos pos;
	protected ItemStackHandler scrollSlot, bookSlot, ingredientsSlot;
	protected RecipeTableTileEntity tileEntity;

	private int index = 0;
	private String inputText = "";

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
			/** Only Writable Book Accepted Here */
			@Override
			public boolean isItemValid(ItemStack stack){
				return stack != null 
					&& stack.getItem() == Items.WRITABLE_BOOK 
					&& super.isItemValid(stack);
			}
			@Override
			public void onSlotChanged(){
				RecipeTableContainer.this.refreshScroll();
			}
		});

		this.addSlotToContainer(new SlotItemHandler(scrollSlot, 0, 182, 71){
			@Override
			public boolean isItemValid(ItemStack stack){
				return false;
			}
			@Override
			public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack){
				RecipeTableContainer.this.bookSlot.setStackInSlot(0, ItemStack.EMPTY);
				/* Init the amount needed in recipe, and rarity */
				RecipeScroll.initStack(stack, RecipeTableContainer.this.world.rand);
				return stack;
			}
		});

		this.updateSlot();
	}
	/* Dynamic ingredient slots */
	private void updateSlot(){
		this.inventorySlots = this.inventorySlots.subList(0, 38);

		List<Ingredient> ingredientList = this.getIngredients();
		this.ingredientsSlot = new ItemStackHandler(ingredientList.size());

		/* Make A Slot Circle With N Slots */
		int[][] slotPos = this.getSlotPos();
		for(int i = 0; i <= ingredientList.size()-1; i ++){
			this.addSlotToContainer(new RecipeTableSlot(ingredientsSlot, i
				, slotPos[i][0] - 8, slotPos[i][1] - 8
				, ingredientList.get(i), this));
		}
		this.refreshScroll();
	}

	/* Update Scroll in slot */
	public void refreshScroll(){
		if(bookSlot.getStackInSlot(0).getItem() == Items.WRITABLE_BOOK
			&& !this.hasEmptyIngredientSlot()){

			ItemStack scroll = RecipeScroll.create(
				  CookingPatterns.list.get(this.index)
				, this.getStacksInIngredientSlots()
				, this.inputText);

			scrollSlot.setStackInSlot(0, scroll);
		}else{
			scrollSlot.setStackInSlot(0, ItemStack.EMPTY);
		}
	}

	private boolean hasEmptyIngredientSlot(){
		for(int i = 0; i <= ingredientsSlot.getSlots()-1; i ++){
			if(ingredientsSlot.getStackInSlot(i).isEmpty()){
				return true;
			}
		}
		return false;
	}

	private List<ItemStack> getStacksInIngredientSlots(){
		List<ItemStack> ingredientsList = new ArrayList<ItemStack>();
		for(int i = 0; i <= ingredientsSlot.getSlots()-1; i ++){
			ingredientsList.add(ingredientsSlot.getStackInSlot(i));
		}
		return ingredientsList;
	}

	/* Detect Index and inputText Change Or Not */
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

		String tileInputText = this.tileEntity.getInputText();
		if(this.inputText != tileInputText){
			for(int i = 0; i <= this.listeners.size()-1; i ++){
				this.listeners.get(i).sendWindowProperty(this, 1, -1);
			}
			this.inputText = tileInputText;
			this.refreshScroll();
		}
	}

	/* Update Index and inputText On Server Message */
	@SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data){
    	super.updateProgressBar(id, data);

    	switch(id){
    		case 0:
    			this.index = data;
    			this.updateSlot();
    			break;
    		case 1:
    			this.inputText = this.tileEntity.getInputText();
    			this.refreshScroll();
    			break;
    	}
    }

    @Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int fromSlot){
		return ItemStack.EMPTY;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn){
		super.onContainerClosed(playerIn);
		if(playerIn.isServerWorld()){
			if(playerIn.inventory.getFirstEmptyStack() != -1){
				playerIn.inventory.addItemStackToInventory(bookSlot.getStackInSlot(0));
			}else{
				playerIn.dropItem(bookSlot.getStackInSlot(0), false);
			}
		}
	}

	/* Click on slot to regist the ingredient to it */
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer playerIn){
		if(slotId >= 38 && slotId <= this.ingredientsSlot.getSlots() + 37){
			Slot slot = this.getSlot(slotId);
			if(slot instanceof RecipeTableSlot){
				RecipeTableSlot currentSlot = (RecipeTableSlot) slot;
				currentSlot.tryRegistIngrediant(playerIn.inventory.getItemStack());
			}
			return ItemStack.EMPTY;
		}
		return super.slotClick(slotId, dragType, clickTypeIn, playerIn);
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

    public String getInputText(){
    	return this.tileEntity.getInputText();
    }
}