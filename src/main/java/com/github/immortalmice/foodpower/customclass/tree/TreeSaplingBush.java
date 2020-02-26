package com.github.immortalmice.foodpower.customclass.tree;

import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import com.github.immortalmice.foodpower.customclass.tree.FPTree;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class TreeSaplingBush extends SaplingBlock{
    private String fpName;
    private Item item;

	public TreeSaplingBush(String nameIn, FPTree treeIn){
        super(treeIn, Block.Properties.create(Material.PLANTS)
            .hardnessAndResistance(0.0f)
            .tickRandomly()
            .sound(SoundType.PLANT));

		this.fpName = nameIn;
        this.setRegistryName(nameIn.concat("_sapling"));

        item = new BlockItem(this, new Item.Properties().group(FPCreativeTabs.BLOCK_TAB));

        Trees.saplingBushList.add(this);

        /* Regist it to game using DeferredRegister */
        Trees.REGISTER.register(this.getFPName(), () -> this);
	}

    public String getFPName(){
        return this.fpName;
    }

    @Override
    public Item asItem(){
        return this.item;
    }

    @Override
    public String getTranslationKey(){
        return this.getFPName();
    }
}