package com.github.immortalmice.foodpower.boss.render;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.boss.entities.SaltyBoss;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

public class SaltyBossRenderer extends MobRenderer<SaltyBoss, SaltyBossRenderer.SaltyBossModel> {
	public SaltyBossRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new SaltyBossModel(), 0.7f);
	}

	@Override
	public ResourceLocation getEntityTexture(SaltyBoss entity) {
		return new ResourceLocation(FoodPower.MODID, "textures/entity/salty_boss.png");
	}
	
	public static class SaltyBossModel extends EntityModel<SaltyBoss> {
		@Override
		public void setRotationAngles(SaltyBoss entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
				float netHeadYaw, float headPitch) {
		}

		@Override
		public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
				float red, float green, float blue, float alpha) {
		}
	}
}
