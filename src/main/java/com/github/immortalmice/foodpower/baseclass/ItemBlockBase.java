package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.block.material.Material;

import com.github.immortalmice.foodpower.baseclass.BlockBase;
import com.github.immortalmice.foodpower.lists.other.OtherItemBlocks;

public class ItemBlockBase extends BlockBase{
	public ItemBlockBase(String name, Material material, boolean addToOtherList){
		super(name, material, false);

		if(addToOtherList)
			OtherItemBlocks.list.add(this);
	}
	/** Constructor Overload, Add To Other List If Not Specify */
	public ItemBlockBase(String name, Material material){
		this(name, material, true);
	}
}