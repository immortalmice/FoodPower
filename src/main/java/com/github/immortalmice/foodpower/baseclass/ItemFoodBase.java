package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;

import com.github.immortalmice.foodpower.lists.other.OtherItems;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class ItemFoodBase extends ItemFood{
	public ItemFoodBase(String name, int amount, float saturation){
		/** Can wolf eat my sweetie? NO WAY!*/
		super(amount, saturation, false);
		
		this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(FPCreativeTabs.ITEM_TAB);
	}

	/** Three Second Rule! */
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem){
		if(this.getRegistryName().toString() != "dirty_food"){
			NBTTagCompound compound = new NBTTagCompound();
			entityItem.writeEntityToNBT(compound);
			if(compound.getShort("Age") >= 20 * 3){
				entityItem.setItem(new ItemStack(OtherItems.DIRTY_FOOD, entityItem.getItem().getCount()));
			}
		}
		return false;
	}
}