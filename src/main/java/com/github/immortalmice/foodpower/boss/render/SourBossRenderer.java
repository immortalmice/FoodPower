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
import net.minecraft.util.math.MathHelper;

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
		private final ModelRenderer body1;
		private final ModelRenderer body2;
		private final ModelRenderer body3;
		private final ModelRenderer body4;

		public SourBossModel() {
			textureWidth = 256;
			textureHeight = 256;

			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, 16.0F, 8.0F);
			head.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -16.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
			head.setTextureOffset(0, 52).addBox(-1.0F, -13.0F, -13.0F, 2.0F, 5.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(6, 52).addBox(-2.0F, -12.0F, -13.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(6, 52).addBox(1.0F, -12.0F, -13.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(6, 52).addBox(2.0F, -10.0F, -13.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(6, 52).addBox(-3.0F, -10.0F, -13.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(6, 52).addBox(-4.0F, -12.0F, -13.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(6, 52).addBox(4.0F, -9.0F, -13.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(6, 52).addBox(3.0F, -12.0F, -13.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(6, 53).addBox(-5.0F, -9.0F, -13.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

			body1 = new ModelRenderer(this);
			body1.setRotationPoint(0.0F, 18.5F, 13.0F);
			body1.setTextureOffset(0, 32).addBox(-5.0F, -5.5F, -5.0F, 10.0F, 11.0F, 10.0F, 0.0F, false);

			body2 = new ModelRenderer(this);
			body2.setRotationPoint(0.0F, 18.5F, 23.0F);
			body2.setTextureOffset(0, 32).addBox(-5.0F, -5.5F, -5.0F, 10.0F, 11.0F, 10.0F, 0.0F, false);

			body3 = new ModelRenderer(this);
			body3.setRotationPoint(0.0F, 18.5F, 33.0F);
			body3.setTextureOffset(0, 32).addBox(-5.0F, -5.5F, -5.0F, 10.0F, 11.0F, 10.0F, 0.0F, false);

			body4 = new ModelRenderer(this);
			body4.setRotationPoint(0.0F, 18.5F, 43.0F);
			body4.setTextureOffset(0, 32).addBox(-5.0F, -5.5F, -5.0F, 10.0F, 11.0F, 10.0F, 0.0F, false);
		}
	
		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			body1.render(matrixStack, buffer, packedLight, packedOverlay);
			body2.render(matrixStack, buffer, packedLight, packedOverlay);
			body3.render(matrixStack, buffer, packedLight, packedOverlay);
			body4.render(matrixStack, buffer, packedLight, packedOverlay);
		}
	
		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	
		@Override
		public void setRotationAngles(SourBoss entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		      this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		      this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		      this.body1.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		      this.body2.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		      this.body3.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		      this.body4.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		}
	}
}
