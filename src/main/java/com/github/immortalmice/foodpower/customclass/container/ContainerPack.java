package com.github.immortalmice.foodpower.customclass.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.network.IContainerFactory;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.lists.Containers;

public class ContainerPack<T extends ContainerBase>{
	private final IContainerFactory<T> factory;

	public ContainerPack(String nameIn, IContainerFactory<T> factoryIn){
		this.factory = factoryIn;

		Containers.list.add(this);
		Containers.REGISTER.register(nameIn, () -> this.getContainerType());
	}

	public ContainerType<T> getContainerType(){
		return new ContainerType<T>(factory);
	}
}