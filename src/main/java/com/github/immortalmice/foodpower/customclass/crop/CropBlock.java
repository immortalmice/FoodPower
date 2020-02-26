package com.github.immortalmice.foodpower.customclass.crop;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.customclass.crop.CropSeed;
import com.github.immortalmice.foodpower.lists.Crops;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class CropBlock extends CropsBlock{
	private static final float SECOND_SEED_CHANCE = 0.25f;
	private String name;
	private CropSeed seed;
	private Ingredient crop;
	public CropBlock(String nameIn, Ingredient cropIn){
		super(Properties.create(Material.PLANTS)
			.hardnessAndResistance(0.0F)
			.doesNotBlockMovement()
			.tickRandomly()
			.sound(SoundType.PLANT));

        this.name = nameIn.concat("_crop");
        this.seed = Crops.getSeed(nameIn.concat("_seed"));
        this.crop = Ingredients.getIngredientByName(nameIn);

        this.setRegistryName(nameIn.concat("_crop"));
        
        Crops.blockList.add(this);
        /** Regist it to game using DeferredRegister */
        Crops.BLOCK_REGISTER.register(this.getFPName(), () -> this);
	}

	@Override
	public String getTranslationKey(){
		return this.name;
	}

	public String getFPName(){
		return this.name;
	}
	
	private List<ItemStack> doDrop(BlockState stateIn, IWorld worldIn){
		List<ItemStack> dropList = new ArrayList<ItemStack>();
		int seedAmount = 1;
		if(this.isMaxAge(stateIn)){
			if(worldIn.getRandom().nextFloat() <= CropBlock.SECOND_SEED_CHANCE){
				seedAmount ++;
			}
			dropList.add(new ItemStack(this.crop));
		}
		dropList.add(new ItemStack(this.seed, seedAmount));

		return dropList;
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos posIn, BlockState stateIn){
		List<ItemStack> dropList = this.doDrop(stateIn, worldIn);
		for(int i = 0; i <= dropList.size()-1; i ++){
			worldIn.addEntity(new ItemEntity((World)worldIn, posIn.getX(), posIn.getY(), posIn.getZ(), dropList.get(i)));
		}
	}
}