package com.github.immortalmice.foodpower.types;

import javax.annotation.Nullable;

import com.github.immortalmice.foodpower.lists.FlavorTypes;

public class FlavorType{
	private String name;
	private FlavorType oppositeFlavor;
	private boolean isOppositeSet = false;

	public FlavorType(String nameIn){
		this.name = nameIn;

		FlavorTypes.list.add(this);
	}

	/* If oppositeFlavor is already set, skip everything */
	public void setOppositeFlavor(FlavorType flavorIn){
		if(this.isOppositeSet)
			return;
		this.oppositeFlavor = flavorIn;
		this.isOppositeSet = true;
		flavorIn.setOppositeFlavor(this);
	}

	@Nullable
	public FlavorType getOppositeFlavor(){
		return this.oppositeFlavor;
	}

	public String getName(){
		return this.name;
	}

	public boolean equals(Object flavorIn){
		if(flavorIn instanceof FlavorType){
			return this.name.equals(((FlavorType)flavorIn).getName());
		}
		return false;
	}

	public boolean isOpposite(FlavorType flavorIn){
		return this.oppositeFlavor.equals(flavorIn);
	}
}