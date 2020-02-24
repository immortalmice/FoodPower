package com.github.immortalmice.foodpower.event;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.customclass.tree.TreeSaplingBush;

public class TerrainEventHandler{
	public TerrainEventHandler(){
		MinecraftForge.TERRAIN_GEN_BUS.register(TerrainEventHandler.class);
	}
	/** Terrain Gen Events */
	@SubscribeEvent
	public static void onDecorate(DecorateBiomeEvent.Decorate event){
		/** Generate Mod Tree in Biome */
		if(event.getType() == DecorateBiomeEvent.Decorate.EventType.TREE){
			Random rand = event.getRand();
			if (rand.nextDouble() > 0.1) return;

			World world = event.getWorld();
			BlockPos pos = event.getChunkPos().getBlock(0, 0, 0);

			int x = rand.nextInt(16) + 8;
			int y = rand.nextInt(16) + 8;
			BlockPos posToGrow = world.getHeight(pos.add(x, 0, y));

			int biomeId = Biome.getIdForBiome(world.getBiomeForCoordsBody(posToGrow));

			/** I like mango, so it's default, but better not actually used */
			TreeSaplingBush selectedTreeType = Trees.MANGO;
			/** See ID at net.minecraft.world.biome.Biome */
			switch(biomeId){
				/** Some Warm Biomes */
				/** Forest */
				case 4:
				/** ForestHills */
				case 18:
				/** Birch Forest */
				case 27:
				/** Birch Forest Hills */
				case 28:
				/** Flower Forest */
				case 132:
				/** Birch Forest M */
				case 155:
				/** Birch Forest Hills M */
				case 156:
					switch(rand.nextInt(2)){
						case 0:
							selectedTreeType = Trees.ORANGE;
							break;
						case 1:
							selectedTreeType = Trees.KIWI;
							break;
					}
					break;

				/** Some Hot Biomes */
				/** Swampland */
				case 6:
				/** Jungle */
				case 21:
				/** JungleHills */
				case 22:
				/** Roofed Forest */
				case 29:
				/** Swampland M */
				case 134:
				/** Jungle M */
				case 149:
				/** Roofed Forest M */
				case 157:
					switch(rand.nextInt(3)){
						case 0:
							selectedTreeType = Trees.PAPAYA;
							break;
						case 1:
							selectedTreeType = Trees.MANGO;
							break;
						case 2:
							selectedTreeType = Trees.LEMON;
							break;
					}
					break;
				default:
					return;
			}
			selectedTreeType.generateTree(world, posToGrow, rand);

			event.setResult(Event.Result.DENY);
		}
	}
}