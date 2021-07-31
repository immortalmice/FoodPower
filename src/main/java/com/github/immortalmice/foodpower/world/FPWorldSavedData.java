package com.github.immortalmice.foodpower.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.boss.entities.BitterBoss;
import com.github.immortalmice.foodpower.boss.entities.EnderBoss;
import com.github.immortalmice.foodpower.boss.entities.NetherBoss;
import com.github.immortalmice.foodpower.boss.entities.SaltyBoss;
import com.github.immortalmice.foodpower.boss.entities.SourBoss;
import com.github.immortalmice.foodpower.boss.entities.SweetBoss;
import com.github.immortalmice.foodpower.capability.implement.FPFlavorExpCapability;
import com.github.immortalmice.foodpower.food.Meal;
import com.github.immortalmice.foodpower.handlers.ConfigHandler;
import com.github.immortalmice.foodpower.lists.Bosses;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.FlavorTypes;
import com.github.immortalmice.foodpower.types.FlavorType;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class FPWorldSavedData extends WorldSavedData {
	private List<WasteData> wasteList = new ArrayList<>();
	
	public FPWorldSavedData() {
		super("foodpower");
	}

	@Override
	public void read(CompoundNBT nbt) {
		ListNBT wasteDataNBT = nbt.getList("waste_data", 10);
		wasteList = WasteData.fromCompounds(wasteDataNBT);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("waste_data", WasteData.toCompounds(this.wasteList));
		return compound;
	}
	
	@Nullable
	public void triggerWaste(ServerPlayerEntity player, ItemEntity entityItem, ItemStack stack) {
		player.getCapability(Capabilities.FLAVOR_EXP_CAPABILITY, null).ifPresent(cap -> {
			World world = entityItem.world;
			long time = world.getGameTime();
			FlavorType summoned = null;
			Map<String, Integer> exps = Meal.getFlavorExp(stack);
			Optional<WasteData> optional = this.wasteList.stream().filter(wasteData -> {
				return wasteData.id.equals(player.getGameProfile().getId());
			}).findFirst();
			
			WasteData data = null;
			if(!optional.isPresent()) {
				data = new WasteData(player.getGameProfile().getId());
				this.wasteList.add(data);
			} else {
				data = optional.get();
			}
			
			if(time - data.lastWasteTime > 200) {
				data.wasteCounter.clear();
			}
			
			for(Entry<String, Integer> entry : exps.entrySet()) {
				FlavorType type = FlavorTypes.getFlavorByName(entry.getKey());
				if(type != null && cap.getExpLevel(type) >= FPFlavorExpCapability.CEIL_LEVEL) {
					Integer value = data.wasteCounter.getOrDefault(type, 0);
					data.wasteCounter.put(type, entry.getValue() * stack.getCount() + value);
				}
			}
			data.lastWasteTime = time;
			
			for(Entry<FlavorType, Integer> entry : data.wasteCounter.entrySet()) {
				if(entry.getValue() >= 6000) {
					summoned = entry.getKey();
					entry.setValue(0);
				}
			}
			
			MobEntity bossEntity = null;
            if(summoned == FlavorTypes.SWEET) {
            	bossEntity = new SweetBoss(Bosses.EntityTypes.SWEET_BOSS, world);
            }else if(summoned == FlavorTypes.BITTER){
            	bossEntity = new BitterBoss(Bosses.EntityTypes.BITTER_BOSS, world);
            }else if(summoned == FlavorTypes.SOUR) {
            	bossEntity = new SourBoss(Bosses.EntityTypes.SOUR_BOSS, world);
            }else if(summoned == FlavorTypes.SALTY) {
            	bossEntity = new SaltyBoss(Bosses.EntityTypes.SALTY_BOSS, world);
            }else if(summoned == FlavorTypes.NETHER) {
            	bossEntity = new NetherBoss(Bosses.EntityTypes.NETHER_BOSS, world);
            }else if(summoned == FlavorTypes.ENDER) {
            	bossEntity = new EnderBoss(Bosses.EntityTypes.ENDER_BOSS, world);
            }
            if(bossEntity != null && ConfigHandler.SERVER.canSummonBoss) {
            	bossEntity.setPosition(entityItem.getPosX(), entityItem.getPosY(), entityItem.getPosZ());
            	world.addEntity(bossEntity);
            }
		});
	}
	
	private static class WasteData {
		private final UUID id;
		private long lastWasteTime = 0;
		private Map<FlavorType, Integer> wasteCounter = new HashMap<>();
		
		public WasteData(UUID idIn) {
			this.id = idIn;
		}
		
		public static WasteData read(CompoundNBT nbt) {
			WasteData data = new WasteData(UUID.fromString(nbt.getString("player")));
			data.lastWasteTime = nbt.getLong("last");
			CompoundNBT counterNBT = (CompoundNBT) nbt.get("counter");
			for(String key : counterNBT.keySet()) {
				FlavorType type = FlavorTypes.getFlavorByName(key);
				if(type != null) {
					data.wasteCounter.put(type, counterNBT.getInt(key));
				}
			}
			return data;
		}
		
		public static List<WasteData> fromCompounds(ListNBT nbt) {
			List<WasteData> list = new ArrayList<>();
			nbt.forEach(comp -> {
				list.add(WasteData.read((CompoundNBT) comp));
			});
			return list;
		}
		
		public CompoundNBT write(CompoundNBT nbt) {
			nbt.putString("player", this.id.toString());
			nbt.putLong("last", this.lastWasteTime);
			CompoundNBT counterNBT = new CompoundNBT();
			this.wasteCounter.entrySet().forEach((entry) -> {
				counterNBT.putInt(entry.getKey().getName(), entry.getValue());
			});
			nbt.put("counter", counterNBT);
			return nbt;
		}
		
		public static ListNBT toCompounds(List<WasteData> list) {
			ListNBT nbt = new ListNBT();
			list.forEach(data -> {
				nbt.add(data.write(new CompoundNBT()));
			});
			return nbt;
		}
	}
}
