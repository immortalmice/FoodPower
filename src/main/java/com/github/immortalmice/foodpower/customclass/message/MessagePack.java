package com.github.immortalmice.foodpower.customclass.message;

import com.github.immortalmice.foodpower.baseclass.IMessageBase;
import com.github.immortalmice.foodpower.lists.Messages;

/* Use in store Message Classes and regist them when needed */
public class MessagePack{
	private final Class<? extends IMessageBase<?>> messageClass;
	private IMessageBase<?> INSTANCE = null;
	public MessagePack(Class<? extends IMessageBase<?>> classIn){
		this.messageClass = classIn;
		try{
			this.INSTANCE = this.messageClass.getConstructor().newInstance();
		}catch(Exception e){

		}
		Messages.list.add(this);
	}
	public void regist(int i){
		if(this.INSTANCE != null){
			this.INSTANCE.registMessage(i);
		}
	}
}