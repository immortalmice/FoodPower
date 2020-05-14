package com.github.immortalmice.foodpower.customclass.food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.customclass.client.TooltipUtil;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.effect.FoodEffect;
import com.github.immortalmice.foodpower.customclass.flavor.FlavorType;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.FlavorTypes;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.mojang.datafixers.util.Pair;

/* The final product you get! It will give you power! */
public class Meal extends CookedFood{
	public Meal(String nameIn){
		super(nameIn);
	}

	/* create a ItemStack and set NBTTags with given RecipeScroll */
	public static ItemStack create(ItemStack scrollIn, int amount){
		CompoundNBT scrollNBT = scrollIn.getTag();
		if(scrollNBT != null && scrollNBT.contains("pattern")){
			Ingredient ingredient = Ingredients.getIngredientByName(scrollNBT.getString("pattern"));
			if(ingredient instanceof Meal){
				ItemStack result = new ItemStack(ingredient.asItem(), amount);

				CompoundNBT mealNBT = new CompoundNBT();
				mealNBT.putString("pattern", scrollNBT.getString("pattern"));
				mealNBT.putString("displayName", scrollNBT.getString("displayName"));
				mealNBT.put("ingredients", scrollNBT.get("ingredients").copy());
				result.setTag(mealNBT);

                Meal.calculateAndAssignExpPoints(result);

				return result;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stackIn, World worldIn, LivingEntity entityLiving){
        if(worldIn.isRemote) return stackIn;

        /* Give effect to player when eaten */
		ItemStack stack = entityLiving.onFoodEaten(worldIn, stackIn);
		if(stackIn.hasTag() && stackIn.getTag().contains("ingredients")){
			ListNBT list = (ListNBT)stackIn.getTag().get("ingredients");
    		for(int i = 0; i <= list.size()-1; i ++){
    			CompoundNBT element = (CompoundNBT) list.get(i);

    			FoodEffect effect = Ingredients.getIngredientByName(element.getString("name")).getEffect();
    			entityLiving.addPotionEffect(effect.getEffectInstance(1200, element.getInt("level") - 1));
    		}
		}

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

	@OnlyIn(Dist.CLIENT)
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

    	if(nbt.contains("ingredients")){
            tooltipHelper.newBlankRow();
    		tooltipHelper.addTranslate("general.foodpower.ingredients");
    		ListNBT list = (ListNBT)nbt.get("ingredients");
    		for(int i = 0; i <= list.size()-1; i ++){
    			CompoundNBT element = (CompoundNBT) list.get(i);
    			String ingredientStr = TooltipUtil.translate("item.foodpower." + element.getString("name"));
    			ingredientStr += " [" + TooltipUtil.translate("general.foodpower.level") + element.getInt("level") + "]";
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

	@OnlyIn(Dist.CLIENT)
    @Override
    public ITextComponent getDisplayName(ItemStack stack){
    	return new StringTextComponent(this.formateDisplayName(stack));
    }

    @OnlyIn(Dist.CLIENT)
    private String formateDisplayName(ItemStack stack){
        if(stack.hasTag()
            && stack.getTag().contains("displayName") 
            && !stack.getTag().getString("displayName").isEmpty()){

            return stack.getTag().getString("displayName");
        }
        return I18n.format("item.foodpower." + this.getFPName());
    }

    /* In general, this map should have size in 1 or 0 only */
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

    private static Map<String, Integer> getFlavorExp(ItemStack stack){
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
        if(stack.getItem() instanceof Meal && stack.hasTag() && stack.getTag().contains("ingredients")){
            CompoundNBT nbt = stack.getTag();
            ListNBT list = (ListNBT) nbt.get("ingredients");
            int patternPoint = 0;
            Map<FlavorType, Integer> flavorExpMap = new HashMap<>();
            for(int i = 0; i <= list.size()-1; i ++){
                CompoundNBT element = (CompoundNBT) list.get(i);
                if(element.contains("name")){
                    Ingredient ingredient = Ingredients.getIngredientByName(element.getString("name"));
                    if(!ingredient.isEmpty()){
                        int level = element.contains("level") ? element.getInt("level") : 0;
                        /* Pattern Exp Point */
                        patternPoint += 10 * level;
                        /* Flavor Exp Point */
                        FlavorType flavor = ingredient.getFlavorType();
                        if(flavor.equals(FlavorTypes.NONE)) continue;

                        int currentFlavorPoint = flavorExpMap.containsKey(flavor) ? flavorExpMap.get(flavor) : 0;
                        flavorExpMap.put(flavor, currentFlavorPoint + 10 * level);
                    }
                }
            }
            /* Filter Opposite Flavor */
            flavorExpMap.entrySet().removeIf((entry) -> {
                FlavorType flavor = entry.getKey();
                return flavorExpMap.containsKey(flavor.getOppositeFlavor())
                    && flavorExpMap.get(flavor.getOppositeFlavor()) >= flavorExpMap.get(flavor);
            });

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
        private List<EffectInstance> effectInstances = new ArrayList<EffectInstance>();
        private List<Float> effectDurationMultipliers = new ArrayList<Float>();
        private List<Integer> extraEffectDurationTicks = new ArrayList<Integer>();
        private List<Float> notConsumMealChances = new ArrayList<Float>();

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

        private void apply(ItemStack stackIn, World worldIn, LivingEntity entityLivingIn){

        }
    }
}