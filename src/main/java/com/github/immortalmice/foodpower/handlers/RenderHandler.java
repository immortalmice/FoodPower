package com.github.immortalmice.foodpower.handlers;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.customclass.crop.CropBlock;
import com.github.immortalmice.foodpower.customclass.tree.TreeSaplingBush;
import com.github.immortalmice.foodpower.lists.Crops;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;
import com.github.immortalmice.foodpower.lists.Trees;

public class RenderHandler{
	public static void setup(){
		RenderHandler.setupRenderLayer();
	}

	/* RenderType.func_228641_d_() : cutout_mipped */
	public static void setupRenderLayer(){
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