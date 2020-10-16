package com.github.immortalmice.foodpower.capability.implement;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import com.github.immortalmice.foodpower.capability.interfaces.IFPPatternExpCapability;
import com.github.immortalmice.foodpower.cooking.CookingPattern;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.util.LevelPointConverter;

/* Capability about FoodPower's Pattern Exp */
public class FPPatternExpCapability implements IFPPatternExpCapability{
	private Map<CookingPattern, Integer> patternExp = new HashMap<>();

	public static final int MAX_LEVEL = 99;
	public static final int MIN_LEVEL = 0;

	public FPPatternExpCapability(){
		for(CookingPattern pattern : CookingPatterns.list){
			this.patternExp.put(pattern, 0);
		}
	}

	@Override
	public int getExpLevel(CookingPattern patternIn){
		if(this.patternExp.containsKey(patternIn)){
			return LevelPointConverter.PATTERN_CONVERTER.pointToLevel(this.patternExp.get(patternIn));
		}
		return 0;
	}

	@Override
	public Map<CookingPattern, Integer> getAllExpLevel(){
		Map<CookingPattern, Integer> map = new HashMap<>();
		for(CookingPattern pattern : this.patternExp.keySet()){
			map.put(pattern, LevelPointConverter.PATTERN_CONVERTER.pointToLevel(this.patternExp.get(pattern)));
		}
		return map;
	}

	@Override
	public void setExpLevel(CookingPattern pattern, int level){
		if(pattern == null) return;

		if(level > FPPatternExpCapability.MAX_LEVEL) level = FPPatternExpCapability.MAX_LEVEL;
		if(level < FPPatternExpCapability.MIN_LEVEL) level = FPPatternExpCapability.MIN_LEVEL;

		this.patternExp.put(pattern, LevelPointConverter.PATTERN_CONVERTER.levelToPoint(level, 0));
	}

	@Override
	public int addExp(CookingPattern patternIn, int value){
		if(patternIn != null && this.patternExp.containsKey(patternIn)){
			int oldPoint = this.patternExp.get(patternIn);
			int newPoint = oldPoint + value;
			this.patternExp.put(patternIn, newPoint);

			if(LevelPointConverter.PATTERN_CONVERTER.pointToLevel(this.patternExp.get(patternIn)) >= FPPatternExpCapability.MAX_LEVEL)
				this.setExpLevel(patternIn, FPPatternExpCapability.MAX_LEVEL);

			if(LevelPointConverter.PATTERN_CONVERTER.pointToLevel(this.patternExp.get(patternIn)) <= FPPatternExpCapability.MIN_LEVEL)
				this.setExpLevel(patternIn, FPPatternExpCapability.MIN_LEVEL);

			return this.patternExp.get(patternIn) - oldPoint;
		}
		return 0;
	}

	public static class Provider implements ICapabilitySerializable<CompoundNBT>{
		private LazyOptional<IFPPatternExpCapability> instance = LazyOptional.of(Capabilities.PATTERN_EXP_CAPABILITY::getDefaultInstance);
		private IStorage<IFPPatternExpCapability> storage = Capabilities.PATTERN_EXP_CAPABILITY.getStorage();
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			if(cap == Capabilities.PATTERN_EXP_CAPABILITY){
				return this.instance.cast();
			}
			return LazyOptional.empty();
		}
		@Override
		public CompoundNBT serializeNBT() {
			if(instance.isPresent()){
				return (CompoundNBT) storage.writeNBT(Capabilities.PATTERN_EXP_CAPABILITY, this.instance.orElse(null), null);
			}
			return new CompoundNBT();
		}
		@Override
		public void deserializeNBT(CompoundNBT nbt) {
			instance.ifPresent((capability) -> {
				storage.readNBT(Capabilities.PATTERN_EXP_CAPABILITY, capability, null, nbt);
			});
			return;
		}
	}
	
	public static class Storage implements Capability.IStorage<IFPPatternExpCapability>{
		@Override
		public INBT writeNBT(Capability<IFPPatternExpCapability> capability, IFPPatternExpCapability instance, Direction side){
			CompoundNBT nbt = new CompoundNBT();

			CompoundNBT patternNBT = new CompoundNBT();
			for(CookingPattern pattern : instance.getAllExpLevel().keySet()){
				if(pattern == null) continue;
				patternNBT.putInt(pattern.getName(), instance.getExpLevel(pattern));
			}
			nbt.put("pattern_nbt", patternNBT);

			return nbt;
		}

		@Override
		public void readNBT(Capability<IFPPatternExpCapability> capability, IFPPatternExpCapability instance, Direction side, INBT nbtIn){
			CompoundNBT nbt = (CompoundNBT)nbtIn;
			
			if(nbt.contains("pattern_nbt")){
				CompoundNBT patternNBT = (CompoundNBT)nbt.get("pattern_nbt");
				for(String key : patternNBT.keySet()){
					CookingPattern pattern = CookingPatterns.getPatternByName(key);
					if(pattern == null) continue;
					instance.setExpLevel(pattern, patternNBT.getInt(key));
				}
			}
			return;
		}		
	}
}