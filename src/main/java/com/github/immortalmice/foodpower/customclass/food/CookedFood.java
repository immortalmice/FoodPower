package com.github.immortalmice.foodpower.customclass.food;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.OtherItems;

public class CookedFood extends Ingredient{
	/* For a empty CookedFood */
	public CookedFood(String name){
		super(name);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    	return;
    }

    @Override
    public int getItemStackLimit(ItemStack stack){
    	return 1;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem){
        if(this.getRegistryName().toString() != "dirty_food"){
            CompoundNBT compound = new CompoundNBT();
            entityItem.writeAdditional(compound);
            if(compound.getShort("Age") >= 20 * 3){
                entityItem.setItem(new ItemStack(OtherItems.DIRTY_FOOD, entityItem.getItem().getCount()));
            }
        }
        return false;
    }
}