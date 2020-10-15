package com.github.immortalmice.foodpower.customclass.food;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.customclass.cooking.CookingRecipe.StepRequest;
import com.github.immortalmice.foodpower.customclass.client.TooltipUtil;
import com.github.immortalmice.foodpower.customclass.cooking.ICookingElement;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;

public class CookedFood extends ItemFoodBase implements ICookingElement{
    public CookedFood(){
        super(0, 0.0f, false);
    }

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
		CompoundNBT nbt = stack.getTag();
		if(nbt != null){
			TooltipUtil tooltipHelper = new TooltipUtil(tooltip);
			
			if(nbt.contains("id")){
				tooltipHelper.add("(" + nbt.getString("id") + ")", new Style().setItalic(true).setColor(TextFormatting.GRAY));
			}
			
			if(nbt.contains("output_amount")){
				tooltipHelper.newBlankRow();
				tooltipHelper.addTranslate("message.foodpower.output_amount", nbt.getInt("output_amount"));
			}
		}
    	return;
    }

    @Override
    public int getItemStackLimit(ItemStack stack){
    	return 1;
    }

    @Override
    public ICookingElement.ElementType getElementType(){
        return ICookingElement.ElementType.COOKED_FOOD;
    }

    /* 3 second rule! If you don't pickup in 3 seconds.....Eww */
    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem){
        if(this.getRegistryName().toString() != "foodpower:dirty_food"){
            CompoundNBT compound = new CompoundNBT();
            entityItem.writeAdditional(compound);
            if(compound.getShort("Age") >= 20 * 3){
                entityItem.setItem(new ItemStack(Items.DIRTY_FOOD, entityItem.getItem().getCount()));
            }
        }
        return false;
    }

	public static boolean isMatchedWith(ItemStack provide, String id, int outputAmount){
        // TODO
		return true;
	}

	public static ItemStack create(StepRequest stepRequest){
		ItemStack result = new ItemStack(stepRequest.getResult());
		
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("output_amount", stepRequest.getOutputAmount());
		nbt.putString("id", stepRequest.getRequestID());
		result.setTag(nbt);
		
		return result;
	}
}