package com.github.immortalmice.foodpower.container.chefcard;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.immortalmice.foodpower.baseclass.ScreenBase;
import com.github.immortalmice.foodpower.cooking.CookingPattern;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.lists.FlavorTypes;
import com.github.immortalmice.foodpower.types.FlavorType;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ChefCardScreen extends ScreenBase<ChefCardContainer> {
	private static final Map<FlavorType, Integer> FLAVOR_COLORS = new HashMap<>();
	private static final Map<CookingPattern, Integer> PATTERN_COLORS = new HashMap<>();
	
	static {
		FLAVOR_COLORS.put(FlavorTypes.SWEET, 0xFF55FF);
		FLAVOR_COLORS.put(FlavorTypes.SALTY, 0x00AAAA);
		FLAVOR_COLORS.put(FlavorTypes.NETHER, 0xAA0000);
		FLAVOR_COLORS.put(FlavorTypes.BITTER, 0x555555);
		FLAVOR_COLORS.put(FlavorTypes.SOUR, 0xFFFF55);
		FLAVOR_COLORS.put(FlavorTypes.ENDER, 0xAA00AA);
		
		PATTERN_COLORS.put(CookingPatterns.CAKE, 0xFFFFFF);
		PATTERN_COLORS.put(CookingPatterns.PIZZA, 0xFF5555);
		PATTERN_COLORS.put(CookingPatterns.SANDWICH, 0x55FF55);
		PATTERN_COLORS.put(CookingPatterns.ICE_CREAM, 0xFF55FF);
		PATTERN_COLORS.put(CookingPatterns.FRIED_RICE, 0xFFFF55);
		PATTERN_COLORS.put(CookingPatterns.NOODLE_SOUP, 0x00AAAA);
		PATTERN_COLORS.put(CookingPatterns.HONEY_TOAST, 0xFFAA00);
		PATTERN_COLORS.put(CookingPatterns.SALAD, 0x00AA00);
		PATTERN_COLORS.put(CookingPatterns.JUICE, 0x55FFFF);
	}
	
	private ResourceLocation skinLocation;
	
	public ChefCardScreen(ChefCardContainer containerIn, PlayerInventory inventoryIn, ITextComponent textIn) {
		super(containerIn, inventoryIn, textIn);
		
		this.textureFileName = "chef_card";
		this.xSize = 256;
		this.ySize = 128;

		if(this.playerInventory.player instanceof ClientPlayerEntity) {
			skinLocation = ((ClientPlayerEntity) this.playerInventory.player).getLocationSkin();
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		this.minecraft.getTextureManager().bindTexture(this.skinLocation);
		AbstractGui.blit(16, 40, 48, 48, 48, 48, 384, 384);
		
		int y = 0;
		for(Entry<FlavorType, Integer> entry : this.container.getFlavorExp().entrySet()) {
			this.drawString(this.font, entry.getKey().getName() + String.valueOf(entry.getValue()), 0, y, FLAVOR_COLORS.get(entry.getKey()));
			y += 20;
		}
		
		y = 0;
		for(Entry<CookingPattern, Integer> entry : this.container.getPatternExps().entrySet()) {
			this.drawString(this.font, entry.getKey().getName() + String.valueOf(entry.getValue()), 50, y, PATTERN_COLORS.get(entry.getKey()));
			y += 20;
		}
	}
}
