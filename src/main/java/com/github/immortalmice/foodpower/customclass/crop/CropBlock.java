package com.github.immortalmice.foodpower.customclass.crop;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.Item;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.IBlockReader;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.lists.Crops;

public class CropBlock extends CropsBlock{
	private String name;
	public CropBlock(String nameIn, ItemFoodBase cropIn){
		super(Properties.create(Material.PLANTS)
			.hardnessAndResistance(0.0F)
			.doesNotBlockMovement()
			.sound(SoundType.PLANT));

        this.name = nameIn;

        this.setRegistryName(nameIn.concat("_crop"));
        
        Crops.blockList.add(this);
	}

	@Override
	public String getTranslationKey(){
		return this.name.concat("_crop");
	}

	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos){
		return state.getBlock() == Blocks.FARMLAND;
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, BlockState state){
		BlockState soil = worldIn.getBlockState(pos.down());
		return (worldIn.getLight(pos) >= 8 || worldIn.func_226660_f_(pos)) && soil.getBlock() == Blocks.FARMLAND;
	}

	protected Item getSeed(){
		return Item.REGISTRY.getObject(new ResourceLocation(FoodPower.MODID, name.concat("_seed")));
	}

	protected Item getCrop(){
		return Item.REGISTRY.getObject(new ResourceLocation(FoodPower.MODID, name));
	}
}