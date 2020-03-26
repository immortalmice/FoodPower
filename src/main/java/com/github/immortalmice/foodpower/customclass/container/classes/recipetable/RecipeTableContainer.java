package com.github.immortalmice.foodpower.customclass.container.classes.recipetable;

import java.util.ArrayList;
import java.util.List;

import java.lang.Math;

import net.minecraft.util.IntReferenceHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.customclass.container.util.RecipeTableSlot;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class RecipeTableContainer extends ContainerBase{

	protected ItemStackHandler scrollSlot, bookSlot, ingredientsSlot;

	/* index is the current page in container */
	private int index = 0;
	private String inputText = "";
	private final int windowId;
	private boolean isCreative;

	private static final int RADIUS = 40;
	private static final int[] CENTER = {90, 80};
	private static final int PATTERN_LIST_SIZE = CookingPatterns.list.size();

	public RecipeTableContainer(int windowId, PlayerInventory inv, PacketBuffer extraData){
		this(windowId, inv, extraData.readBoolean());
	}

	public RecipeTableContainer(int windowIdIn, PlayerInventory playerInventory, boolean isCreativeIn){
		super(Containers.ContainerTypes.RECIPE_TABLE, windowIdIn, new int[]{45, 145}, playerInventory);

		this.windowId = windowIdIn;
		this.isCreative = isCreativeIn;
		this.bookSlot = new ItemStackHandler(1);
		this.scrollSlot = new ItemStackHandler(1);
		this.ingredientsSlot = new ItemStackHandler(0);

		this.addSlot(new SlotItemHandler(bookSlot, 0, 83, 72){
			/* Only Writable Book Accepted Here */
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

		this.addSlot(new SlotItemHandler(scrollSlot, 0, 182, 71){
			@Override
			public boolean isItemValid(ItemStack stack){
				return false;
			}
			@Override
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack){
				RecipeTableContainer.this.bookSlot.setStackInSlot(0, ItemStack.EMPTY);
				/* Init the ingredient amount needed in recipe, and rarity */
				RecipeScroll.initStack(stack);
				return stack;
			}
		});

		this.trackInt(new IntReferenceHolder(){
			@Override
			public int get(){
				return RecipeTableContainer.this.index;
			}
			@Override
			public void set(int value){
				RecipeTableContainer.this.index = value;
				RecipeTableContainer.this.updateSlot();
			}
		});

		updateSlot();
	}
	/* Dynamic ingredient slots */
	private void updateSlot(){
		while(this.inventorySlots.size() > 38){
			this.inventorySlots.remove(38);
		}

		List<Ingredient> ingredientList = this.getIngredients();
		this.ingredientsSlot.setSize(ingredientList.size());

		/* Make A Slot Circle With N Slots */
		int[][] slotPos = this.getSlotPos();
		for(int i = 0; i <= ingredientList.size()-1; i ++){
			this.addSlot(new RecipeTableSlot(ingredientsSlot, i
				, slotPos[i][0] - 8, slotPos[i][1] - 8
				, ingredientList.get(i), this));
		}
		this.refreshScroll();
	}

	/* Update Scroll in slot */
	public void refreshScroll(){
		scrollSlot.setStackInSlot(0, this.getScroll(false));
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

    @Override
	public ItemStack transferStackInSlot(PlayerEntity entityPlayer, int fromSlot){
		return ItemStack.EMPTY;
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn){
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
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity playerIn){
		if(slotId >= 38 && slotId <= this.ingredientsSlot.getSlots() + 37){
			Slot slot = this.getSlot(slotId);
			if(slot instanceof RecipeTableSlot){
				RecipeTableSlot currentSlot = (RecipeTableSlot) slot;
				currentSlot.tryRegistIngrediant(playerIn.inventory.getItemStack());
			}
			return ItemStack.EMPTY;
		}
		this.refreshScroll();
		return super.slotClick(slotId, dragType, clickTypeIn, playerIn);
	}

	/* Get ingreidient list of current pattern */
	public List<Ingredient> getIngredients(){
		CookingPattern currentPattern = CookingPatterns.list.get(this.index);
		return currentPattern.getIngredients();
	}

	public ItemStack getScroll(boolean isUsedInMeal){
		if(this.isVaildForScroll() || isUsedInMeal){
			return RecipeScroll.create(
					  CookingPatterns.list.get(this.index)
					, this.getStacksInIngredientSlots()
					, this.inputText);
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getFinishedMeal(int amount){
		return Meal.create(this.getScroll(true), amount);
	}
	/* Compute the coordinate list of circle */
	public int[][] getSlotPos(){
		int count = this.getIngredients().size();
		int[][] result = new int[count][2];

		float angle = 360 / count;
		for(int i = 0; i <= count-1; i ++){

			int[] slotPostInGui = {
				(int)(RecipeTableContainer.CENTER[0] + RecipeTableContainer.RADIUS * Math.cos((angle * i - 90) * Math.PI / 180)),
				(int)(RecipeTableContainer.CENTER[1] + RecipeTableContainer.RADIUS * Math.sin((angle * i - 90) * Math.PI / 180))
			};

			result[i] = slotPostInGui;
		}
		return result;
	}

	public int getIndex(){
		return this.index;
	}

	public int getWindowId(){
		return this.windowId;
	}

	public boolean isPlayerCreative(){
		return this.isCreative;
	}

	public boolean isVaildForMeal(){
		return !this.hasEmptyIngredientSlot();
	}

	public boolean isVaildForScroll(){
		return bookSlot.getStackInSlot(0).getItem() == Items.WRITABLE_BOOK 
			&& this.isVaildForMeal();
	}

	/* Increase and cycle index */
	public void increaseIndex(){
		this.index++;

		if(this.index > RecipeTableContainer.PATTERN_LIST_SIZE - 1){
			this.index -= RecipeTableContainer.PATTERN_LIST_SIZE;
		}
		
		this.updateSlot();
	}
	/* Decrease and cycle index */
	public void decreaseIndex(){
		this.index--;

		if(this.index < 0){
			this.index += RecipeTableContainer.PATTERN_LIST_SIZE;
		}
		
		this.updateSlot();
	}

    public void setInputText(String str){
    	this.inputText = str;
    	this.refreshScroll();
    }
}