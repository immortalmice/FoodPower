package com.github.immortalmice.foodpower.handlers;

import java.util.List;

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

@OnlyIn(Dist.CLIENT)
public class RenderHandler{
	public static void setup(){
		RenderHandler.setupRenderLayer();
	}

	public static void setupRenderLayer(){
		List<KitchenAppliance> list = KitchenAppliances.getList();
		for(KitchenAppliance block : list){
			RenderTypeLookup.setRenderLayer(block, RenderType.func_228641_d_());
		}
		List<CropBlock> cropList = Crops.getBlockList();
		for(CropBlock block : cropList){
			RenderTypeLookup.setRenderLayer(block, RenderType.func_228641_d_());
		}
		List<TreeSaplingBush> saplingList = Trees.getSaplingList();
		for(TreeSaplingBush block : saplingList){
			RenderTypeLookup.setRenderLayer(block, RenderType.func_228641_d_());
		}
	}
}