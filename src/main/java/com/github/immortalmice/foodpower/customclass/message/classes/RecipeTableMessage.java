package com.github.immortalmice.foodpower.customclass.message.classes;

import java.util.function.Supplier;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.IMessageBase;
import com.github.immortalmice.foodpower.customclass.container.classes.recipetable.RecipeTableContainer;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.customclass.specialclass.RecipeScroll;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

/* Used to trasfer RecipeTableScreen data to update */
public class RecipeTableMessage implements IMessageBase<RecipeTableMessage>{
	private String action, message;
	private int windowId;

	public RecipeTableMessage(int windowidIn, String actionIn, String messageIn){
		this.windowId = windowidIn;
		this.action = actionIn;
		this.message = messageIn;
	}
	public RecipeTableMessage(){
		this(0, "", "");
	}

	@Override
	public void encode(RecipeTableMessage msg, PacketBuffer buf){
		buf.writeInt(msg.getWindowId());
		buf.writeString(msg.getAction());
		buf.writeString(msg.getMessage());
	}

	@Override
	public RecipeTableMessage decode(PacketBuffer buf){
		this.windowId = buf.readInt();
		this.action = buf.readString(32767);
		this.message = buf.readString(32767);
		return this;
	}

	/* Get the container and update data */
	@Override
	public void handle(RecipeTableMessage msg, Supplier<NetworkEvent.Context> ctx){
		ServerPlayerEntity player = ctx.get().getSender();
		if(player.openContainer instanceof RecipeTableContainer){
			RecipeTableContainer container = (RecipeTableContainer) player.openContainer;
			if(container.getWindowId() == msg.getWindowId()){
				switch(msg.getAction()){
					case "Set Index":
						switch(msg.getMessage()){
							case "Decrease":
								container.decreaseIndex();
								break;
							case "Increase":
								container.increaseIndex();
								break;
							default:
						}
						break;
					case "Set InputText":
						container.setInputText(msg.getMessage());
						break;
					/* Valid player is in creative mode or not */
					case "Try Give Meal":
						if(player.abilities.isCreativeMode){
							player.addItemStackToInventory(container.getFinishedMeal(64));
						}
						break;
					case "Init Recipe Scroll":
						RecipeTableMessage.handleInitRecipeScroll(player, container);

				}
			}
		}
		ctx.get().setPacketHandled(true);
	}

	@Override
	public void registMessage(int i) {
		FoodPower.NETWORK.registerMessage(
			i
			, RecipeTableMessage.class
			, this::encode
			, this::decode
			, this::handle);
	}

	public int getWindowId(){
		return this.windowId;
	}

	public String getAction(){
		return this.action;
	}

	public String getMessage(){
		return this.message;
	}

	private static void handleInitRecipeScroll(ServerPlayerEntity player, RecipeTableContainer container){
		CookingPattern pattern = CookingPatterns.list.get(container.getIndex());
		player.getCapability(Capabilities.EXP_CAPABILITY, null).ifPresent((capability) -> {
			int rarity = 0;
			int level = capability.getPatternExpLevel(pattern);
			ServerWorld serverWorld = player.getServerWorld();
			int rand = serverWorld.rand.nextInt(100) + 1;

			int para_1 = level;
	        int para_2 = 100 - para_1;

	        double[] chance = {0, 0, 0, 0};
	        chance[0] = para_2 * para_2 / 100; // Wood Chance (%)
	        chance[3] = para_1 * para_1 / 100; // Diamond Chance (%)
	        chance[1] = (100 - chance[0] - chance[3]) * para_2 / 100; // Iron Chance (%)
	        chance[2] = (100 - chance[0] - chance[3]) * para_1 / 100; // Gold Chance (%)

	        int i = 0;
	        while(i < 3){
	            rand -= chance[i];
	            if(rand < 0){
	                rarity = i;
	                break;
	            }
	            rarity = ++ i;
	        }

	        ItemStack stack = player.inventory.getItemStack();
	        if(stack.getItem() instanceof RecipeScroll){
	        	/* Give a random float 0.9 ~ 1.1 to randomize ingredient amount in need */
	        	RecipeScroll.initStack(stack, rarity, serverWorld.rand.nextFloat() * 0.2f + 0.9f);
	        	player.updateHeldItem();
	        }
		});
	}
}