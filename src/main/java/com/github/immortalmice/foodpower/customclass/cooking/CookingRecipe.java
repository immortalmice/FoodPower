package com.github.immortalmice.foodpower.customclass.cooking;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.mojang.datafixers.util.Pair;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/* A pattern fulfilled with selected ingredients & levels */
public class CookingRecipe{
	private final CookingPattern pattern;
	private String recipeName;
	private List<Pair<ItemStack, Integer>> ingredients = new ArrayList<>();
	private boolean isInitialized = false;

	private int rarity = 0;
	private float rand = 1.1f;
	private int outputAmount = 1;

	private static final float[] RARITY_DISCOUNT = {1.0f, 0.8f, 0.6f, 0.4f};

	public CookingRecipe(CookingPattern patternIn, List<ItemStack> listIn, String nameIn){
		this.pattern = patternIn;
		this.recipeName = nameIn;
		listIn.forEach((stack) -> {
			this.ingredients.add(new Pair<>(stack, stack.getCount()));
		});
	}

	public CookingRecipe(CookingPattern patternIn, List<ItemStack> listIn){
		this(patternIn, listIn, "");
	}

	public CookingPattern getPattern(){
		return this.pattern;
	}

	public ITextComponent getDisplayName(){
		if(!this.recipeName.equals("")){
    		return new StringTextComponent(this.recipeName);
    	}
    	return new TranslationTextComponent("general.foodpower.unknown_recipe");
	}

	public void initialize(int rarityIn, float randIn){
		if(!this.isInitialized){
			this.rarity = rarityIn;
			this.rand = randIn;
			this.setOutputAmount(this.outputAmount);
		}
	}

	public void setOutputAmount(int amount){
		this.outputAmount = amount;

		this.ingredients.forEach((pair) -> {
			ItemStack stack = pair.getFirst();
			int level = pair.getSecond();

			if(stack.getItem() instanceof Ingredient){
				Ingredient ingredient = (Ingredient) stack.getItem();

				float newCount = this.outputAmount;
				newCount *= ingredient.getBaseAmount();
				newCount *= Math.pow(2, level - 1);
				newCount *= this.rand;
				newCount *= CookingRecipe.RARITY_DISCOUNT[this.rarity];

				stack.setCount((int) Math.ceil(newCount));
			}
		});
	}

	public static CompoundNBT write(CookingRecipe recipe){
		CompoundNBT nbt = new CompoundNBT();

		nbt.putString("pattern", recipe.pattern.getName());
		nbt.putInt("rarity", recipe.rarity);
		nbt.putFloat("rand", recipe.rand);
		nbt.putInt("output_amount", recipe.outputAmount);
		nbt.putString("displayName", recipe.recipeName);

		ListNBT listNBT = new ListNBT();
		recipe.ingredients.forEach((pair) -> {
			CompoundNBT element = new CompoundNBT();

			ItemStack stack = pair.getFirst();
			int level = pair.getSecond();

			element.put("stack", stack.write(new CompoundNBT()));
			element.putInt("level", level);

			listNBT.add(element);
		});
		nbt.put("ingredients", listNBT);

		return nbt;
	}

	@Nullable
	public static CookingRecipe read(CompoundNBT nbt){
		if(nbt.contains("pattern") && nbt.contains("ingredients")){
			CookingPattern pattern = CookingPatterns.getPatternByName(nbt.getString("pattern"));
			if(pattern == null) return null;
			final CookingRecipe recipe = new CookingRecipe(pattern, new ArrayList<>());

			ListNBT listNBT = (ListNBT) nbt.get("ingredients");
			listNBT.forEach((ele) -> {
				CompoundNBT element = (CompoundNBT) ele;
				if(element.contains("stack") && element.contains("level")){
					ItemStack stack = ItemStack.read((CompoundNBT) element.get("stack"));
					int level = element.getInt("level");

					recipe.ingredients.add(new Pair<>(stack, level));
				}
			});

			recipe.outputAmount = nbt.contains("outputAmount") ? nbt.getInt("outputAmount") : 1;
			recipe.recipeName = nbt.contains("displayName") ? nbt.getString("displayName") : "";

			recipe.initialize(nbt.contains("rarity") ? nbt.getInt("rarity") : 0
				, nbt.contains("rand") ? nbt.getFloat("rand") : 1.1f);

			return recipe;
		}
		return null;
	}

	public CompoundNBT write(){
		return CookingRecipe.write(this);
	}
}