package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.item.Item;
import net.minecraft.item.Food;

import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class ItemFoodBase extends ItemBase{
	public ItemFoodBase(String name, int hunger, float saturation){
		/** Can wolf eat my sweetie? NO WAY!*/
		super(name, new Item.Properties()
			.group(FPCreativeTabs.ITEM_TAB)
			.food(new Food.Builder().saturation(saturation).hunger(hunger).build()));
	}

	/** Three Second Rule! */
	/*
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
	*/
}