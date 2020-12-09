package com.github.immortalmice.foodpower.boss.render;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.boss.entities.EnderBoss;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

public class EnderBossRenderer extends MobRenderer<EnderBoss, EnderBossRenderer.EnderBossModel> {
	public EnderBossRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new EnderBossModel(), 0.7f);
	}

	@Override
	public ResourceLocation getEntityTexture(EnderBoss entity) {
		return new ResourceLocation(FoodPower.MODID, "textures/entity/ender_boss.png");
	}

	public static class EnderBossModel extends EntityModel<EnderBoss> {

		@Override
		public void setRotationAngles(EnderBoss entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
				float netHeadYaw, float headPitch) {
		}

		@Override
		public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
				float red, float green, float blue, float alpha) {
		}

	}
}
