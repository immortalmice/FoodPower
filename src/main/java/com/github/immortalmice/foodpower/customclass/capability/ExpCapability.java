package com.github.immortalmice.foodpower.customclass.capability;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class ExpCapability{
	private Map<String, Integer> patternExp = new HashMap<>();

	public ExpCapability(){
		for(CookingPattern pattern : CookingPatterns.list){
			this.patternExp.put(pattern.getName(), 0);
		}
	}

	public String getFullPatternExpToString(){
		String str = "";
		for(String key : this.patternExp.keySet()){
			str += key + ":" + this.getPatternExpInt(key) + "\n";
		}
		return str;
	}

	public int getPatternExpInt(String patternNameIn){
		return this.patternExp.get(patternNameIn);
	}

	public Map<String, Integer> getPatternExp(){
		return this.patternExp;
	}

	private void setPatternExp(String patternName, int value){
		this.patternExp.put(patternName, value);
	}

	public static class Provider implements ICapabilityProvider{
		private LazyOptional<ExpCapability> instance = LazyOptional.of(Capabilities.EXP_CAPABILITY::getDefaultInstance);
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			if(cap == Capabilities.EXP_CAPABILITY){
				return this.instance.cast();
			}
			return LazyOptional.empty();
		}
	}
	
	public static class Storage implements Capability.IStorage<ExpCapability>{
		@Override
		public INBT writeNBT(Capability<ExpCapability> capability, ExpCapability instance, Direction side){
			CompoundNBT nbt = new CompoundNBT();

			CompoundNBT patternNBT = new CompoundNBT();
			for(String patternName : instance.getPatternExp().keySet()){
				patternNBT.putInt(patternName, instance.getPatternExpInt(patternName));
			}
			nbt.put("pattern_nbt", patternNBT);

			return nbt;
		}

		@Override
		public void readNBT(Capability<ExpCapability> capability, ExpCapability instance, Direction side, INBT nbtIn){
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