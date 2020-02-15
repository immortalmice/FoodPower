package com.github.immortalmice.foodpower.customclass.food;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFood;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.FoodTypes;

public class Ingredient extends ItemFoodBase{
	private String name;
	private FoodType foodType;
	/** For Mod Ingredients */
	public Ingredient(String nameIn, int amount, float saturation, FoodType ftIn){
		super(nameIn, amount, saturation);

		this.name = nameIn;
		this.foodType = ftIn;

        /** Add to ingredient list, and regist it later */
        Ingredients.list.add(this);
	}
	/** For Vanilla Ingredient is Food */
	public Ingredient(String nameIn, ItemFood itemIn, FoodType ftIn){
		this(nameIn, itemIn.getHealAmount(ItemStack.EMPTY), itemIn.getSaturationModifier(ItemStack.EMPTY), ftIn);

		/** Add to ingredient list, and regist it later */
		Ingredients.vanillaList.add(this);
	}
	/** For Vanilla Ingredient not Food */
	public Ingredient(String nameIn, Item itemIn, FoodType ftIn){
		this(nameIn, 0, 0.0f, ftIn);

		Ingredients.vanillaList.add(this);
	}
	/** For CookedFoods */
	public Ingredient(String nameIn){
		this(nameIn, 2, 0.4f, FoodTypes.NONE);
	}
	/** For Empty (Use In Present A Food With That Type) */
	public Ingredient(FoodType ftIn){
		this("empty", 0, 0.0f, ftIn);
	}

	public boolean isEqual(Ingredient a){
		return this.name == a.name;
	}

	public boolean isTypeEqual(Ingredient a){
		return this.foodType == a.foodType;
	}

    public boolean isEmpty(){
    	return this.name == "empty";
    }

	public FoodType getFoodType(){
		return this.foodType;
	}

	public String getName(){
		return this.name;
	}

	@SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    	super.addInformation(stack, worldIn, tooltip, flagIn);

    	String foodTypeName = I18n.format("food_type." + this.foodType.getName() + ".name");
    	tooltip.add(I18n.format("general.food_type.name") + " : " + foodTypeName);
    }
}
