package com.github.immortalmice.foodpower.customclass.tree;

import java.util.Random;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraft.init.Blocks;

import com.github.immortalmice.foodpower.customclass.tree.TreeLeave;
import com.github.immortalmice.foodpower.customclass.tree.TreeGenerator;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class TreeSaplingBush extends SaplingBlock implements IGrowable{
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	private TreeLeave treeLeave;

	public TreeSaplingBush(String nameIn, Item dropItemIn){
		this.setTranslationKey(nameIn.concat("_sapling"));
        this.setRegistryName(nameIn.concat("_sapling"));

        this.setCreativeTab(FPCreativeTabs.ITEM_TAB);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);

        /** Add to sapling bush list, and regist it later */
        Trees.saplingBushList.add(this);

        /** Construct tree leave block */
        this.treeLeave = new TreeLeave(nameIn, this, dropItemIn);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient){
		return true;
	}

	/** Use random determine bonemeal use */
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state){
		return worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {STAGE});
    }

	@Override
    public IBlockState getStateFromMeta(int meta){
        return getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
    }

    @Override
    public int getMetaFromState(IBlockState state){
        int i = 0;
        i = i | state.getValue(STAGE).intValue() << 3;
        return i;
    }

    /** Update every tick, if light over the block higher than 9, and in chance of 1/8, call grow */
	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
        if (!worldIn.isRemote){
            super.updateTick(worldIn, pos, state, rand);

            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0){
                grow(worldIn, rand, pos, state);
            }
        }
    }

    /** STAGE is 0, go next value, else call generateTree */
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state){
		if(state.getValue(STAGE).intValue() == 0){
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        }else{
            generateTree(worldIn, pos, rand);
        }
	}

	public void generateTree(World worldIn, BlockPos pos, Random rand){
        if (!TerrainGen.saplingGrowTree(worldIn, rand, pos))return;
        WorldGenerator worldgenerator = new TreeGenerator(this.treeLeave);
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
        worldgenerator.generate(worldIn, rand, pos);
    }
}