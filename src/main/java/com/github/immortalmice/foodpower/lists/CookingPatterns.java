package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.lists.KitchenAppliances.Blocks;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.cooking.CookingStep;
import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;

public class CookingPatterns{
	public static List<CookingPattern> list = new ArrayList<CookingPattern>();

	public static CookingPattern CAKE = new CookingPattern("cake", Meals.Items.CAKE, new CookingStep[]{
		new CookingStep(Blocks.UNIVERSAL_STATION, CookedFoods.Items.BATTER, new ItemFoodBase[]{
			Ingredients.Items.FLOUR, Ingredients.Items.EGG, Ingredients.Items.BUTTER, Ingredients.Items.SUGAR, new Ingredient(FoodTypes.SWEET)
		}),
		new CookingStep(Blocks.OVEN, CookedFoods.Items.CAKE_BASE, new ItemFoodBase[]{
			CookedFoods.Items.BATTER
		}),
		new CookingStep(Blocks.REVOLVING_CAKE_STAND, Meals.Items.CAKE, new ItemFoodBase[]{
			CookedFoods.Items.CAKE_BASE, Ingredients.Items.CREAM, new Ingredient(FoodTypes.FRUIT)
		})
	});
	
	public static CookingPattern PIZZA = new CookingPattern("pizza", Meals.Items.PIZZA, new CookingStep[]{
		new CookingStep(Blocks.CHOPPING_BOARD, CookedFoods.Items.FLAT_DOUGH, new ItemFoodBase[]{
			Ingredients.Items.DOUGH
		}),
		new CookingStep(Blocks.UNIVERSAL_STATION, CookedFoods.Items.RAW_PIZZA, new ItemFoodBase[]{
			CookedFoods.Items.FLAT_DOUGH, Ingredients.Items.CHEESE, Ingredients.Items.KETCHUP, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE)
		}),
		new CookingStep(Blocks.OVEN, CookedFoods.Items.COOKED_PIZZA, new ItemFoodBase[]{
			CookedFoods.Items.RAW_PIZZA
		}),
		new CookingStep(Blocks.CHOPPING_BOARD, Meals.Items.PIZZA, new ItemFoodBase[]{
			CookedFoods.Items.COOKED_PIZZA
		})
	});
	
	public static CookingPattern SANDWICH = new CookingPattern("sandwich", Meals.Items.SANDWICH, new CookingStep[]{
		new CookingStep(Blocks.OVEN, CookedFoods.Items.TOAST, new ItemFoodBase[]{
			Ingredients.Items.DOUGH
		}),
		new CookingStep(Blocks.CHOPPING_BOARD, CookedFoods.Items.TOAST_SLICE, new ItemFoodBase[]{
			CookedFoods.Items.TOAST
		}),
		new CookingStep(Blocks.UNIVERSAL_STATION, Meals.Items.SANDWICH, new ItemFoodBase[]{
			CookedFoods.Items.TOAST_SLICE, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE), new Ingredient(FoodTypes.FRUIT)
		})
	});
	
	public static CookingPattern ICE_CREAM = new CookingPattern("ice_cream", Meals.Items.ICE_CREAM, new CookingStep[]{
		new CookingStep(Blocks.JUICER, CookedFoods.Items.RAW_JUICE, new ItemFoodBase[]{
			new Ingredient(FoodTypes.FRUIT)
		}),
		new CookingStep(Blocks.UNIVERSAL_STATION, CookedFoods.Items.MIXED_JUICE, new ItemFoodBase[]{
			CookedFoods.Items.RAW_JUICE, Ingredients.Items.MILK_BUCKET, Ingredients.Items.CREAM
		}),
		new CookingStep(Blocks.REFRIGERATOR, CookedFoods.Items.ICE_CREAM_BASE, new ItemFoodBase[]{
			CookedFoods.Items.MIXED_JUICE
		}),
		new CookingStep(Blocks.UNIVERSAL_STATION, Meals.Items.ICE_CREAM, new ItemFoodBase[]{
			CookedFoods.Items.ICE_CREAM_BASE, new Ingredient(FoodTypes.SWEET)
		})
	});
	
	public static CookingPattern FRIED_RICE = new CookingPattern("fried_rice", Meals.Items.FRIED_RICE, new CookingStep[]{
		new CookingStep(Blocks.ELECTRIC_POT, CookedFoods.Items.COOKED_RICE, new ItemFoodBase[]{
			Ingredients.Items.RICE, Ingredients.Items.WATER_BUCKET
		}),
		new CookingStep(Blocks.FRYING_PAN, Meals.Items.FRIED_RICE, new ItemFoodBase[]{
			CookedFoods.Items.COOKED_RICE, Ingredients.Items.SALT, new Ingredient(FoodTypes.SEASONING), Ingredients.Items.OIL, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE)
		})
	});
	
	public static CookingPattern NOODLE_SOUP = new CookingPattern("noodle_soup", Meals.Items.NOODLE_SOUP, new CookingStep[]{
		new CookingStep(Blocks.CHOPPING_BOARD, CookedFoods.Items.FLAT_DOUGH, new ItemFoodBase[]{
			Ingredients.Items.DOUGH
		}),
		new CookingStep(Blocks.CHOPPING_BOARD, CookedFoods.Items.NOODLE, new ItemFoodBase[]{
			CookedFoods.Items.FLAT_DOUGH
		}),
		new CookingStep(Blocks.STOCKPOT, Meals.Items.NOODLE_SOUP, new ItemFoodBase[]{
			CookedFoods.Items.NOODLE, Ingredients.Items.WATER_BUCKET, Ingredients.Items.SALT, Ingredients.Items.OIL, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE)
		})
	});
	
	public static CookingPattern HONEY_TOAST = new CookingPattern("honey_toast", Meals.Items.HONEY_TOAST, new CookingStep[]{
		new CookingStep(Blocks.OVEN, CookedFoods.Items.TOAST, new ItemFoodBase[]{
			Ingredients.Items.DOUGH
		}),
		new CookingStep(Blocks.UNIVERSAL_STATION, Meals.Items.HONEY_TOAST, new ItemFoodBase[]{
			CookedFoods.Items.TOAST, Ingredients.Items.HONEY_BOTTLE, Ingredients.Items.CREAM, new Ingredient(FoodTypes.FRUIT), new Ingredient(FoodTypes.SWEET)
		})
	});
	
	public static CookingPattern SALAD = new CookingPattern("salad", Meals.Items.SALAD, new CookingStep[]{
		new CookingStep(Blocks.UNIVERSAL_STATION, Meals.Items.SALAD, new ItemFoodBase[]{
			new Ingredient(FoodTypes.VEGETABLE), new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.SEASONING)
		})
	});
	
	public static CookingPattern JUICE = new CookingPattern("juice", Meals.Items.JUICE, new CookingStep[]{
		new CookingStep(Blocks.JUICER, CookedFoods.Items.RAW_JUICE, new ItemFoodBase[]{
			new Ingredient(FoodTypes.FRUIT), new Ingredient(FoodTypes.FRUIT)
		}),
		new CookingStep(Blocks.REFRIGERATOR, CookedFoods.Items.ICE, new ItemFoodBase[]{
			Ingredients.Items.WATER_BUCKET
		}),
		new CookingStep(Blocks.SHAKER, Meals.Items.JUICE, new ItemFoodBase[]{
			CookedFoods.Items.RAW_JUICE, CookedFoods.Items.ICE, Ingredients.Items.SUGAR
		})
	});

	@Nullable
	public static CookingPattern getPatternByName(String nameIn){
		for(CookingPattern pattern : CookingPatterns.list){
			if(pattern.getName().equals(nameIn)){
				return pattern;
			}
		}
		return null;
	}

	public static List<String> getPatternNames(){
		List<String> names = new ArrayList<String>();
		for(CookingPattern pattern : CookingPatterns.list){
			names.add(pattern.getName());
		}
		return names;
	}
}