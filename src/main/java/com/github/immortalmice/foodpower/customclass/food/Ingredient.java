package com.github.immortalmice.foodpower.customclass.food;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.lists.FoodTypes;

public class Ingredient extends ItemFoodBase{
	private FoodType foodType;
	/* baseAmount is the value that this ingredient needed per food in level 1 */
	private double baseAmount;
	/** For Mod Ingredients */
	public Ingredient(String nameIn, int healing, float saturation, FoodType ftIn, double amountIn){
		super(nameIn, healing, saturation);

		this.foodType = ftIn;
		this.baseAmount = amountIn;
	}
	/* For Vanilla Ingredient Food or not Food */
	public Ingredient(String nameIn, Item itemIn, FoodType ftIn, double amountIn){
		this(nameIn
			, itemIn.isFood() ? itemIn.getFood().getHealing() : 0
			, itemIn.isFood() ? itemIn.getFood().getSaturation() : 0.0f
			, ftIn, amountIn);
	}
	/* For CookedFoods & Meals */
	public Ingredient(String nameIn){
		this(nameIn, 2, 0.4f, FoodTypes.NONE, 1);
	}
	/* For Empty (Use In Present A Food With That Type) */
	public Ingredient(FoodType ftIn){
		this("empty", 0, 0.0f, ftIn, 1);
	}

	public boolean isEqual(Ingredient a){
		return this.getFPName() == a.getFPName();
	}

	public boolean isTypeEqual(Ingredient a){
		return this.foodType == a.foodType;
	}

    public boolean isEmpty(){
    	return this.getFPName() == "empty";
    }

	public FoodType getFoodType(){
		return this.foodType;
	}

	public double getBaseAmount(){
		return this.baseAmount;
	}
	/* Show FoodType on tooltip */
	@OnlyIn(Dist.CLIENT)
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    	super.addInformation(stack, worldIn, tooltip, flagIn);

    	String foodTypeName = I18n.format("general.foodpower.food_type");
    	String foodType = I18n.format("food_type.foodpower." + this.foodType.getName());
    	tooltip.add(new StringTextComponent(foodTypeName + " : " + foodType));
    }
}
