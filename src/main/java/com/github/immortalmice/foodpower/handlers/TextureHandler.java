package com.github.immortalmice.foodpower.handlers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.lists.Effects;

public class TextureHandler{
	/* Hack sprites field in TextureStitchEvent.Pre.class and remove all FoodEffect's resource loaction */
	public static void deleteAllFoodEffectTexture(TextureStitchEvent.Pre event){
		try{
			/* I know I need to pass in srg name, but it's forge's class xd */
			Set<ResourceLocation> sprites = ObfuscationReflectionHelper.getPrivateValue(TextureStitchEvent.Pre.class, event, "sprites");
			List<String> foodEffectNames = Effects.list.stream().map((foodEffect) -> foodEffect.getFPName()).collect(Collectors.toList());
			Set<ResourceLocation> removeObjs = new HashSet<ResourceLocation>();

			for(ResourceLocation sprite : sprites){
				if(sprite.getNamespace().equals(FoodPower.MODID)){
					String fileName = sprite.getPath().substring("mob_effect/".length());
					if(foodEffectNames.contains(fileName)){
						removeObjs.add(sprite);
					}
				}
			}
			
			sprites.removeAll(removeObjs);
		}catch(RuntimeException e){
			/* Hack failed, prepare for log bomb of FileNotFoundException */
		}
		return;
	}
}