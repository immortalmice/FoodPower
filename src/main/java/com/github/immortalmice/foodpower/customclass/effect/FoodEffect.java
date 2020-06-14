package com.github.immortalmice.foodpower.customclass.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

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
/*	
	// I can't actually cover the original broken texture, so I disable the HUD display.
	// Need help.

	@Override
	public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha){
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
		gui.blit(x, y, 141, 166, 24, 24);

		ItemRenderer itemRenderer = minecraft.getItemRenderer();
		itemRenderer.zLevel = z;
		itemRenderer.renderItemAndEffectIntoGUI(new ItemStack((Ingredients.getIngredientByName(this.getIngredientName())).asItem())
			, x + 4, y + 4);
	}
*/
	@Override
	public boolean shouldRenderHUD(EffectInstance effect){
		return false;
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