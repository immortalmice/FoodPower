package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.message.ToServerMessagePack;
import com.github.immortalmice.foodpower.customclass.message.classes.MarketMessage;
import com.github.immortalmice.foodpower.customclass.message.classes.RecipeTableMessage;


public class Messages{
	/* Messages that used in send data from client to server. */
	public static List<ToServerMessagePack<?>> sendToServerList = new ArrayList<ToServerMessagePack<?>>();

	public static ToServerMessagePack<MarketMessage> MARKET = new ToServerMessagePack<MarketMessage>(MarketMessage.class, MarketMessage.Handler.class);
	public static ToServerMessagePack<RecipeTableMessage> RECIPE_TABLE = new ToServerMessagePack<RecipeTableMessage>(RecipeTableMessage.class, RecipeTableMessage.Handler.class);

	/* Regist all messages in list(s) and will call this method in CommonProxy */
	public static void regist(){
		for(int i = 0; i <= sendToServerList.size()-1; i ++){
			sendToServerList.get(i).registThis(i);
		}
	}
}