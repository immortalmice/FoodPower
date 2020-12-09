package com.github.immortalmice.foodpower.boss.render;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.boss.entities.BitterBoss;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

public class BitterBossRenderer extends MobRenderer<BitterBoss, BitterBossRenderer.BitterBossModel> {
	public BitterBossRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BitterBossModel(), 0.7f);
	}
	
	@Override
	public ResourceLocation getEntityTexture(BitterBoss entity) {
		return new ResourceLocation(FoodPower.MODID, "textures/entity/bitter_boss.png");
	}

	public static class BitterBossModel extends EntityModel<BitterBoss> {

		@Override
		public void setRotationAngles(BitterBoss entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
				float netHeadYaw, float headPitch) {
		}

		@Override
		public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
				float red, float green, float blue, float alpha) {
		}

	}
}
