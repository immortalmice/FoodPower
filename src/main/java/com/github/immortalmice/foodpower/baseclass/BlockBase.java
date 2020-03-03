package com.github.immortalmice.foodpower.baseclass;

import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;

import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class BlockBase extends HorizontalBlock{
	private String name;
	private BlockItem item;

	public BlockBase(String nameIn, Block.Properties propertiesIn){
		super(propertiesIn);

		this.name = nameIn;
        item = new BlockItem(this, new Item.Properties().group(FPCreativeTabs.BLOCK_TAB));
	}

	public BlockBase(String nameIn, Material material){
		this(nameIn, Block.Properties.create(material));
	}

	public BlockItem getBlockItem(){
		return this.item;
	}

	public String getFPName(){
		return this.name;
	}
}