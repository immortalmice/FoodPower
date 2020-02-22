package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

//import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

//import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.message.ToServerMessagePack;
import com.github.immortalmice.foodpower.customclass.message.classes.MarketMessage;
import com.github.immortalmice.foodpower.customclass.message.classes.RecipeTableMessage;


public class Messages{
	public static List<ToServerMessagePack<? extends IMessage>> sendToServerList = new ArrayList<ToServerMessagePack<? extends IMessage>>();

	public static ToServerMessagePack<MarketMessage> MARKET = new ToServerMessagePack<MarketMessage>(MarketMessage.class, MarketMessage.Handler.class);
	public static ToServerMessagePack<RecipeTableMessage> RECIPE_TABLE = new ToServerMessagePack<RecipeTableMessage>(RecipeTableMessage.class, RecipeTableMessage.Handler.class);

	public static void regist(){
		for(int i = 0; i <= sendToServerList.size()-1; i ++){
			/*
			ToServerMessagePack<? extends IMessage> element = sendToServerList.get(i);
			FoodPower.network.registerMessage(element.getIMessageClass(), element.getHandlerClass(), i, Side.SERVER);
			*/
		}
	}
}