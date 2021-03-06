package com.github.immortalmice.foodpower.food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import com.github.immortalmice.foodpower.cooking.CookingPattern;
import com.github.immortalmice.foodpower.cooking.CookingRecipe;
import com.github.immortalmice.foodpower.handlers.ConfigHandler;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.FlavorTypes;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.types.FlavorType;
import com.github.immortalmice.foodpower.util.TooltipUtil;
import com.mojang.datafixers.util.Pair;

/* The final product you get! It will give you power! */
public class Meal extends CookedFood{
	public Meal(){
		super();
	}

	/* create a ItemStack and set NBTTags with given RecipeScroll */
	public static ItemStack create(ItemStack scrollIn){
        CookingRecipe recipe = RecipeScroll.readCookingRecipe(scrollIn);
		if(recipe != null){
			CookingPattern pattern = recipe.getPattern();
			ItemStack result = new ItemStack(pattern.getResult(), recipe.getOutputAmount());

			CompoundNBT mealNBT = new CompoundNBT();
			mealNBT.putString("pattern", pattern.getName());
			mealNBT.putString("displayName", recipe.getDisplayName().getUnformattedComponentText());
			
			List<Pair<ItemStack, Integer>> ingredients = recipe.getIngredients();
			ListNBT listNBT = new ListNBT();
			ingredients.forEach(pair -> {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putString("name", pair.getFirst().getItem().getRegistryName().toString());
				nbt.putInt("level", pair.getSecond());
				listNBT.add(nbt);
			});
			mealNBT.put("ingredients", listNBT);
			
			result.setTag(mealNBT);

            Meal.calculateAndAssignExpPoints(result);

			return result;
		}
		return ItemStack.EMPTY;
	}

    /* Get the level of specific ingredient in this meal, return 0 when ingredient not exist in this meal */
    public static int getIngredientLevel(ItemStack meal, Ingredient ingredient){
        if(!(meal.getItem() instanceof Meal)) return 0;

        ListNBT nbt = Meal.getIngredientsListNBT(meal);

        return Meal.getIngredientLevel(nbt, ingredient);
    }

    public static int getIngredientLevel(ListNBT nbt, Ingredient ingredient){
        for(int i = 0; i <= nbt.size()-1; i ++){
            CompoundNBT element = nbt.getCompound(i);
            
            Ingredient ingredientResult = Ingredients.getIngredientByName(element.getString("name"));
            if(ingredientResult != null && ingredientResult.isEqual(ingredient)){
                return element.getInt("level");
            }
        }
        return 0;
    }

    public static List<Ingredient> getIngredients(ItemStack meal){
        if(!(meal.getItem() instanceof Meal)) return new ArrayList<Ingredient>();

        ListNBT nbt = Meal.getIngredientsListNBT(meal);
        return Meal.getIngredients(nbt);
    }

    public static List<Ingredient> getIngredients(ListNBT nbt){
        List<Ingredient> list = new ArrayList<Ingredient>();

        for(int i = 0; i <= nbt.size()-1; i ++){
            CompoundNBT element = nbt.getCompound(i);
            Ingredient ingredient = Ingredients.getIngredientByName(element.getString("name"));
            if(ingredient != null)
                list.add(ingredient);
        }
        return list;
    }

    public static ListNBT getIngredientsListNBT(ItemStack meal){
        if(meal.hasTag()){
            CompoundNBT nbt = meal.getTag();
            if(nbt.contains("ingredients")){
                return (ListNBT) nbt.get("ingredients");
            }
        }
        return new ListNBT();
    }

	@Override
	public ItemStack onItemUseFinish(ItemStack stackIn, World worldIn, LivingEntity entityLiving){
        if(worldIn.isRemote) return stackIn;

        /* Give effect to player when eaten */
		ItemStack stack = entityLiving.onFoodEaten(worldIn, stackIn);

        ListNBT ingredientNBT = Meal.getIngredientsListNBT(stackIn);
        List<Ingredient> ingredientList = Meal.getIngredients(ingredientNBT);
        MealEffectContainer container = new MealEffectContainer(stack, worldIn, entityLiving);

        int levelSum = 0;
        for(Ingredient ingredient : ingredientList){
        	Boolean isDisabled = ConfigHandler.SERVER.disables.get(ingredient.asItem().getRegistryName().getPath());
        	if(isDisabled == null || !isDisabled) {
        		int level = Meal.getIngredientLevel(ingredientNBT, ingredient);
                ingredient.applyMealEffect(container, level);
                levelSum += level;
        	}
        }

        container.setHunger(levelSum).setSaturation(1.2f);
        container.apply();

        /* Give pattern exp to player when eaten */
        Pair<String, Integer> patternExp = Meal.getPatternExp(stackIn);
        if(patternExp != null){
            CookingPattern pattern = CookingPatterns.getPatternByName(patternExp.getFirst());
            if(pattern != null){
                entityLiving.getCapability(Capabilities.PATTERN_EXP_CAPABILITY, null).ifPresent((capability) -> {
                    capability.addExp(pattern, patternExp.getSecond());
                });
            }
        }

        /* Give flavor exp to player when eaten */
        Map<String, Integer> flavorExp = Meal.getFlavorExp(stackIn);

        if(flavorExp.size() > 0){
            entityLiving.getCapability(Capabilities.FLAVOR_EXP_CAPABILITY, null).ifPresent((capability) -> {
                for(String key : flavorExp.keySet()){
                    FlavorType flavor = FlavorTypes.getFlavorByName(key);
                    if(flavor != null){
                        capability.addExp(flavor, flavorExp.get(key));
                    }
                }
            });
        }

		return stack;
	}

    @Override
    public int getItemStackLimit(ItemStack stack){
        return 64;
    }

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    	super.addInformation(stack, worldIn, tooltip, flagIn);
        TooltipUtil tooltipHelper = new TooltipUtil(tooltip);
        
    	String patternStr = TooltipUtil.translate("general.foodpower.meal") + " : ";
    	CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();
    	if(nbt.contains("pattern")){
    		patternStr += TooltipUtil.translate("pattern.foodpower." + nbt.getString("pattern"));
    	}else{
    		patternStr += TooltipUtil.translate("general.foodpower.none");
    	}
    	tooltipHelper.add(patternStr);

        /* Whether player press down shift key or not */
        boolean moreInfo = Screen.hasShiftDown();
        if(!moreInfo){
            tooltipHelper.newBlankRow();
            tooltipHelper.addTranslate("message.foodpower.tooltip_more_info"
                , (new Style()).setItalic(true).setColor(TextFormatting.GRAY));
            return;
        }

        ListNBT ingredientNBT = Meal.getIngredientsListNBT(stack);
        List<Ingredient> ingredientList = Meal.getIngredients(ingredientNBT);
    	if(!ingredientList.isEmpty()){
            tooltipHelper.newBlankRow();
    		tooltipHelper.addTranslate("general.foodpower.ingredients");
    		for(Ingredient ingredient : ingredientList){
    			String ingredientStr = TooltipUtil.translate(ingredient.asItem().getTranslationKey());
    			ingredientStr += " [" + TooltipUtil.translate("general.foodpower.level") + Meal.getIngredientLevel(ingredientNBT, ingredient) + "]";
    			tooltipHelper.addWithLeftSpace(ingredientStr);
    		}
    	}

        Pair<String, Integer> patternExp = Meal.getPatternExp(stack);
        boolean hasPatternExp = patternExp != null;

        Map<String, Integer> flavorExp = Meal.getFlavorExp(stack);
        boolean hasFlavorExp =  flavorExp.size() > 0;
        
        if(hasPatternExp || hasFlavorExp){
            tooltipHelper.newBlankRow();
            tooltipHelper.addTranslate("message.foodpower.tooltip_exp_title");
        }

        if(hasPatternExp){
            tooltipHelper.addWithLeftSpace(TooltipUtil.translate("pattern.foodpower." + patternExp.getFirst()) + " : " + patternExp.getSecond());
        }

        if(hasFlavorExp){
            for(String key : flavorExp.keySet()){
                tooltipHelper.addWithLeftSpace(TooltipUtil.translate("flavor_type.foodpower." + key) + " : " + flavorExp.get(key));
            }
        }
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack){
    	return new StringTextComponent(this.formateDisplayName(stack));
    }

    private String formateDisplayName(ItemStack stack){
        if(stack.hasTag()
            && stack.getTag().contains("displayName") 
            && !stack.getTag().getString("displayName").isEmpty()){

            return stack.getTag().getString("displayName");
        }
        return I18n.format("item.foodpower." + this.getRegistryName().getPath());
    }

    @Nullable
    private static Pair<String, Integer> getPatternExp(ItemStack stack){
        if(stack.hasTag() && stack.getTag().contains("pattern_exp")){
            CompoundNBT nbt = (CompoundNBT)stack.getTag().get("pattern_exp");
            String[] keys = nbt.keySet().toArray(new String[0]);
            Pair<String, Integer> pair = new Pair<String, Integer>(keys[0], nbt.getInt(keys[0]));
            return pair;
        }
        return null;
    }

    public static Map<String, Integer> getFlavorExp(ItemStack stack){
        Map<String, Integer> map = new HashMap<>();

        if(stack.hasTag() && stack.getTag().contains("flavor_exp")){
            CompoundNBT nbt = (CompoundNBT)stack.getTag().get("flavor_exp");
            String[] keys = nbt.keySet().toArray(new String[0]);
            for(String key : keys){
                map.put(key, nbt.getInt(key));
            }
        }
        return map;
    }

    /* Calculate Pattern & Flavor Exp And Assign To NBT */
    private static void calculateAndAssignExpPoints(ItemStack stack){
        ListNBT ingredientNBT = Meal.getIngredientsListNBT(stack);
        List<Ingredient> ingredientList = Meal.getIngredients(ingredientNBT);
        int patternPoint = 0;
        Map<FlavorType, Integer> flavorExpMap = new HashMap<>();
        for(Ingredient ingredient : ingredientList){
            int level = Meal.getIngredientLevel(ingredientNBT, ingredient);
            /* Pattern Exp Point */
            patternPoint += 10 * level;
            /* Flavor Exp Point */
            FlavorType flavor = ingredient.getFlavorType();
            if(flavor.equals(FlavorTypes.NONE)) continue;

            int currentFlavorPoint = flavorExpMap.containsKey(flavor) ? flavorExpMap.get(flavor) : 0;
            flavorExpMap.put(flavor, currentFlavorPoint + 10 * level);
        }
        /* Filter Opposite Flavor */
        flavorExpMap.entrySet().removeIf((entry) -> {
            FlavorType flavor = entry.getKey();
            return flavorExpMap.containsKey(flavor.getOppositeFlavor())
                && flavorExpMap.get(flavor.getOppositeFlavor()) >= flavorExpMap.get(flavor);
        });

        if(stack.hasTag()){
            CompoundNBT nbt = stack.getTag();
            if(nbt.contains("pattern") && patternPoint != 0){
                CompoundNBT patternExpNBT = new CompoundNBT();
                patternExpNBT.putInt(nbt.getString("pattern"), patternPoint);
                nbt.put("pattern_exp", patternExpNBT);
            }

            if(flavorExpMap.size() > 0){
                CompoundNBT flavorExpNBT = new CompoundNBT();
                for(FlavorType flavor : flavorExpMap.keySet()){
                    flavorExpNBT.putInt(flavor.getName(), flavorExpMap.get(flavor));
                }
                nbt.put("flavor_exp", flavorExpNBT);
            }
        }
    }

    public static class MealEffectContainer{
        private final ItemStack stack;
        private final World world;
        private final LivingEntity entityLiving;

        private int hunger = 0;
        private float saturation = 0.2f;

        private List<EffectInstance> effectInstances = new ArrayList<EffectInstance>();
        private List<Float> effectDurationMultipliers = new ArrayList<Float>();
        private List<Integer> extraEffectDurationTicks = new ArrayList<Integer>();
        private List<Float> notConsumMealChances = new ArrayList<Float>();
        private List<Float> hungerMultipliers = new ArrayList<Float>();
        private List<Integer> extraHungerPoints = new ArrayList<Integer>();
        private List<Runnable> extraBehaviors = new ArrayList<Runnable>();

        private MealEffectContainer(ItemStack stackIn, World worldIn, LivingEntity entityLivingIn){
            this.stack = stackIn;
            this.world = worldIn;
            this.entityLiving = entityLivingIn;
        }

        private MealEffectContainer setHunger(int hungerIn){
            this.hunger = hungerIn;
            return this;
        }

        private MealEffectContainer setSaturation(float saturationIn){
            this.saturation = saturationIn;
            return this;
        }
        
        public MealEffectContainer addDefaultEffectInstance(Effect effectIn, int level){
        	this.addEffectInstance(new EffectInstance(effectIn, (level - 1) * 1500 + 600, level - 1));
        	return this;
        }

        public MealEffectContainer addEffectInstance(EffectInstance effectInstanceIn){
            this.effectInstances.add(effectInstanceIn);
            return this;
        }

        public MealEffectContainer addEffectDuationMultiplier(float multiplierIn){
            this.effectDurationMultipliers.add(multiplierIn);
            return this;
        }

        public MealEffectContainer addExtraEffectDurationTick(int tickIn){
            this.extraEffectDurationTicks.add(tickIn);
            return this;
        }

        public MealEffectContainer addNotConsumMealChance(float chanceIn){
            this.notConsumMealChances.add(chanceIn);
            return this;
        }

        public MealEffectContainer addHungerMultiplier(float multiplierIn){
            this.hungerMultipliers.add(multiplierIn);
            return this;
        }

        public MealEffectContainer addExtraHungerPoints(int pointsIn){
            this.extraHungerPoints.add(pointsIn);
            return this;
        }

        /* You can use getItemStack, getWorld, getEntityLiving to do something else */
        public MealEffectContainer addExtraBehavior(Runnable runnableIn){
            this.extraBehaviors.add(runnableIn);
            return this;
        }

        public ItemStack getItemStack(){return this.stack;}
        public World getWorld(){return this.world;}
        public LivingEntity getEntityLiving(){return this.entityLiving;}

        private void apply(){
            List<EffectInstance> effectsToApply = new ArrayList<EffectInstance>();

            /* Apply effects to entity */
            for(EffectInstance effect : this.effectInstances){
                Effect potion = effect.getPotion();
                int amplifier = effect.getAmplifier();
                int duration = effect.getDuration();

                for(Float multiplier : this.effectDurationMultipliers){
                    duration *= multiplier;
                }

                for(int extraTick : this.extraEffectDurationTicks){
                    duration += extraTick;
                }

                effectsToApply.add(new EffectInstance(potion, duration, amplifier));
            }
            for(EffectInstance effect : effectsToApply){
                this.entityLiving.addPotionEffect(effect);
            }

            /* Chance not consum item */
            if(!(this.entityLiving instanceof PlayerEntity) || !((PlayerEntity)this.entityLiving).abilities.isCreativeMode){
                Float finalChance = 0.0f;
                for(Float chance : this.notConsumMealChances){
                    finalChance += chance;
                }
                if(this.world.rand.nextFloat() <= finalChance){
                    this.stack.shrink(-1);
                }
            }

            /* Add hunger & staturation to player */
            if(this.entityLiving instanceof PlayerEntity){
                for(float multiplier : this.hungerMultipliers){
                    this.hunger *= multiplier;
                }
                for(int point : this.extraHungerPoints){
                    this.hunger += point;
                }
                ((PlayerEntity)this.entityLiving).getFoodStats().addStats(this.hunger, this.saturation);
            }

            /* Some extra behavior */
            for(Runnable runnable : this.extraBehaviors){
                runnable.run();
            }
        }
    }
}