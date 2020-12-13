package com.github.immortalmice.foodpower.boss.render;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.boss.entities.SweetBoss;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
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
		private final ModelRenderer left_foot;
		private final ModelRenderer right_foot;
		private final ModelRenderer body;
		private final ModelRenderer left_hand;
		private final ModelRenderer right_hand;
		private final ModelRenderer head;

		public SweetBossModel() {
			textureWidth = 64;
			textureHeight = 64;

			left_foot = new ModelRenderer(this);
			left_foot.setRotationPoint(3.0F, 12.0F, 0.0F);
			left_foot.setTextureOffset(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

			right_foot = new ModelRenderer(this);
			right_foot.setRotationPoint(-2.0F, 12.0F, 0.0F);
			right_foot.setTextureOffset(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

			body = new ModelRenderer(this);
			body.setRotationPoint(0.5F, 4.5F, 0.0F);
			body.setTextureOffset(0, 13).addBox(-4.5F, -7.5F, -2.0F, 9.0F, 15.0F, 4.0F, 0.0F, false);

			left_hand = new ModelRenderer(this);
			left_hand.setRotationPoint(5.0F, -3.0F, 0.0F);
			left_hand.setTextureOffset(26, 13).addBox(0.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

			right_hand = new ModelRenderer(this);
			right_hand.setRotationPoint(-4.0F, -3.0F, 0.0F);
			right_hand.setTextureOffset(26, 13).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

			head = new ModelRenderer(this);
			head.setRotationPoint(0.5F, -3.0F, 0.875F);
			head.setTextureOffset(0, 0).addBox(-2.5F, -6.0F, -2.875F, 5.0F, 6.0F, 4.0F, 0.0F, false);
			head.setTextureOffset(18, 0).addBox(-3.5F, -6.0F, -1.875F, 1.0F, 5.0F, 3.0F, 0.0F, false);
			head.setTextureOffset(18, 0).addBox(2.5F, -6.0F, -1.875F, 1.0F, 5.0F, 3.0F, 0.0F, false);
			head.setTextureOffset(26, 0).addBox(-2.5F, -6.0F, 1.125F, 5.0F, 12.0F, 1.0F, 0.0F, false);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		@Override
		public void setRotationAngles(SweetBoss entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
				float netHeadYaw, float headPitch) {


		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
			left_foot.render(matrixStack, buffer, packedLight, packedOverlay);
			right_foot.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			left_hand.render(matrixStack, buffer, packedLight, packedOverlay);
			right_hand.render(matrixStack, buffer, packedLight, packedOverlay);
			head.render(matrixStack, buffer, packedLight, packedOverlay);
		}	

	}
}
