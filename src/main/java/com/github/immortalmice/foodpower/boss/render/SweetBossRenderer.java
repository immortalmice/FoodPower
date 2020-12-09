package com.github.immortalmice.foodpower.boss.render;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.boss.entities.SweetBoss;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

public class SweetBossRenderer extends MobRenderer<SweetBoss, SweetBossRenderer.SweetBossModel> {
	public SweetBossRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new SweetBossModel(), 0.7f);
	}

	@Override
	public ResourceLocation getEntityTexture(SweetBoss entity) {
		return new ResourceLocation(FoodPower.MODID, "textures/entity/sweet_boss.png");
	}

	public static class SweetBossModel extends EntityModel<SweetBoss> {

		@Override
		public void setRotationAngles(SweetBoss entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
				float netHeadYaw, float headPitch) {

		}

		@Override
		public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
				float red, float green, float blue, float alpha) {

		}

	}
}
