package com.github.immortalmice.foodpower.customclass;

import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;

import com.github.immortalmice.foodpower.baseclass.BlockRotatableBase;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;

public class KitchenAppliance extends BlockRotatableBase{
	public KitchenAppliance(String name){
		super(name, Material.IRON);

		/** How dare you use stone pickaxe break this holy things */
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(1.5f);
		this.setSoundType(SoundType.METAL);

        this.setCreativeTab(FPCreativeTabs.kitchenApplianceTab);

        /** Add to kitchen appliances list, and regist it later */
        KitchenAppliances.list.add(this);
	}
}