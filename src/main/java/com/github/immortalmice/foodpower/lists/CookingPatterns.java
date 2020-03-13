package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.lists.KitchenAppliances.Blocks;
import com.github.immortalmice.foodpower.lists.Ingredients.Items;
import com.github.immortalmice.foodpower.lists.FoodTypes;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.cooking.CookingStep;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;

public class CookingPatterns{
	public static List<CookingPattern> list = new ArrayList<CookingPattern>();

	public static CookingPattern CAKE = new CookingPattern("cake", Items.CAKE, new CookingStep[]{
		new CookingStep(Blocks.UNIVERSAL_STATION, Items.BATTER, new Ingredient[]{
			Items.FLOUR, Items.EGG, Items.BUTTER, Items.SUGAR, new Ingredient(FoodTypes.SWEET)
		}),
		new CookingStep(Blocks.OVEN, Items.CAKE_BASE, new Ingredient[]{
			Items.BATTER
		}),
		new CookingStep(Blocks.REVOLVING_CAKE_STAND, Items.CAKE, new Ingredient[]{
			Items.CAKE_BASE, Items.CREAM, new Ingredient(FoodTypes.FRUIT)
		})
	});
	
	public static CookingPattern PIZZA = new CookingPattern("pizza", Items.PIZZA, new CookingStep[]{
		new CookingStep(Blocks.CHOPPING_BOARD, Items.FLAT_DOUGH, new Ingredient[]{
			Items.DOUGH
		}),
		new CookingStep(Blocks.UNIVERSAL_STATION, Items.RAW_PIZZA, new Ingredient[]{
			Items.FLAT_DOUGH, Items.CHEESE, Items.KETCHUP, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE)
		}),
		new CookingStep(Blocks.OVEN, Items.COOKED_PIZZA, new Ingredient[]{
			Items.RAW_PIZZA
		}),
		new CookingStep(Blocks.CHOPPING_BOARD, Items.PIZZA, new Ingredient[]{
			Items.COOKED_PIZZA
		})
	});
	
	public static CookingPattern SANDWICH = new CookingPattern("sandwich", Items.SANDWICH, new CookingStep[]{
		new CookingStep(Blocks.OVEN, Items.TOAST, new Ingredient[]{
			Items.DOUGH
		}),
		new CookingStep(Blocks.CHOPPING_BOARD, Items.TOAST_SLICE, new Ingredient[]{
			Items.TOAST
		}),
		new CookingStep(Blocks.UNIVERSAL_STATION, Items.SANDWICH, new Ingredient[]{
			Items.TOAST_SLICE, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE), new Ingredient(FoodTypes.FRUIT)
		})
	});
	
	public static CookingPattern ICE_CREAM = new CookingPattern("ice_cream", Items.ICE_CREAM, new CookingStep[]{
		new CookingStep(Blocks.JUICER, Items.RAW_JUICE, new Ingredient[]{
			new Ingredient(FoodTypes.FRUIT)
		}),
		new CookingStep(Blocks.UNIVERSAL_STATION, Items.MIXED_JUICE, new Ingredient[]{
			Items.RAW_JUICE, Items.MILK_BUCKET, Items.CREAM
		}),
		new CookingStep(Blocks.REFRIGERATOR, Items.ICE_CREAM_BASE, new Ingredient[]{
			Items.MIXED_JUICE
		}),
		new CookingStep(Blocks.UNIVERSAL_STATION, Items.ICE_CREAM, new Ingredient[]{
			Items.ICE_CREAM_BASE, new Ingredient(FoodTypes.SWEET)
		})
	});
	
	public static CookingPattern FRIED_RICE = new CookingPattern("fried_rice", Items.FRIED_RICE, new CookingStep[]{
		new CookingStep(Blocks.ELECTRIC_POT, Items.COOKED_RICE, new Ingredient[]{
			Items.RICE, Items.WATER_BUCKET
		}),
		new CookingStep(Blocks.FRYING_PAN, Items.FRIED_RICE, new Ingredient[]{
			Items.COOKED_RICE, Items.SALT, Items.SAUCE, Items.OIL, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE)
		})
	});
	
	public static CookingPattern NOODLE_SOUP = new CookingPattern("noodle_soup", Items.NOODLE_SOUP, new CookingStep[]{
		new CookingStep(Blocks.CHOPPING_BOARD, Items.FLAT_DOUGH, new Ingredient[]{
			Items.DOUGH
		}),
		new CookingStep(Blocks.CHOPPING_BOARD, Items.NOODLE, new Ingredient[]{
			Items.FLAT_DOUGH
		}),
		new CookingStep(Blocks.STOCKPOT, Items.NOODLE_SOUP, new Ingredient[]{
			Items.WATER_BUCKET, Items.SALT, Items.OIL, new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.VEGETABLE)
		})
	});
	
	public static CookingPattern HONEY_TOAST = new CookingPattern("honey_toast", Items.HONEY_TOAST, new CookingStep[]{
		new CookingStep(Blocks.OVEN, Items.TOAST, new Ingredient[]{
			Items.DOUGH
		}),
		new CookingStep(Blocks.UNIVERSAL_STATION, Items.HONEY_TOAST, new Ingredient[]{
			Items.TOAST, Items.HONEY_BOTTLE, Items.CREAM, new Ingredient(FoodTypes.FRUIT), new Ingredient(FoodTypes.SWEET)
		})
	});
	
	public static CookingPattern SALAD = new CookingPattern("salad", Items.SALAD, new CookingStep[]{
		new CookingStep(Blocks.UNIVERSAL_STATION, Items.SALAD, new Ingredient[]{
			new Ingredient(FoodTypes.VEGETABLE), new Ingredient(FoodTypes.MEAT), new Ingredient(FoodTypes.SEASONING)
		})
	});
	
	public static CookingPattern JUICE = new CookingPattern("juice", Items.JUICE, new CookingStep[]{
		new CookingStep(Blocks.JUICER, Items.RAW_JUICE, new Ingredient[]{
			new Ingredient(FoodTypes.FRUIT), new Ingredient(FoodTypes.FRUIT)
		}),
		new CookingStep(Blocks.REFRIGERATOR, Items.ICE, new Ingredient[]{
			Items.WATER_BUCKET
		}),
		new CookingStep(Blocks.SHAKER, Items.JUICE, new Ingredient[]{
			Items.RAW_JUICE, Items.ICE, Items.SUGAR
		})
	});
}