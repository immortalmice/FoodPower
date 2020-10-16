package com.github.immortalmice.foodpower.handlers;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Collection;

import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.tree.FPTree;

/* Add Tree Features To Corresponding Biomes */
public class BiomeHandler{
	private static final FPTree[] TREES_IN_WARM = new FPTree[]{
		Trees.ORANGE, Trees.KIWI
	};
	private static final FPTree[] TREES_IN_HOT = new FPTree[]{
		Trees.PAPAYA, Trees.MANGO, Trees.LEMON
	};

	public static void setup(){
		Trees.setup();
		Collection<Biome> biomes = ForgeRegistries.BIOMES.getValues();
		for(Biome biome : biomes){
			switch(biome.getCategory()){
				case FOREST:
				case TAIGA:
				case PLAINS:
					for(FPTree tree : TREES_IN_WARM){
						biome.addFeature(Decoration.VEGETAL_DECORATION
							, tree.getConfiguredFeature()
								.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP
									.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
					}
					break;
				case JUNGLE:
				case SWAMP:
				case SAVANNA:
					for(FPTree tree : TREES_IN_HOT){
						biome.addFeature(Decoration.VEGETAL_DECORATION
							, tree.getConfiguredFeature()
								.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP
									.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
					}
					break;
				default:
			}
		}
	}
}