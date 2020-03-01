package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.message.MessagePack;
import com.github.immortalmice.foodpower.customclass.message.classes.MarketMessage;
import com.github.immortalmice.foodpower.customclass.message.classes.RecipeTableMessage;


public class Messages{
	/* Messages that used in send data from client to server. */
	public static List<MessagePack> list = new ArrayList<MessagePack>();

	public static MessagePack MARKET = new MessagePack(MarketMessage.class);
	public static MessagePack RECIPE_TABLE = new MessagePack(RecipeTableMessage.class);

	/* Regist all messages in list(s) and will call this method in CommonProxy */
	public static void registAllMessage(){
		for(int i = 0; i <= list.size()-1; i ++){
			list.get(i).regist(i);
		}
	}
}