package com.github.immortalmice.foodpower.handlers;

import java.util.List;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.customclass.KitchenAppliance;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;

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
	}
}