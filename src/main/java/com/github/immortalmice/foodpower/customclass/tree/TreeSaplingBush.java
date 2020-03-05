package com.github.immortalmice.foodpower.customclass.tree;

import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import com.github.immortalmice.foodpower.customclass.tree.FPTree;

public class TreeSaplingBush extends SaplingBlock{
    private String fpName;

	public TreeSaplingBush(String nameIn, FPTree treeIn){
        super(treeIn, Block.Properties.create(Material.PLANTS)
            .hardnessAndResistance(0.0f)
            .tickRandomly()
            .sound(SoundType.PLANT));

		this.fpName = nameIn;
	}

    public String getFPName(){
        return this.fpName;
    }
}