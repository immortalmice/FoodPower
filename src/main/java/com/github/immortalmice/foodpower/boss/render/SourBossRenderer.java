package com.github.immortalmice.foodpower.boss.render;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.boss.entities.SourBoss;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class SourBossRenderer extends MobRenderer<SourBoss, SourBossRenderer.SourBossModel> {
	public SourBossRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new SourBossModel(), 0.7f);
	}

	@Override
	public ResourceLocation getEntityTexture(SourBoss entity) {
		return new ResourceLocation(FoodPower.MODID, "textures/entity/sour_boss.png");
	}
	

	public static class SourBossModel extends EntityModel<SourBoss> {
		private final ModelRenderer head;
		private final ModelRenderer body;
	
		public SourBossModel() {
			textureWidth = 64;
			textureHeight = 64;
	
			head = new ModelRenderer(this);
			head.setRotationPoint(-3.0F, 24.0F, -7.0F);
			head.setTextureOffset(0, 0).addBox(-1.0F, -8.0F, 3.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
	
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 24.0F, 0.0F);
			body.setTextureOffset(0, 16).addBox(-3.0F, -6.0F, 4.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
			body.setTextureOffset(0, 16).addBox(-3.0F, -6.0F, 10.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
			body.setTextureOffset(0, 16).addBox(-3.0F, -6.0F, 16.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
			body.setTextureOffset(0, 16).addBox(-3.0F, -6.0F, 22.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
			body.setTextureOffset(0, 16).addBox(-3.0F, -6.0F, 28.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
		}
	
		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
		}
	
		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	
		@Override
		public void setRotationAngles(SourBoss entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
				float netHeadYaw, float headPitch) {
			// TODO Auto-generated method stub
			
		}
	}
}
