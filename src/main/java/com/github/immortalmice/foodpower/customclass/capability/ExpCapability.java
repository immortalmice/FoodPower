package com.github.immortalmice.foodpower.customclass.capability;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class ExpCapability implements IExpCapability{
	private Map<CookingPattern, Integer> patternExp = new HashMap<>();

	private static int BASE = 10;
	private static double POWER = 1.1;

	public ExpCapability(){
		for(CookingPattern pattern : CookingPatterns.list){
			this.patternExp.put(pattern, 0);
		}
	}

	@Override
	public String getFullPatternExpToString(){
		String str = "";
		for(CookingPattern key : this.patternExp.keySet()){
			str += I18n.format("pattern.foodpower." + key.getName()) + " : Lv." + this.getPatternExpLevel(key) + "\n";
		}
		return str;
	}

	@Override
	public int getPatternExpLevel(CookingPattern patternIn){
		return ExpCapability.valueToLevel(this.patternExp.get(patternIn));
	}

	@Override
	public Map<CookingPattern, Integer> getAllPatternExpLevel(){
		Map<CookingPattern, Integer> map = new HashMap<>();
		for(CookingPattern pattern : this.patternExp.keySet()){
			map.put(pattern, ExpCapability.valueToLevel(this.patternExp.get(pattern)));
		}
		return map;
	}

	@Override
	public void setPatternExpLevel(CookingPattern pattern, int level){
		this.patternExp.put(pattern, ExpCapability.levelToValue(level, 0));
	}

	private static int valueToLevel(int value){
		if(value != 0)
			return (int) Math.floor(Math.log(1 - ((value * (1 - ExpCapability.POWER)) / ExpCapability.BASE)) / Math.log(ExpCapability.POWER));
		else
			return 0;
	}

	private static int levelToValue(int level, int remaining){
		return (int) Math.ceil(((Math.pow(ExpCapability.POWER, level) - 1) * ExpCapability.BASE) / (ExpCapability.POWER - 1)) + remaining;
	}

	public static class Provider implements ICapabilitySerializable<CompoundNBT>{
		private LazyOptional<IExpCapability> instance = LazyOptional.of(Capabilities.EXP_CAPABILITY::getDefaultInstance);
		private IStorage<IExpCapability> storage = Capabilities.EXP_CAPABILITY.getStorage();
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			if(cap == Capabilities.EXP_CAPABILITY){
				return this.instance.cast();
			}
			return LazyOptional.empty();
		}
		@Override
		public CompoundNBT serializeNBT() {
			if(instance.isPresent()){
				return (CompoundNBT) storage.writeNBT(Capabilities.EXP_CAPABILITY, this.instance.orElse(null), null);
			}
			return new CompoundNBT();
		}
		@Override
		public void deserializeNBT(CompoundNBT nbt) {
			instance.ifPresent((capability) -> {
				storage.readNBT(Capabilities.EXP_CAPABILITY, capability, null, nbt);
			});
			return;
		}
	}
	
	public static class Storage implements Capability.IStorage<IExpCapability>{
		@Override
		public INBT writeNBT(Capability<IExpCapability> capability, IExpCapability instance, Direction side){
			CompoundNBT nbt = new CompoundNBT();

			CompoundNBT patternNBT = new CompoundNBT();
			for(CookingPattern pattern : instance.getAllPatternExpLevel().keySet()){
				patternNBT.putInt(pattern.getName(), instance.getPatternExpLevel(pattern));
			}
			nbt.put("pattern_nbt", patternNBT);

			return nbt;
		}

		@Override
		public void readNBT(Capability<IExpCapability> capability, IExpCapability instance, Direction side, INBT nbtIn){
			CompoundNBT nbt = (CompoundNBT)nbtIn;
			
			if(nbt.contains("pattern_nbt")){
				CompoundNBT patternNBT = (CompoundNBT)nbt.get("pattern_nbt");
				for(String key : patternNBT.keySet()){
					instance.setPatternExpLevel(CookingPatterns.getPatternByName(key), patternNBT.getInt(key));
				}
			}
			return;
		}		
	}
}