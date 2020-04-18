package com.github.immortalmice.foodpower.customclass.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.github.immortalmice.foodpower.baseclass.EffectBase;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class FoodEffect extends EffectBase{
	private static final String POSTFIX = "_power";
	private String ingredientName, fpName;

	public FoodEffect(String ingredientNameIn, int liquidColorIn){
		super(FoodEffect.getFPNameByIngredientName(ingredientNameIn), EffectType.BENEFICIAL, liquidColorIn);

		this.ingredientName = ingredientNameIn;
		this.fpName = FoodEffect.getFPNameByIngredientName(this.ingredientName);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, int x, int y, float z){
		/* Cover original icon */
		Minecraft minecraft = gui.getMinecraft();
		minecraft.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
		gui.blit(x + 6, y, 6, 166, 18, 32);

		/* Show custom icon */
		ItemRenderer itemRenderer = minecraft.getItemRenderer();
		itemRenderer.zLevel = z;
		itemRenderer.renderItemAndEffectIntoGUI(new ItemStack((Ingredients.getIngredientByName(this.getIngredientName())).asItem())
			, x + 6, y + 7);
	}

	public EffectInstance getEffectInstance(int durationIn, int amplifierIn){
		return new EffectInstance(this, durationIn, amplifierIn, false, false);
	}

	public String getIngredientName(){
		return this.ingredientName;
	}

	public String getFPName(){
		return this.fpName;
	}

	public static String getFPNameByIngredientName(String str){
		return str + FoodEffect.POSTFIX;
	}
}