package com.github.immortalmice.foodpower.customclass.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.network.IContainerFactory;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.lists.Containers;

public class ContainerPack<T extends ContainerBase>{
	private final String name;
	private final IContainerFactory<T> factory;

	public ContainerPack(String nameIn, IContainerFactory<T> factoryIn){
		this.factory = factoryIn;
		this.name = nameIn;

		Containers.list.add(this);
		Containers.REGISTER.register(this.name, () -> this.getContainerType());
	}

	public String getName(){
		return this.name;
	}

	public ContainerType<T> getContainerType(){
		return new ContainerType<T>(factory);
	}
}