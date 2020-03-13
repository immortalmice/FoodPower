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
	protected ConfiguredFeature<TreeFeatureConfig, ?> func_225546_b_(Random rand, boolean boolIn){
		/* Only generate configured feature once */
		if(this.configuredFeature == null){
			this.configuredFeature = Feature.NORMAL_TREE.func_225566_b_((
				new TreeFeatureConfig.Builder(
					new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState())
					, new SimpleBlockStateProvider(this.leave.getDefaultState())
					, new BlobFoliagePlacer(2, 0)))
			.func_225569_d_(4)//base height
			.func_227354_b_(2)//height rand a
			//.func_227355_c_()//height rand b
			//.func_227356_e_(2)//trunk height
			//.func_227357_f_(1)//trunk height random
			//.func_227358_g_()//trunk top offset
			//.func_227359_h_()//trunk top offset random
			.func_227360_i_(3)//foliage height
			//.func_227361_j_()//foliage height random
			//.func_227362_k_()//max water depth
			.func_227352_a_()//ignore vines
			.setSapling((IPlantable) this.sapling)
			.func_225568_b_());
		}
		return this.configuredFeature;
	}

	public ConfiguredFeature<TreeFeatureConfig, ?> getConfiguredFeature(){
		return this.func_225546_b_(new Random(), false);
	}

	public String getFPName(){
		return this.name;
	}
}