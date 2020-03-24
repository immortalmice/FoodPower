package com.github.immortalmice.foodpower.customclass.capability;

import java.util.HashMap;
import java.util.Map;

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
	private Map<String, Integer> patternExp = new HashMap<>();

	public ExpCapability(){
		for(CookingPattern pattern : CookingPatterns.list){
			this.patternExp.put(pattern.getName(), 0);
		}
	}

	public String getFullPatternExpToString(){
		String str = "";
		for(String key : this.patternExp.keySet()){
			str += key + " : " + this.getPatternExpInt(key) + "\n";
		}
		return str;
	}

	public int getPatternExpInt(String patternNameIn){
		return this.patternExp.get(patternNameIn);
	}

	public Map<String, Integer> getPatternExp(){
		return this.patternExp;
	}

	public void setPatternExp(String patternName, int value){
		this.patternExp.put(patternName, value);
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
			for(String patternName : instance.getPatternExp().keySet()){
				patternNBT.putInt(patternName, instance.getPatternExpInt(patternName));
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
					instance.setPatternExp(key, patternNBT.getInt(key));
				}
			}
			return;
		}		
	}
}