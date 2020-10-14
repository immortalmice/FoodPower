package com.github.immortalmice.foodpower.customclass.food;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.text.ITextComponent;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.customclass.cooking.ICookingElement;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;

public class CookedFood extends ItemFoodBase implements ICookingElement{
    public CookedFood(){
        super(0, 0.0f, false);
    }

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
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

	public static boolean isMatchedID(ItemStack provide, String id, int outputAmount){
        // TODO
		return true;
	}

	public static ItemStack getItemStack(CookedFood result, int outputAmount){
		// TODO
		return new ItemStack(result);
	}
}