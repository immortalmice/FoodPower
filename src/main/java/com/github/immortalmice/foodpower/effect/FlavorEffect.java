package com.github.immortalmice.foodpower.effect;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.handlers.ConfigHandler;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class FlavorEffect extends Effect {
	protected FlavorEffect() {
		super(EffectType.NEUTRAL, 0);
	}
	
	@Override
	public boolean shouldRender(EffectInstance effect) { return ConfigHandler.CLIENT.doShowFlavorEffect; }
	
	@Override
	public boolean shouldRenderInvText(EffectInstance effect) { return ConfigHandler.CLIENT.doShowFlavorEffect; }
	
	@Override
	public boolean shouldRenderHUD(EffectInstance effect) { return ConfigHandler.CLIENT.doShowFlavorEffect; }
	
	@Override
	public List<ItemStack> getCurativeItems() { return new ArrayList<>(); }
}
