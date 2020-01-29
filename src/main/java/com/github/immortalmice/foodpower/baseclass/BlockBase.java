package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class BlockBase extends Block{
	public BlockBase(String name, Material material){
		super(material);

		this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(FPCreativeTabs.BLOCK_TAB);
	}
}