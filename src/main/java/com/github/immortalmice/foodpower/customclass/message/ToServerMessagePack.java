package com.github.immortalmice.foodpower.customclass.message;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

import com.github.immortalmice.foodpower.lists.Messages;

public class ToServerMessagePack<T extends IMessage>{
	Class<T> iMessageClass;
	Class<? extends IMessageHandler<T, IMessage>> handlerClass;

	public ToServerMessagePack(Class<T> iMessageClassIn, Class<? extends IMessageHandler<T, IMessage>> handlerClassIn){
		this.iMessageClass = iMessageClassIn;
		this.handlerClass = handlerClassIn;

		Messages.sendToServerList.add(this);
	}

	public Class<T> getIMessageClass(){
		return iMessageClass;
	}

	public Class<? extends IMessageHandler<T, IMessage>> getHandlerClass(){
		return handlerClass;
	}
}