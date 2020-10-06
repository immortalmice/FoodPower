package com.github.immortalmice.foodpower.customclass.container.classes.recipetable;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.IntReferenceHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.cooking.ICookingElement;
import com.github.immortalmice.foodpower.customclass.food.Meal;
import com.github.immortalmice.foodpower.customclass.message.classes.RecipeTableMessage;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.customclass.util.SlotPosProvider.Position2D;
import com.github.immortalmice.foodpower.customclass.util.SlotPosProvider.RecipeTableSlotPos;
import com.github.immortalmice.foodpower.lists.Containers;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class RecipeTableContainer extends ContainerBase{

	protected ItemStackHandler scrollSlot, bookSlot, ingredientsSlot;

	/* index is the current page in container */
	private int index = 0;
	private String inputText = "";
	private final int windowId;
	private boolean isCreative;

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

		this.addSlot(new SlotItemHandler(bookSlot, 0, RecipeTableSlotPos.CENTER.translateToLeftTop().x, RecipeTableSlotPos.CENTER.translateToLeftTop().y){
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
			/* You can't take the stack, but I handle this in slotClick method */
			@Override
			public boolean canTakeStack(PlayerEntity playerIn){
				return false;
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

		List<ICookingElement> elementList = this.getCurrentRootElements();
		this.ingredientsSlot.setSize(elementList.size());

		/* Make A Slot Circle With N Slots */
		List<Position2D> slotPos = RecipeTableSlotPos.provide(elementList.size());
		for(int i = 0; i <= elementList.size()-1; i ++){
			this.addSlot(new RecipeTableSlot(ingredientsSlot, i
				, slotPos.get(i).x, slotPos.get(i).y
				, elementList.get(i), this));
		}
		this.refreshScroll();
	}

	/* Update Scroll in slot */
	public void refreshScroll(){
		scrollSlot.setStackInSlot(0, this.getScroll(false));
	}

	/* Server will call this when a scroll is successfully taken by player */
	public void setScrollTaken(){
		ItemStack book = this.bookSlot.getStackInSlot(0);
		/* Decrease written book stack count in slot by 1, just consider may other mod change max stack size of written book */
		book.setCount(book.getCount() - 1);
		this.bookSlot.setStackInSlot(0, book);
		this.refreshScroll();
		return;
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
	public ItemStack transferStackInSlot(PlayerEntity player, int from){
		Slot fromSlot = this.inventorySlots.get(from);
		ItemStack copy = fromSlot.getStack().copy();

		if(fromSlot != null && fromSlot.getHasStack() && fromSlot.getStack().getItem() == Items.WRITABLE_BOOK){
			if(
				// From scroll slot
				(from == 36 && this.mergeItemStack(fromSlot.getStack(), 0, 36, false)) ||
				// From player inventory 
				(from < 36 && this.scrollSlot.getStackInSlot(0).isEmpty() && this.mergeItemStack(fromSlot.getStack(), 36, 37, true))
			){
				// Merge success
				this.refreshScroll();
				return copy;
			}
		}
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
		Slot slot = this.getSlot(slotId);
		if(slotId >= 38 && slotId <= this.ingredientsSlot.getSlots() + 37){
			if(slot instanceof RecipeTableSlot && clickTypeIn == ClickType.PICKUP){
				RecipeTableSlot currentSlot = (RecipeTableSlot) slot;
				// diff = Left Clicked: +1, Right Clicked: -1
				int diff = (Container.getDragEvent(dragType) * 2 - 1) * -1;
				currentSlot.tryRegistIngrediant(playerIn.inventory.getItemStack(), diff);
			}
			return ItemStack.EMPTY;
		}else if(slotId == 37){
			this.refreshScroll();
			ItemStack scroll = slot.getStack();
			if(!scroll.isEmpty() && scroll.getItem() instanceof RecipeScroll){
				FoodPower.NETWORK.sendToServer(new RecipeTableMessage(RecipeTableContainer.this.getWindowId(), "Init Recipe Scroll", scroll));
			}
		}
		this.refreshScroll();
		return super.slotClick(slotId, dragType, clickTypeIn, playerIn);
	}

	/* Get ingreidient list of current pattern */
	public List<ICookingElement> getCurrentRootElements(){
		CookingPattern currentPattern = CookingPatterns.list.get(this.index);
		return currentPattern.getRootElements();
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