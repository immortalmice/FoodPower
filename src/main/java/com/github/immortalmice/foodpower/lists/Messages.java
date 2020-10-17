package com.github.immortalmice.foodpower.lists;

import com.github.immortalmice.foodpower.baseclass.MessageBase;
import com.github.immortalmice.foodpower.message.MarketMessage;
import com.github.immortalmice.foodpower.message.RecipeScrollMessage;
import com.github.immortalmice.foodpower.message.RecipeTableMessage;
import com.github.immortalmice.foodpower.message.ShootPapayaSeedMessage;
import com.github.immortalmice.foodpower.util.ReflectList;


public class Messages{
	/* Messages that used in send data from client to server. */
	@SuppressWarnings("rawtypes")
	public static ReflectList<MessageBase, Messages> list = new ReflectList<MessageBase, Messages>(MessageBase.class, Messages.class, true);

	public static final MarketMessage MARKET = new MarketMessage();
	public static final RecipeTableMessage RECIPE_TABLE = new RecipeTableMessage();
	public static final RecipeScrollMessage RECIPE_SCROLL = new RecipeScrollMessage();
	public static final ShootPapayaSeedMessage SHOOT_PAPAYA_SEED = new ShootPapayaSeedMessage();

	/* Regist all messages in list(s) and will call this method in CommonProxy */
	public static void registAllMessage(){
		for(int i = 0; i <= list.size()-1; i ++){
			Messages.list.get(i).registMessage(i);
		}
	}
}