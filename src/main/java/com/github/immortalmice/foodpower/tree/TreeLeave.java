package com.github.immortalmice.foodpower.tree;


import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class TreeLeave extends LeavesBlock{
    private String fpName;

	public TreeLeave(String nameIn){
        super(Block.Properties.create(Material.LEAVES)
            .hardnessAndResistance(0.2F)
            .sound(SoundType.PLANT)
            .tickRandomly()
            .variableOpacity()
            .notSolid());//is not Soild

        this.fpName = nameIn;
	}

    public String getFPName(){
        return this.fpName;
    }
}