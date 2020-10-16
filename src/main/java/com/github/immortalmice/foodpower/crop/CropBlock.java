package com.github.immortalmice.foodpower.crop;

import net.minecraft.block.SoundType;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.material.Material;


public class CropBlock extends CropsBlock{
	private String name;
	public CropBlock(String nameIn){
		super(Properties.create(Material.PLANTS)
			.hardnessAndResistance(0.0F)
			.doesNotBlockMovement()
			.tickRandomly()
			.sound(SoundType.CROP));

        this.name = nameIn;
	}

	public String getFPName(){
		return this.name;
	}
}