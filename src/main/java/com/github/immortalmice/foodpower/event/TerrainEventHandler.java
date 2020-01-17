package com.github.immortalmice.foodpower.event;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Biomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import com.github.immortalmice.foodpower.lists.Trees;

public class TerrainEventHandler{
	public TerrainEventHandler(){
		MinecraftForge.TERRAIN_GEN_BUS.register(this);
	}
	/** Terrain Gen Events */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onDecorate(DecorateBiomeEvent.Decorate event){
		World world = event.getWorld();
		BlockPos pos = event.getPlacementPos();
		Biome biome = world.getBiomeForCoordsBody(pos);
		Random rand = event.getRand();

		System.out.print("Biome:");
		System.out.println(biome);

		/** Generate Mod Tree in Biome */
		if(biome == Biomes.FOREST){
			if (rand.nextDouble() > 0.1) return;
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