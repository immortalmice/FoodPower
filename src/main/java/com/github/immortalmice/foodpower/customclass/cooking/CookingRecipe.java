package com.github.immortalmice.foodpower.customclass.cooking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.client.TooltipUtil;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.customclass.food.FoodType;
import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.mojang.datafixers.util.Pair;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/* A pattern fulfilled with selected ingredients & levels */
public class CookingRecipe{
	private final String ID;
	private final CookingPattern pattern;
	private String recipeName;
	private List<Pair<ItemStack, Integer>> ingredients = new ArrayList<>();
	private boolean isInitialized = false;

	private int rarity = 0;
	private float rand = 1.1f;
	private int outputAmount = 1;

	private static final float[] RARITY_DISCOUNT = {1.0f, 0.8f, 0.6f, 0.4f};

	public static interface NBT_KEY{
		String PATTERN = "pattern";
		String RARITY = "rarity";
		String RAND = "rand";
		String OUTPUT_AMOUNT = "output_amount";
		String DISPLAY_NAME = "display_name";
		String ID = "id";
		String INGREDIENTS = "ingredients";
		String STACK = "stack";
		String LEVEL = "level";
		String IS_INIT = "is_init";
	}

	/**
	 * @param patternIn The CookingPattern.
	 * @param listIn The list of ingredients, the count of each stack present the level of that ingredient.
	 * @param nameIn The recipe's display name.
	 */
	public CookingRecipe(CookingPattern patternIn, List<ItemStack> listIn, String nameIn){
		this.pattern = patternIn;
		this.recipeName = nameIn;
		listIn.forEach((stack) -> {
			this.ingredients.add(new Pair<>(stack, stack.getCount()));
		});
		this.ID = String.format("%08X", new Random().nextInt() & 0xFFFFFFFF);
	}

	public CookingRecipe(CookingPattern patternIn, List<ItemStack> listIn){
		this(patternIn, listIn, "");
	}

	private CookingRecipe(CookingPattern patternIn, String IDIn){
		this.pattern = patternIn;
		this.ID = IDIn;
	}

	public String getID(){
		return this.ID;
	}

	public CookingPattern getPattern(){
		return this.pattern;
	}

	@Nullable
	public static CookingPattern getPattern(CompoundNBT nbt){
		if(nbt.contains(NBT_KEY.PATTERN)){
			return CookingPatterns.getPatternByName(nbt.getString(NBT_KEY.PATTERN));
		}
		return null;
	}

	public int getRarity(){
		return this.rarity;
	}

	public static int getRarity(CompoundNBT nbt){
		if(nbt.contains(NBT_KEY.RARITY)){
			return nbt.getInt(NBT_KEY.RARITY);
		}
		return 0;
	}

	public List<StepRequest> getStepReqests(){
		if(!this.isInitialized) this.initialize(0, 1.1f);

		final List<StepRequest> returnList = new ArrayList<>();
		final List<ItemStack> copyOfIngredients = this.ingredients.stream().map((pair) -> {
			return pair.getFirst();
		}).collect(Collectors.toList());

		// Fist filter out all ICookingElement is a CookedFood or Ingredient.
		this.pattern.getSteps().forEach((step) -> {
			final StepRequest stepRequest = new StepRequest(step.getEquipment());

			step.getElements().forEach((element) -> {
				if(element instanceof CookedFood){
					stepRequest.addSampleAsRequest(new ItemStack((CookedFood) element));
				}else if(element instanceof Ingredient){
					Optional<ItemStack> opStack = copyOfIngredients.stream().filter((stack) -> {
						return stack.getItem() == element;
					}).findFirst();
					if(opStack.isPresent()){
						stepRequest.addSampleAsRequest(opStack.get());
						// Remove it so it won't be selected again.
						copyOfIngredients.remove(opStack.get());
					}
				}
			});

			returnList.add(stepRequest);
		});

		// Then match the rest by FoodType
		int index = 0;
		for(CookingStep step : this.pattern.getSteps()){
			final StepRequest stepRequest = returnList.get(index);
			step.getElements().forEach((element) -> {
				if(element instanceof FoodType){
					Optional<ItemStack> opStack = copyOfIngredients.stream().filter((stack) -> {
						return stack.getItem() instanceof Ingredient ? element.isMatch((Ingredient) stack.getItem()) : false;
					}).findFirst();
					if(opStack.isPresent()){
						stepRequest.addSampleAsRequest(opStack.get());
						copyOfIngredients.remove(opStack.get());
					}
				}
			});

			index ++;
		}
		return returnList;
	}

	public List<StepRequest> getStepReqests(KitchenAppliance applianceIn){
		return this.getStepReqests().stream().filter((stepRequest) -> {
			return stepRequest.getEquipment() == applianceIn;
		}).collect(Collectors.toList());
	}

	public int getOutputAmount(){
		return this.outputAmount;
	}

	public ITextComponent getDisplayName(){
		if(!this.recipeName.equals("")){
    		return new StringTextComponent(this.recipeName);
    	}
    	return new TranslationTextComponent("general.foodpower.unknown_recipe");
	}

	public static ITextComponent getDisplayName(CompoundNBT nbt){
		if(nbt.contains(NBT_KEY.DISPLAY_NAME) && !nbt.getString(NBT_KEY.DISPLAY_NAME).equals("")){
    		return new StringTextComponent(nbt.getString(NBT_KEY.DISPLAY_NAME));
    	}
    	return new TranslationTextComponent("general.foodpower.unknown_recipe");
	}

	public void appendIngredientInfo(TooltipUtil tooltipHelper){
		if(this.ingredients.size() != 0){
            tooltipHelper.newBlankRow();
    		tooltipHelper.addTranslate("general.foodpower.ingredients");

    		this.ingredients.forEach((pair) -> {
    			ItemStack stack = pair.getFirst();
    			int level = pair.getSecond();

    			String ingredientStr = TooltipUtil.translate(stack.getItem().getTranslationKey());
    			ingredientStr += " [" + TooltipUtil.translate("general.foodpower.level") + level + "]";
    			if(this.isInitialized)
    				ingredientStr += " (" + stack.getCount() + ")";

    			tooltipHelper.addWithLeftSpace(ingredientStr);
    		});
    	}
	}

	public void initialize(int rarityIn, float randIn){
		if(!this.isInitialized){
			this.rarity = rarityIn;
			this.rand = randIn;
			this.setOutputAmount(this.outputAmount);
			this.isInitialized = true;
		}
	}

	public void setOutputAmount(int amount){
		this.outputAmount = amount;

		this.ingredients.forEach((pair) -> {
			ItemStack stack = pair.getFirst();
			int level = pair.getSecond();

			Ingredient ingredient = Ingredients.getIngredientByItem(stack.getItem());
			if(ingredient != null){
				float newCount = this.outputAmount;
				newCount *= ingredient.getBaseAmount();
				newCount *= Math.pow(2, level - 1);
				newCount *= this.rand;
				newCount *= CookingRecipe.RARITY_DISCOUNT[this.rarity];

				stack.setCount(Math.max((int) Math.ceil(newCount), 1));
			}
		});
	}

	public static CompoundNBT write(CookingRecipe recipe){
		CompoundNBT nbt = new CompoundNBT();

		nbt.putString(NBT_KEY.PATTERN, recipe.pattern.getName());
		nbt.putInt(NBT_KEY.RARITY, recipe.rarity);
		nbt.putFloat(NBT_KEY.RAND, recipe.rand);
		nbt.putInt(NBT_KEY.OUTPUT_AMOUNT, recipe.outputAmount);
		nbt.putString(NBT_KEY.DISPLAY_NAME, recipe.recipeName);
		nbt.putString(NBT_KEY.ID, recipe.ID);
		nbt.putBoolean(NBT_KEY.IS_INIT, recipe.isInitialized);

		ListNBT listNBT = new ListNBT();
		recipe.ingredients.forEach((pair) -> {
			CompoundNBT element = new CompoundNBT();

			ItemStack stack = pair.getFirst();
			int level = pair.getSecond();

			element.put(NBT_KEY.STACK, stack.write(new CompoundNBT()));
			element.putInt(NBT_KEY.LEVEL, level);

			listNBT.add(element);
		});
		nbt.put(NBT_KEY.INGREDIENTS, listNBT);

		return nbt;
	}

	@Nullable
	public static CookingRecipe read(CompoundNBT nbt){
		if(nbt.contains(NBT_KEY.PATTERN) && nbt.contains(NBT_KEY.INGREDIENTS) && nbt.contains(NBT_KEY.ID)){
			CookingPattern pattern = CookingPatterns.getPatternByName(nbt.getString(NBT_KEY.PATTERN));
			if(pattern == null) return null;

			final CookingRecipe recipe = new CookingRecipe(pattern, nbt.getString(NBT_KEY.ID));

			ListNBT listNBT = (ListNBT) nbt.get(NBT_KEY.INGREDIENTS);
			listNBT.forEach((ele) -> {
				CompoundNBT element = (CompoundNBT) ele;
				if(element.contains(NBT_KEY.STACK) && element.contains(NBT_KEY.LEVEL)){
					ItemStack stack = ItemStack.read((CompoundNBT) element.get(NBT_KEY.STACK));
					int level = element.getInt(NBT_KEY.LEVEL);

					recipe.ingredients.add(new Pair<>(stack, level));
				}
			});

			recipe.outputAmount = nbt.contains(NBT_KEY.OUTPUT_AMOUNT) ? nbt.getInt(NBT_KEY.OUTPUT_AMOUNT) : 1;
			recipe.recipeName = nbt.contains(NBT_KEY.DISPLAY_NAME) ? nbt.getString(NBT_KEY.DISPLAY_NAME) : "";

			if(nbt.contains(NBT_KEY.IS_INIT) && nbt.getBoolean(NBT_KEY.IS_INIT)){
				recipe.initialize(nbt.contains(NBT_KEY.RARITY) ? nbt.getInt(NBT_KEY.RARITY) : 0
					, nbt.contains(NBT_KEY.RAND) ? nbt.getFloat(NBT_KEY.RAND) : 1.1f);
			}

			return recipe;
		}
		return null;
	}

	public CompoundNBT write(){
		return CookingRecipe.write(this);
	}

	public class StepRequest{
		private final KitchenAppliance equipment;
		private final List<ItemStackRequest> requires = new ArrayList<>();

		private StepRequest(KitchenAppliance equipmentIn){
			this.equipment = equipmentIn;
		}

		private void addSampleAsRequest(ItemStack sample){
			Predicate<ItemStack> predicator = (stack) -> {
				return stack.isItemEqualIgnoreDurability(sample);
			};

			if(sample.getItem() instanceof CookedFood){
				predicator = predicator.and((stack) -> {
					return CookedFood.isMatchedID(stack, CookingRecipe.this.ID);
				});
			}

			this.requires.add(new ItemStackRequest(predicator, sample.getCount()));
		}

		public List<ItemStackRequest> getRequires(){
			return new ArrayList<>(this.requires);
		}

		public KitchenAppliance getEquipment(){
			return this.equipment;
		}

		public String getRequestID(){
			return CookingRecipe.this.ID;
		}

		public boolean isSatisfied(List<ItemStack> provides){
			// TODO Is this still needed?
			return CookingRecipe.this.ingredients.stream().filter((pair) -> {
				ItemStack request = pair.getFirst();

				return provides.stream().filter((provide) -> {
					boolean valid = provide.isItemEqual(request) && request.getCount() <= provide.getCount();
					if(valid && request.getItem() instanceof CookedFood){
						valid = CookedFood.isMatchedID(provide, CookingRecipe.this.ID);
					}
					return valid; // Provide stack can satisfy the request.
				}).count() == 0; // Request not satisfied.
			}).count() == 0; // No request is not satisfied => Satisfied.
		}
	}

	public static class ItemStackRequest{
		public static final ItemStackRequest EMPTY = new ItemStackRequest((stack) -> {
			return true;
		}, 0);

		private final Predicate<ItemStack> satisfyPredicator;
		private final Predicate<ItemStack> matchPredicator;

		private ItemStackRequest(Predicate<ItemStack> matchPredicatorIn, int satisfiedCountIn){
			this(matchPredicatorIn, matchPredicatorIn.and(ItemStackRequest.COUNT_PREDICATOR(satisfiedCountIn)));
		}

		private ItemStackRequest(Predicate<ItemStack> matchPredicatorIn, Predicate<ItemStack> satisfyPredicatorIn){
			this.matchPredicator = matchPredicatorIn;
			this.satisfyPredicator = satisfyPredicatorIn;
		}

		// Return true when provide stack can be part of the request.
		public boolean isMatched(ItemStack provide){
			return this.matchPredicator.test(provide);
		}

		// Return true when provide stack can satisfied the request.
		public boolean isSatisfied(ItemStack provide){
			return this.satisfyPredicator.test(provide);
		}

		public static Predicate<ItemStack> COUNT_PREDICATOR(int satisfiedCount){
			return (stack) -> {
				return stack.getCount() >= satisfiedCount;
			};
		}
	}
}