package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.github.immortalmice.foodpower.lists.other.OtherBlocks;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class BlockBase extends Block{
	public BlockBase(String name, Material material, boolean addToOtherList){
		super(material);

		this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(FPCreativeTabs.BLOCK_TAB);

        if(addToOtherList)
        	OtherBlocks.list.add(this);
	}
	/** Constructor Overload, Add To Other List If Not Specify */
	public BlockBase(String name, Material material){
		this(name, material, true);
	}
}