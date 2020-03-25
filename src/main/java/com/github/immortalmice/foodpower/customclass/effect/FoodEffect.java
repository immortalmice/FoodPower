package com.github.immortalmice.foodpower.customclass.effect;

import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.EffectBase;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class FoodEffect extends EffectBase{
	private static final String POSTFIX = "_power";
	private String ingredientName, fpName;
	public FoodEffect(String ingredientNameIn, int liquidColorIn){
		super(FoodEffect.getFPNameByIngredientName(ingredientNameIn), EffectType.BENEFICIAL, liquidColorIn);

		this.ingredientName = ingredientNameIn;
		this.fpName = FoodEffect.getFPNameByIngredientName(this.ingredientName);
	}

	@Override
	public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, int x, int y, float z){
		gui.getMinecraft().getTextureManager().bindTexture(this.getTexture());
		System.out.println(this.getTexture());
		gui.blit(x + 8, y + 8, 0, 0, 18, 18);
	}

	public EffectInstance getEffectInstance(int durationIn, int amplifierIn){
		return new EffectInstance(this, durationIn, amplifierIn, false, false);
	}

	private ResourceLocation getTexture(){
		Ingredient ingredient = Ingredients.getIngredientByName(this.getIngredientName());
		String domain = "";
		if(ingredient.isVanillaItem()){
			domain = "minecraft";
		}else{
			domain = FoodPower.MODID;
		}
		return new ResourceLocation(domain + ":textures/items/" + this.getIngredientName());
	}

	public String getIngredientName(){
		return this.ingredientName;
	}

	public String getFPName(){
		return this.fpName;
	}

	public static String getFPNameByIngredientName(String str){
		return str + FoodEffect.POSTFIX;
	}
}