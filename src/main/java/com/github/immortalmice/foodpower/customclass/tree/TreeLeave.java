package com.github.immortalmice.foodpower.customclass.tree;


import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;

import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class TreeLeave extends LeavesBlock{
    private String fpName;
    private Item item;

	public TreeLeave(String nameIn){
        super(Block.Properties.create(Material.LEAVES)
            .hardnessAndResistance(0.2F)
            .sound(SoundType.PLANT)
            .tickRandomly()
            .variableOpacity());

        this.fpName = nameIn.concat("_leave");
        this.setRegistryName(this.getFPName());

        item = new BlockItem(this, new Item.Properties().group(FPCreativeTabs.BLOCK_TAB));

		Trees.leaveList.add(this);

        /* Regist it to game using DeferredRegister */
        Trees.REGISTER.register(this.getFPName(), () -> this);
	}

    public String getFPName(){
        return this.fpName;
    }

    @Override
    public String getTranslationKey(){
        return this.getFPName();
    }

    @Override
    public Item asItem(){
        return this.item;
    }
}