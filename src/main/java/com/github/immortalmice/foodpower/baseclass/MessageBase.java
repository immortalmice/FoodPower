package com.github.immortalmice.foodpower.baseclass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.github.immortalmice.foodpower.FoodPower;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public abstract class MessageBase<T extends MessageBase<T>>{
	/**
	 * THIS METHOD IS DANGEROUS
	 * IF YOU CANNOT REALLY UNDERSTAND WHAT THIS WORK, OVERRIDE THIS!
	 * 
	 * Regist this message to network system.
	 * This default implementation will use sub class's static method 'encode', 'decode', 'handle' and regist them with that sub class.
	 * 
	 * @param i
	 */
	public void registMessage(int i){
		@SuppressWarnings("unchecked")
		Class<T> self = (Class<T>) this.getClass();
		
		try{
			// Reflect the encoder method & transform to BiConsumer<T, PacketBuffer>
			Method encoderMethod = self.getDeclaredMethod("encode", self, PacketBuffer.class);
			if(!Modifier.isStatic(encoderMethod.getModifiers())) throw new Exception("Message class " + self.getName() + "'s encoder is not static!");
			
			BiConsumer<T, PacketBuffer> encoder = (message, buffer) -> {
				MessageBase.invokeStaticMethod(encoderMethod, message, buffer);
			};
			
			// Reflect the decoder method & transform to Function<PacketBuffer, T>
			Method decodeMethod = self.getDeclaredMethod("decode", PacketBuffer.class);
			if(!Modifier.isStatic(decodeMethod.getModifiers())) throw new Exception("Message class " + self.getName() + "'s decoder is not static!");
			if(decodeMethod.getReturnType() != self) throw new Exception("Message class " + self.getName() + "'s decoder not return with it's class!");
			
			@SuppressWarnings("unchecked")
			Function<PacketBuffer, T> decoder = (buffer) -> {
				return (T) MessageBase.invokeStaticMethod(decodeMethod, buffer);
			};
			
			// Reflect the handler method & transform to BiConsumer<T, Supplier<NetworkEvent.Context>>
			Method handleMethod = self.getDeclaredMethod("handle", self, Supplier.class);
			if(!Modifier.isStatic(handleMethod.getModifiers())) throw new Exception("Message class " + self.getName() + "'s handle is not static!");
			
			BiConsumer<T, Supplier<NetworkEvent.Context>> handler = (message, context) -> {
				MessageBase.invokeStaticMethod(handleMethod, message, context);
			};
			
			FoodPower.NETWORK.registerMessage(
				i,
				self,
				encoder,
				decoder,
				handler
			);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// This is a VERY DANGEROUS method, use in convenience
	private static Object invokeStaticMethod(Method method, Object ...parameters){
		try {
			return method.invoke(null, parameters);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			e.printStackTrace();
			return null;
		}
	}
}