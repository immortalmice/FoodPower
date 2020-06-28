package com.github.immortalmice.foodpower.customclass.food;

import java.util.List;
import java.util.function.BiConsumer;

import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.customclass.client.TooltipUtil;
import com.github.immortalmice.foodpower.customclass.effect.FoodEffect;
import com.github.immortalmice.foodpower.customclass.flavor.FlavorType;
import com.github.immortalmice.foodpower.customclass.food.Meal.MealEffectContainer;
import com.github.immortalmice.foodpower.lists.FlavorTypes;

/* Do not use ingrediant directly to create ItemStack, use Ingredient#asItem */
public class Ingredient extends ItemFoodBase{
	private FoodType foodType;
	private FlavorType flavorType;
	private FoodEffect effect;
	/* baseAmount is the value that this ingredient needed per meal in level 1 */
	private double baseAmount;
	/* Real Item that registed in game */
	private Item item = null;
	/* A consumer to apply ingredient's effect with level in */
	private BiConsumer<MealEffectContainer, Integer> mealEffectBiConsumer = null;
	/* A consumer to apply some action when player interact with a meal */
	private BiConsumer<PlayerInteractEvent, Integer> interactEffectBiConsumer = null;

	/* For Mod Ingredients */
	public Ingredient(int healing, float saturation, FoodType ftIn, FlavorType flavorIn, double amountIn){
		super(healing, saturation);

		this.foodType = ftIn;
		this.flavorType = flavorIn;
		this.baseAmount = amountIn;

		if(this.item == null) this.item = this;
	}
	/* For Vanilla Ingredient Food or not Food */
	public Ingredient(String nameIn, Item itemIn, FoodType ftIn, FlavorType flavorIn, double amountIn){
		this(itemIn.isFood() ? itemIn.getFood().getHealing() : 0
			, itemIn.isFood() ? itemIn.getFood().getSaturation() : 0.0f
			, ftIn, flavorIn, amountIn);

		this.item = itemIn;
	}
	/* For Empty (Use In Present A Food With That Type) */
	public Ingredient(FoodType ftIn){
		this(0, 0.0f, ftIn, FlavorTypes.NONE, 1);
	}

	public boolean isEqual(Ingredient a){
		return this.item.getRegistryName().equals(a.item.getRegistryName());
	}

	public boolean isTypeEqual(Ingredient a){
		return this.foodType == a.foodType;
	}

    public boolean isEmpty(){
    	return this.item.getRegistryName() == null;
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

	public void applyMealEffect(MealEffectContainer container, int level){
		if(this.mealEffectBiConsumer != null)
			this.mealEffectBiConsumer.accept(container, level);
		return;
	}

	public void setMealEffectBiConsumer(BiConsumer<MealEffectContainer, Integer> consumerIn){
		this.mealEffectBiConsumer = consumerIn;

	public void applyInteractEffect(PlayerInteractEvent event, int level){
		if(this.interactEffectBiConsumer != null)
			this.interactEffectBiConsumer.accept(event, level);
		return;
	}

	public void setInteractEffectBiConsumer(BiConsumer<PlayerInteractEvent, Integer> consumerIn){
		if(this.interactEffectBiConsumer == null)
			this.interactEffectBiConsumer = consumerIn;
		return;
	}

	public Item asItem(){
		return this.item;
	}
	/* Show FoodType & FlavorType on tooltip */
    public static void addFoodAndFlavorTooltip(Ingredient ingredient, List<ITextComponent> tooltip){
    	TooltipUtil tooltipHelper = new TooltipUtil(tooltip);

    	String foodTypeName = TooltipUtil.translate("general.foodpower.food_type");
    	String foodTypeValue = TooltipUtil.translate("food_type.foodpower." + ingredient.foodType.getName());
    	tooltipHelper.add(foodTypeName + " : " + foodTypeValue);

    	String flavorTypeName = TooltipUtil.translate("general.foodpower.flavor_type");
    	String flavorTypeValue = TooltipUtil.translate("flavor_type.foodpower." + ingredient.flavorType.getName());
    	tooltipHelper.add(flavorTypeName + " : " + flavorTypeValue);
    }
}
