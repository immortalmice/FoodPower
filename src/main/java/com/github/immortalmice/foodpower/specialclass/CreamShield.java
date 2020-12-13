package com.github.immortalmice.foodpower.specialclass;

import com.github.immortalmice.foodpower.lists.FPCreativeTabs;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

public class CreamShield extends ShieldItem {
	public CreamShield() {
		super(new Item.Properties().maxDamage(220).group(FPCreativeTabs.ITEM_TAB));
	}

	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity) {
		return super.isShield(stack, entity) || entity.getActiveItemStack().getItem() == Items.CREAM_SHIELD;
	}
}
