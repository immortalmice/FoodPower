package com.github.immortalmice.foodpower.customclass.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.network.IContainerFactory;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.lists.Containers;

public class ContainerPack<T extends ContainerBase>{
	private final String fpName;
	private final IContainerFactory<T> factory;

	public ContainerPack(String nameIn, IContainerFactory<T> factoryIn){
		this.factory = factoryIn;
		this.fpName = nameIn;

		Containers.list.add(this);
		Containers.REGISTER.register(this.getFPName(), () -> this.getContainerType());
	}

	public String getFPName(){
		return this.fpName;
	}

	public ContainerType<T> getContainerType(){
		return new ContainerType<T>(factory);
	}
}