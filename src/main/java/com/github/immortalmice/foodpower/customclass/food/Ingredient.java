package com.github.immortalmice.foodpower.customclass.food;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.customclass.client.TooltipUtil;
import com.github.immortalmice.foodpower.customclass.effect.FoodEffect;
import com.github.immortalmice.foodpower.customclass.flavor.FlavorType;
import com.github.immortalmice.foodpower.lists.FlavorTypes;
import com.github.immortalmice.foodpower.lists.FoodTypes;

public class Ingredient extends ItemFoodBase{
	private FoodType foodType;
	private FlavorType flavorType;
	private FoodEffect effect;
	private boolean isVanilla = false;
	/* baseAmount is the value that this ingredient needed per meal in level 1 */
	private double baseAmount;
	/** For Mod Ingredients */
	public Ingredient(String nameIn, int healing, float saturation, FoodType ftIn, FlavorType flavorIn, double amountIn){
		super(nameIn, healing, saturation);

		this.foodType = ftIn;
		this.flavorType = flavorIn;
		this.baseAmount = amountIn;
	}
	/* For Vanilla Ingredient Food or not Food */
	public Ingredient(String nameIn, Item itemIn, FoodType ftIn, FlavorType flavorIn, double amountIn){
		this(nameIn
			, itemIn.isFood() ? itemIn.getFood().getHealing() : 0
			, itemIn.isFood() ? itemIn.getFood().getSaturation() : 0.0f
			, ftIn, flavorIn, amountIn);

		this.isVanilla = true;
	}
	/* For CookedFoods & Meals */
	public Ingredient(String nameIn){
		this(nameIn, 2, 0.4f, FoodTypes.NONE, FlavorTypes.NONE, 1);

		this.isVanilla = true;
	}
	/* For Empty (Use In Present A Food With That Type) */
	public Ingredient(FoodType ftIn){
		this("empty", 0, 0.0f, ftIn, FlavorTypes.NONE, 1);
	}

	public boolean isEqual(Ingredient a){
		return this.getFPName() == a.getFPName();
	}

	public boolean isTypeEqual(Ingredient a){
		return this.foodType == a.foodType;
	}

    public boolean isEmpty(){
    	return this.getFPName().equals("empty");
    }

	public FoodType getFoodType(){
		return this.foodType;
	}

	public FlavorType getFlavorType(){
		return this.flavorType;
	}

	public FoodEffect getEffect(){
		return this.effect;
	}

	public double getBaseAmount(){
		return this.baseAmount;
	}

	public void setEffect(FoodEffect effectIn){
		this.effect = effectIn;
	}

	public boolean isVanillaItem(){
		return this.isVanilla;
	}
	/* Show FoodType & FlavorType on tooltip */
	@OnlyIn(Dist.CLIENT)
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    	super.addInformation(stack, worldIn, tooltip, flagIn);

    	TooltipUtil tooltipHelper = new TooltipUtil(tooltip);

    	String foodTypeName = TooltipUtil.translate("general.foodpower.food_type");
    	String foodTypeValue = TooltipUtil.translate("food_type.foodpower." + this.foodType.getName());
    	tooltipHelper.add(foodTypeName + " : " + foodTypeValue);

    	String flavorTypeName = TooltipUtil.translate("general.foodpower.flavor_type");
    	String flavorTypeValue = TooltipUtil.translate("flavor_type.foodpower." + this.flavorType.getName());
    	tooltipHelper.add(flavorTypeName + " : " + flavorTypeValue);
    }
}
