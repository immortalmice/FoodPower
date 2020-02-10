package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.lists.KitchenAppliances;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.FoodTypes;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.cooking.CookingStep;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;

public class CookingPatterns{
	public static List<CookingPattern> list = new ArrayList<CookingPattern>();

	public static CookingPattern CAKE = new CookingPattern("cake", Ingredients.CAKE, new CookingStep[]{
		new CookingStep(KitchenAppliances.UNIVERSAL_STATION, Ingredients.BATTER, new Ingredient[]{
			Ingredients.FLOUR, Ingredients.EGG, Ingredients.BUTTER, Ingredients.SUGAR, new Ingredient(FoodTypes.SWEET)
		}),
		new CookingStep(KitchenAppliances.OVEN, Ingredients.CAKE_BASE, new Ingredient[]{
			Ingredients.BATTER
		}),
		new CookingStep(KitchenAppliances.REVOLVING_CAKE_STAND, Ingredients.CAKE, new Ingredient[]{
			Ingredients.CAKE_BASE, Ingredients.CREAM, new Ingredient(FoodTypes.FRUIT)
		})
	});
	
	public static CookingPattern PIZZA = new CookingPattern("pizza", Ingredients.PIZZA, new CookingStep[]{
		new CookingStep(KitchenAppliances.CHOPPING_BOARD, Ingredients.FLAT_DOUGH, new Ingredient[]{
			Ingredients.DOUGH
		}),
		new CookingStep(KitchenAppliances.UNIVERSAL_STATION, Ingredients.RAW_PIZZA, new Ingredient[]{
			Ingredients.FLAT_DOUGH, Ingredients.CHEESE, Ingredients.KETCHUP, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE)
		}),
		new CookingStep(KitchenAppliances.OVEN, Ingredients.COOKED_PIZZA, new Ingredient[]{
			Ingredients.RAW_PIZZA
		}),
		new CookingStep(KitchenAppliances.CHOPPING_BOARD, Ingredients.PIZZA, new Ingredient[]{
			Ingredients.COOKED_PIZZA
		})
	});
	
	public static CookingPattern SANDWICH = new CookingPattern("sandwich", Ingredients.SANDWICH, new CookingStep[]{
		new CookingStep(KitchenAppliances.OVEN, Ingredients.TOAST, new Ingredient[]{
			Ingredients.DOUGH
		}),
		new CookingStep(KitchenAppliances.CHOPPING_BOARD, Ingredients.TOAST_SLICE, new Ingredient[]{
			Ingredients.TOAST
		}),
		new CookingStep(KitchenAppliances.UNIVERSAL_STATION, Ingredients.SANDWICH, new Ingredient[]{
			Ingredients.TOAST_SLICE, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE), new Ingredient(FoodTypes.FRUIT)
		})
	});
	
	public static CookingPattern ICE_CREAM = new CookingPattern("ice_cream", Ingredients.ICE_CREAM, new CookingStep[]{
		new CookingStep(KitchenAppliances.JUICER, Ingredients.RAW_JUICE, new Ingredient[]{
			new Ingredient(FoodTypes.FRUIT)
		}),
		new CookingStep(KitchenAppliances.UNIVERSAL_STATION, Ingredients.MIXED_JUICE, new Ingredient[]{
			Ingredients.RAW_JUICE, Ingredients.MILK_BUCKET, Ingredients.CREAM
		}),
		new CookingStep(KitchenAppliances.REFRIGERATOR, Ingredients.ICE_CREAM_BASE, new Ingredient[]{
			Ingredients.MIXED_JUICE
		}),
		new CookingStep(KitchenAppliances.UNIVERSAL_STATION, Ingredients.ICE_CREAM, new Ingredient[]{
			Ingredients.ICE_CREAM_BASE, new Ingredient(FoodTypes.SWEET)
		})
	});
	
	public static CookingPattern FRIED_RICE = new CookingPattern("fried_rice", Ingredients.FRIED_RICE, new CookingStep[]{
		new CookingStep(KitchenAppliances.ELECTRIC_POT, Ingredients.COOKED_RICE, new Ingredient[]{
			Ingredients.RICE, Ingredients.WATER_BUCKET
		}),
		new CookingStep(KitchenAppliances.FRYING_PAN, Ingredients.FRIED_RICE, new Ingredient[]{
			Ingredients.COOKED_RICE, Ingredients.SALT, Ingredients.SAUCE, Ingredients.OIL, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE)
		})
	});
	
	public static CookingPattern NOODLE_SOUP = new CookingPattern("noodle_soup", Ingredients.NOODLE_SOUP, new CookingStep[]{
		new CookingStep(KitchenAppliances.CHOPPING_BOARD, Ingredients.FLAT_DOUGH, new Ingredient[]{
			Ingredients.DOUGH
		}),
		new CookingStep(KitchenAppliances.CHOPPING_BOARD, Ingredients.NOODLE, new Ingredient[]{
			Ingredients.FLAT_DOUGH
		}),
		new CookingStep(KitchenAppliances.STOCKPOT, Ingredients.NOODLE_SOUP, new Ingredient[]{
			Ingredients.WATER_BUCKET, Ingredients.SALT, Ingredients.OIL, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE)
		})
	});
	
	public static CookingPattern HONEY_TOAST = new CookingPattern("honey_toast", Ingredients.HONEY_TOAST, new CookingStep[]{
		new CookingStep(KitchenAppliances.OVEN, Ingredients.TOAST, new Ingredient[]{
			Ingredients.DOUGH
		}),
		new CookingStep(KitchenAppliances.UNIVERSAL_STATION, Ingredients.HONEY_TOAST, new Ingredient[]{
			Ingredients.TOAST, Ingredients.SUGAR, Ingredients.CREAM, new Ingredient(FoodTypes.FRUIT), new Ingredient(FoodTypes.SWEET)
		})
	});
	
	public static CookingPattern SALAD = new CookingPattern("salad", Ingredients.SALAD, new CookingStep[]{
		new CookingStep(KitchenAppliances.UNIVERSAL_STATION, Ingredients.SALAD, new Ingredient[]{
			new Ingredient(FoodTypes.VEGETABLE), new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.SEASONING)
		})
	});
	
	public static CookingPattern JUICE = new CookingPattern("juice", Ingredients.JUICE, new CookingStep[]{
		new CookingStep(KitchenAppliances.JUICER, Ingredients.RAW_JUICE, new Ingredient[]{
			new Ingredient(FoodTypes.FRUIT), new Ingredient(FoodTypes.FRUIT)
		}),
		new CookingStep(KitchenAppliances.REFRIGERATOR, Ingredients.ICE, new Ingredient[]{
			Ingredients.WATER_BUCKET
		}),
		new CookingStep(KitchenAppliances.SHAKER, Ingredients.JUICE, new Ingredient[]{
			Ingredients.RAW_JUICE, Ingredients.ICE, Ingredients.SUGAR
		})
	});
}