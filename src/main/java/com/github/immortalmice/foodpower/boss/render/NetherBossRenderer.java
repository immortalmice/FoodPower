package com.github.immortalmice.foodpower.boss.render;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.boss.entities.NetherBoss;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

public class NetherBossRenderer extends MobRenderer<NetherBoss, NetherBossRenderer.NetherBossModel> {
	public NetherBossRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new NetherBossModel(), 0.7f);
	}

	@Override
	public ResourceLocation getEntityTexture(NetherBoss entity) {
		return new ResourceLocation(FoodPower.MODID, "textures/entity/nether_boss.png");
	}
	
	public static class NetherBossModel extends EntityModel<NetherBoss> {

		@Override
		public void setRotationAngles(NetherBoss entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
				float netHeadYaw, float headPitch) {
		}

		@Override
		public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
				float red, float green, float blue, float alpha) {
		}

	}
}
