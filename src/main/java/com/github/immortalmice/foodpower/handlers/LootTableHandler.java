package com.github.immortalmice.foodpower.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.github.immortalmice.foodpower.FoodPower;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;

public class LootTableHandler{
	private static final List<LootTableHandler.Modifier> modifiers = new ArrayList<>();
	
	public static boolean modify(LootTableLoadEvent event){
		boolean isModified = false;
		for(LootTableHandler.Modifier modifier: LootTableHandler.modifiers){
			if(modifier.isTarget.test(event.getName())){
				switch(modifier.type){
					case INJECT:
						// Inject a new loot pool into current loot table
						event.getTable().addPool(
							LootPool.builder().addEntry(
								TableLootEntry.builder(modifier.modifierLocation)
							).build()
						);
						isModified = true;
						break;
					case REPLACE:
						// Replace the whole loot table
						event.setTable(
							LootTable.builder().addLootPool(
								LootPool.builder().addEntry(
									TableLootEntry.builder(modifier.modifierLocation)
								)
							).build()
						);
						isModified = true;
						break;
					case EMPTY:
						// Empty the loot table by canceling the event
						event.setCanceled(true);
						isModified = true;
						break;
					case NONE:
						// Do nothing
						break;
				}
				
				if(modifier.extraBehavior != null){
					modifier.extraBehavior.accept(event);
					isModified = true;
				}
			}
		}
		return isModified;
	}
	
	static {
		LootTableHandler.modifiers.addAll(Arrays.asList(
			LootTableHandler.Modifier.builder().type(Modifier.ModifyType.INJECT).name("grass").build(),
			LootTableHandler.Modifier.builder().type(Modifier.ModifyType.INJECT).name("ice").build(),
			LootTableHandler.Modifier.builder().type(Modifier.ModifyType.INJECT).name("packed_ice").build(),
			LootTableHandler.Modifier.builder().type(Modifier.ModifyType.INJECT).name("blue_ice").build(),
			LootTableHandler.Modifier.builder().type(Modifier.ModifyType.INJECT).name("stone").build(),
			LootTableHandler.Modifier.builder().type(Modifier.ModifyType.INJECT).name("melon").build()
		));
	}

	@SuppressWarnings("unused")
	private static class Modifier{
		public static enum ModifyType{
			INJECT, REPLACE, EMPTY, NONE
		}
		
		private final ModifyType type;
		private final Predicate<ResourceLocation> isTarget;
		private final ResourceLocation modifierLocation;
		private final Consumer<LootTableLoadEvent> extraBehavior;
		
		private Modifier(ModifyType typeIn, Predicate<ResourceLocation> isTargetIn, ResourceLocation modifierLocationIn, Consumer<LootTableLoadEvent> extraBehaviorIn){
			this.type = typeIn;
			this.isTarget = isTargetIn;
			this.modifierLocation = modifierLocationIn;
			this.extraBehavior = extraBehaviorIn;
		}
		
		private static Modifier.Builder builder(){
			return new Modifier.Builder();
		}
	
		private static class Builder{
			private ModifyType type;
			private Predicate<ResourceLocation> isTarget;
			private ResourceLocation modifierLocation;
			private Consumer<LootTableLoadEvent> extraBehavior;
			
			private Modifier.Builder type(ModifyType typeIn){
				this.type = typeIn;
				return this;
			}
			
			private Modifier.Builder isTarget(Predicate<ResourceLocation> isTargetIn){
				this.isTarget = isTargetIn;
				return this;
			}
			
			private Modifier.Builder location(ResourceLocation modifierLocationIn){
				this.modifierLocation = modifierLocationIn;
				return this;
			}
			
			private Modifier.Builder extraBehavior(Consumer<LootTableLoadEvent> extraBehaviorIn){
				this.extraBehavior = extraBehaviorIn;
				return this;
			}
			
			private Modifier.Builder name(String nameIn){
				this.isTarget = location -> location.equals(new ResourceLocation("minecraft", "blocks/" + nameIn));
				this.modifierLocation = new ResourceLocation(FoodPower.MODID, "inject/" + nameIn);
				return this;
			}
			
			private Modifier build(){
				ModifyType type = this.type != null ? this.type : ModifyType.INJECT;
				Predicate<ResourceLocation> isTarget = this.isTarget != null ? this.isTarget : location -> false;
				ResourceLocation modifierLocation = this.modifierLocation != null ? this.modifierLocation : new ResourceLocation("");
				Consumer<LootTableLoadEvent> extraBehavior = this.extraBehavior != null ? this.extraBehavior : event -> {};
				
				return new Modifier(type, isTarget, modifierLocation, extraBehavior);
			}
		}
	}
}
