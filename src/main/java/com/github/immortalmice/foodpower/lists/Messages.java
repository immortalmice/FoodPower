package com.github.immortalmice.foodpower.lists;

import com.github.immortalmice.foodpower.baseclass.MessageBase;
import com.github.immortalmice.foodpower.customclass.message.MarketMessage;
import com.github.immortalmice.foodpower.customclass.message.RecipeScrollMessage;
import com.github.immortalmice.foodpower.customclass.message.RecipeTableMessage;
import com.github.immortalmice.foodpower.customclass.message.ShootPapayaSeedMessage;
import com.github.immortalmice.foodpower.customclass.util.ReflectList;


public class Messages{
	/* Messages that used in send data from client to server. */
	@SuppressWarnings("rawtypes")
	public static ReflectList<MessageBase, Messages> list = new ReflectList<MessageBase, Messages>(MessageBase.class, Messages.class, true);

	public static MarketMessage MARKET = new MarketMessage();
	public static RecipeTableMessage RECIPE_TABLE = new RecipeTableMessage();
	public static RecipeScrollMessage RECIPE_SCROLL = new RecipeScrollMessage();
	public static ShootPapayaSeedMessage SHOOT_PAPAYA_SEED = new ShootPapayaSeedMessage();

	/* Regist all messages in list(s) and will call this method in CommonProxy */
	public static void registAllMessage(){
		for(int i = 0; i <= list.size()-1; i ++){
			Messages.list.get(i).registMessage(i);
		}
	}
}