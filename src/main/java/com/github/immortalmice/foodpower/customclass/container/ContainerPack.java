package com.github.immortalmice.foodpower.customclass.container;

import net.minecraft.inventory.container.ContainerType;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.lists.Containers;

public class ContainerPack<T extends ContainerBase>{
	private final String fpName;
	private final ContainerType<T> containerType;

	public ContainerPack(String nameIn, ContainerType.IFactory<T> factory){
		containerType = new ContainerType<T>(factory);
		this.fpName = nameIn;

		Containers.list.add(this);
		Containers.REGISTER.register(this.getFPName(), () -> this.getContainerType());
	}

	public String getFPName(){
		return this.fpName;
	}

	public ContainerType<T> getContainerType(){
		return this.containerType;
	}

	public void registScreen(){
		T.registScreen(this.getContainerType());
	}
}