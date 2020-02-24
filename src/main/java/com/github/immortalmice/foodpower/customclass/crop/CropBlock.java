package com.github.immortalmice.foodpower.customclass.crop;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.lists.Crops;

public class CropBlock extends BlockCrops{
	private String name;
	public CropBlock(String nameIn, ItemFoodBase cropIn){
		super();

		this.setTranslationKey(nameIn.concat("_crop"));
        this.setRegistryName(nameIn.concat("_crop"));
        
        name = nameIn;

        Crops.blockList.add(this);
	}

	protected boolean canSustainBush(IBlockState state){
		return state.getBlock() == Blocks.FARMLAND;
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state){
		IBlockState soil = worldIn.getBlockState(pos.down());
		return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && soil.getBlock() == Blocks.FARMLAND;
	}

	protected Item getSeed(){
		return Item.REGISTRY.getObject(new ResourceLocation(FoodPower.MODID, name.concat("_seed")));
	}

	protected Item getCrop(){
		return Item.REGISTRY.getObject(new ResourceLocation(FoodPower.MODID, name));
	}
}