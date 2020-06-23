package com.github.immortalmice.foodpower.handlers;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.crop.CropBlock;
import com.github.immortalmice.foodpower.customclass.tree.TreeSaplingBush;
import com.github.immortalmice.foodpower.lists.Crops;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;
import com.github.immortalmice.foodpower.lists.Trees;

public class RenderHandler{
	public static void setup(){
		RenderHandler.setupBlockRenderLayer();
	}

	public static void setupBlockRenderLayer(){
		for(KitchenAppliance block : KitchenAppliances.list){
			RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
		}
		for(CropBlock block : Crops.blockList){
			RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
		}
		for(TreeSaplingBush block : Trees.saplingBushList){
			RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
		}
	}
}