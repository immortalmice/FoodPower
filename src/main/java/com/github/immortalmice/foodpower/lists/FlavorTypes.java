package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.customclass.flavor.FlavorType;

public class FlavorTypes{
	public static final List<FlavorType> list = new ArrayList<FlavorType>();

	public static final FlavorType NONE = new FlavorType("none");

	public static final FlavorType SWEET = new FlavorType("sweet");
	public static final FlavorType BITTER = new FlavorType("bitter");
	public static final FlavorType SOUR = new FlavorType("sour");
	public static final FlavorType SALTY = new FlavorType("salty");
	public static final FlavorType HOT = new FlavorType("hot");
	public static final FlavorType ICE = new FlavorType("ice");
	public static final FlavorType NETHER = new FlavorType("nether");
	public static final FlavorType ENDER = new FlavorType("ender");

	@Nullable
	public static FlavorType getFlavorByName(String nameIn){
		for(FlavorType flavor : FlavorTypes.list){
			if(flavor.getName().equals(nameIn)){
				return flavor;
			}
		}
		return null;
	}

	public static List<String> getFlavorNames(){
		List<String> names = new ArrayList<String>();
		for(FlavorType flavor : FlavorTypes.list){
			names.add(flavor.getName());
		}
		return names;
	}

	static{
		FlavorTypes.SWEET.setOppositeFlavor(FlavorTypes.BITTER);
		FlavorTypes.SOUR.setOppositeFlavor(FlavorTypes.SALTY);
		FlavorTypes.HOT.setOppositeFlavor(FlavorTypes.ICE);
		FlavorTypes.NETHER.setOppositeFlavor(FlavorTypes.ENDER);
	}
	
}