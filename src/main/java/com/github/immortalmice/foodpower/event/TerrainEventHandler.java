package com.github.immortalmice.foodpower.event;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Biomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import com.github.immortalmice.foodpower.lists.Trees;

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
			if (rand.nextDouble() > 0.5) return;

			World world = event.getWorld();
			BlockPos pos = event.getChunkPos().getBlock(0, 0, 0);
			if(pos == null){
				System.out.println("Skipped a null position");
				return;
			}

			Biome biome = world.getBiomeForCoordsBody(pos);

			int x = rand.nextInt(16) + 8;
			int y = rand.nextInt(16) + 8;

			BlockPos bp = world.getHeight(pos.add(x, 0, y));
			/** Select Random Tree in Mod */
			int treeType = rand.nextInt(Trees.saplingBushList.size());
			Trees.saplingBushList.get(treeType).generateTree(world, bp, rand);

			event.setResult(Event.Result.DENY);
		}
	}
}