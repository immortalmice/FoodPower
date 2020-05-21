package com.github.immortalmice.foodpower.customclass.tree;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraftforge.common.IPlantable;

public class FPTree extends Tree{
	private String name;
	private TreeSaplingBush sapling;
	private TreeLeave leave;
	private ConfiguredFeature<TreeFeatureConfig, ?> configuredFeature = null;

	public FPTree(String nameIn){
		this.name = nameIn;
	}

	public void setLeaveAndSapling(TreeLeave leaveIn, TreeSaplingBush saplingIn){
		this.leave = leaveIn;
		this.sapling = saplingIn;
	}

	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random rand, boolean boolIn){
		/* Only generate configured feature once */
		if(this.configuredFeature == null){
			this.configuredFeature = Feature.NORMAL_TREE.withConfiguration((
				new TreeFeatureConfig.Builder(
					new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState())
					, new SimpleBlockStateProvider(this.leave.getDefaultState())
					, new BlobFoliagePlacer(2, 0)))
			.baseHeight(4)//base height
			.heightRandA(2)//height rand a
			//.func_227355_c_()//height rand b
			//.func_227356_e_(2)//trunk height
			//.func_227357_f_(1)//trunk height random
			//.func_227358_g_()//trunk top offset
			//.func_227359_h_()//trunk top offset random
			.foliageHeight(3)//foliage height
			//.func_227361_j_()//foliage height random
			//.func_227362_k_()//max water depth
			.ignoreVines()//ignore vines
			.setSapling((IPlantable) this.sapling)
			.build());
		}
		return this.configuredFeature;
	}

	public ConfiguredFeature<TreeFeatureConfig, ?> getConfiguredFeature(){
		return this.getTreeFeature(new Random(), false);
	}

	public String getFPName(){
		return this.name;
	}
}