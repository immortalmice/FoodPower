package com.github.immortalmice.foodpower.customclass.message;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.lists.Messages;

/* Use in store Message Classes and regist them when needed */
public class ToServerMessagePack<T extends IMessage>{
	Class<T> iMessageClass;
	Class<? extends IMessageHandler<T, IMessage>> handlerClass;

	public ToServerMessagePack(Class<T> iMessageClassIn, Class<? extends IMessageHandler<T, IMessage>> handlerClassIn){
		this.iMessageClass = iMessageClassIn;
		this.handlerClass = handlerClassIn;

		Messages.sendToServerList.add(this);
	}

	public void registThis(int discriminator){
		FoodPower.network.registerMessage(this.handlerClass, this.iMessageClass, discriminator, Side.SERVER);
	}
}