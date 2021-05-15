package com.github.immortalmice.foodpower.capability.implement;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import com.github.immortalmice.foodpower.capability.interfaces.IFPFlavorExpCapability;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.FlavorTypes;
import com.github.immortalmice.foodpower.types.FlavorType;
import com.github.immortalmice.foodpower.util.LevelPointConverter;

public class FPFlavorExpCapability implements IFPFlavorExpCapability{
	private Map<FlavorType, Integer> flavorExp = new HashMap<>();
	private boolean dirty = true;

	public static final int MAX_LEVEL = 99;
	public static final int CEIL_LEVEL = 95;
	public static final int MIN_LEVEL = 0;
	
	public FPFlavorExpCapability(){
		for(FlavorType flavor : FlavorTypes.list){
			if(!flavor.equals(FlavorTypes.NONE)){
				flavorExp.put(flavor, 0);
			}
		}
	}

	@Override
	public int getExpLevel(FlavorType flavorIn){
		if(this.flavorExp.containsKey(flavorIn)){
			return LevelPointConverter.FLAVOR_CONVERTER.pointToLevel(this.flavorExp.get(flavorIn));
		}
		return 0;
	}
	@Override
	public Map<FlavorType, Integer> getAllExpLevel(){
		Map<FlavorType, Integer> map = new HashMap<>();
		for(FlavorType flavor : this.flavorExp.keySet()){
			map.put(flavor, LevelPointConverter.FLAVOR_CONVERTER.pointToLevel(this.flavorExp.get(flavor)));
		}
		return map;
	}
	@Override
	public void setExpLevel(FlavorType flavor, int level){
		if(flavor == null) return;

		if(level > FPFlavorExpCapability.MAX_LEVEL) level = FPFlavorExpCapability.MAX_LEVEL;
		if(level < FPFlavorExpCapability.MIN_LEVEL) level = FPFlavorExpCapability.MIN_LEVEL;

		this.flavorExp.put(flavor, LevelPointConverter.FLAVOR_CONVERTER.levelToPoint(level, 0));
		
		this.markDirty(true);
	}

	@Override
	public int addExp(FlavorType flavorIn, int value){
		if(flavorIn == null || !this.flavorExp.containsKey(flavorIn) || !this.flavorExp.containsKey(flavorIn.getOppositeFlavor())) return 0;

		if(this.flavorExp.get(flavorIn) <= this.flavorExp.get(flavorIn.getOppositeFlavor()))
			value /= 2;
		
		int oldPoint = this.flavorExp.get(flavorIn);
		int newPoint = oldPoint + value;
		this.flavorExp.put(flavorIn, newPoint);

		if(LevelPointConverter.FLAVOR_CONVERTER.pointToLevel(this.flavorExp.get(flavorIn)) >= FPFlavorExpCapability.CEIL_LEVEL)
			this.setExpLevel(flavorIn, FPFlavorExpCapability.CEIL_LEVEL);

		if(LevelPointConverter.FLAVOR_CONVERTER.pointToLevel(this.flavorExp.get(flavorIn)) <= FPFlavorExpCapability.MIN_LEVEL)
			this.setExpLevel(flavorIn, FPFlavorExpCapability.MIN_LEVEL);

		this.markDirty(true);
		
		return this.flavorExp.get(flavorIn) - oldPoint;
	}
	
	public void markDirty(boolean dirtyIn) {
		this.dirty = dirtyIn;
	}
	
	public boolean isDirty() {
		return this.dirty;
	}

	public static class Provider implements ICapabilitySerializable<CompoundNBT>{
		private LazyOptional<IFPFlavorExpCapability> instance = LazyOptional.of(Capabilities.FLAVOR_EXP_CAPABILITY::getDefaultInstance);
		private IStorage<IFPFlavorExpCapability> storage = Capabilities.FLAVOR_EXP_CAPABILITY.getStorage();
		
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			if(cap == Capabilities.FLAVOR_EXP_CAPABILITY){
				return this.instance.cast();
			}
			return LazyOptional.empty();
		}

		@Override
		public CompoundNBT serializeNBT() {
			if(instance.isPresent()){
				return (CompoundNBT) storage.writeNBT(Capabilities.FLAVOR_EXP_CAPABILITY, this.instance.orElse(null), null);
			}
			return new CompoundNBT();
		}

		@Override
		public void deserializeNBT(CompoundNBT nbt) {
			instance.ifPresent((capability) -> {
				storage.readNBT(Capabilities.FLAVOR_EXP_CAPABILITY, capability, null, nbt);
			});
			return;
		}
	}

	public static class Storage implements Capability.IStorage<IFPFlavorExpCapability>{

		@Override
		public INBT writeNBT(Capability<IFPFlavorExpCapability> capability, IFPFlavorExpCapability instance,
				Direction side) {
			CompoundNBT nbt = new CompoundNBT();

			CompoundNBT flavorNBT = new CompoundNBT();
			for(FlavorType flavor : instance.getAllExpLevel().keySet()){
				if(flavor == null) continue;
				flavorNBT.putInt(flavor.getName(), instance.getExpLevel(flavor));
			}
			nbt.put("flavor_nbt", flavorNBT);

			return nbt;
		}

		@Override
		public void readNBT(Capability<IFPFlavorExpCapability> capability, IFPFlavorExpCapability instance,
				Direction side, INBT nbtIn) {
			CompoundNBT nbt = (CompoundNBT)nbtIn;
			
			if(nbt.contains("flavor_nbt")){
				CompoundNBT flavorNBT = (CompoundNBT)nbt.get("flavor_nbt");
				for(String key : flavorNBT.keySet()){
					FlavorType flavor = FlavorTypes.getFlavorByName(key);
					if(flavor == null) continue;
					instance.setExpLevel(flavor, flavorNBT.getInt(key));
				}
			}
			return;
		}
	}
}