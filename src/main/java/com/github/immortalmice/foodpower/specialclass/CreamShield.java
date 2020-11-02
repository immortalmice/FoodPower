package com.github.immortalmice.foodpower.specialclass;

import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;

public class CreamShield extends ShieldItem {
	public CreamShield() {
		super(new Item.Properties().maxDamage(220).group(FPCreativeTabs.ITEM_TAB));
	}
}
