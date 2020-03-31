package com.github.immortalmice.foodpower.customclass.food;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.customclass.client.TooltipUtil;
import com.github.immortalmice.foodpower.customclass.effect.FoodEffect;
import com.github.immortalmice.foodpower.customclass.food.CookedFood;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.Ingredients;

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
				ItemStack result = new ItemStack(ingredient, amount);

				CompoundNBT mealNBT = new CompoundNBT();
				mealNBT.putString("pattern", scrollNBT.getString("pattern"));
				mealNBT.putString("displayName", scrollNBT.getString("displayName"));
				mealNBT.put("ingredients", scrollNBT.get("ingredients").copy());
				result.setTag(mealNBT);

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
        int expPoint = Meal.calculatePatternExpPoints(stack);
        entityLiving.getCapability(Capabilities.EXP_CAPABILITY, null).ifPresent((capability) -> {
            capability.addExp(CookingPatterns.getPatternByName(this.getFPName()), expPoint);
        });

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

        int patternExp = Meal.calculatePatternExpPoints(stack);
        if(patternExp != 0 && nbt.contains("pattern")){
            tooltipHelper.newBlankRow();
            tooltipHelper.addTranslate("message.foodpower.tooltip_exp_title");
            tooltipHelper.addWithLeftSpace(TooltipUtil.translate("pattern.foodpower." + nbt.getString("pattern")) + " : " + patternExp);
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

    private static int calculatePatternExpPoints(ItemStack stack){
        int point = 0;
        if(stack.getItem() instanceof Meal){
            CompoundNBT mealNBT = stack.hasTag() ? stack.getTag() : new CompoundNBT();
            if(mealNBT.contains("ingredients")){
                ListNBT list = (ListNBT)mealNBT.get("ingredients");
                for(int i = 0; i <= list.size()-1; i ++){
                    CompoundNBT element = (CompoundNBT) list.get(i);
                    point += 10 * (element.contains("level") ? element.getInt("level") : 0);
                }
            }
        }
        return point;
    }
}