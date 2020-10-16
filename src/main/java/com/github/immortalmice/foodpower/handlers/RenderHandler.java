package com.github.immortalmice.foodpower.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import com.github.immortalmice.foodpower.crop.CropBlock;
import com.github.immortalmice.foodpower.lists.Crops;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;
import com.github.immortalmice.foodpower.lists.OtherEntitys;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.specialclass.KitchenAppliance;
import com.github.immortalmice.foodpower.tree.TreeSaplingBush;

public class RenderHandler{
	public static void setupClient(){
		RenderHandler.setupBlockRenderLayer();
		RenderHandler.registEntityRenderer();
	}

	private static void setupBlockRenderLayer(){
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

	private static void registEntityRenderer(){
		RenderingRegistry.registerEntityRenderingHandler(OtherEntitys.EntityTypes.PAPAYA_SEED, RenderHandler.getSpriteIRenderFactory());
	}

	private static IRenderFactory<Entity> getSpriteIRenderFactory(){
		return new IRenderFactory<Entity>(){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public EntityRenderer<? super Entity> createRenderFor(EntityRendererManager manager){
				return new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer());
			}
		};
	}
}