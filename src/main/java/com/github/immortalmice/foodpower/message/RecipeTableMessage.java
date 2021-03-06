package com.github.immortalmice.foodpower.message;

import java.util.function.Supplier;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;

import com.github.immortalmice.foodpower.baseclass.MessageBase;
import com.github.immortalmice.foodpower.container.recipetable.RecipeTableContainer;
import com.github.immortalmice.foodpower.cooking.CookingPattern;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.CookingPatterns;
import com.github.immortalmice.foodpower.specialclass.RecipeScroll;

/* Used to transfer RecipeTableScreen data to update */
public class RecipeTableMessage extends MessageBase<RecipeTableMessage>{
	private String action, message;
	private int windowId;
	private ItemStack scroll;

	private static final int LEVEL_REQUIRED = 15;

	public RecipeTableMessage(int windowidIn, String actionIn, String messageIn, ItemStack scrollIn){
		this.windowId = windowidIn;
		this.action = actionIn;
		this.message = messageIn;
		this.scroll = scrollIn;
	}

	public RecipeTableMessage(int windowidIn, String actionIn, String messageIn){
		this(windowidIn, actionIn, messageIn, ItemStack.EMPTY);
	}

	public RecipeTableMessage(int windowidIn, String actionIn, ItemStack scrollIn){
		this(windowidIn, actionIn, "", scrollIn);
	}

	public RecipeTableMessage(){
		this(0, "", "");
	}

	public static void encode(RecipeTableMessage msg, PacketBuffer buf){
		buf.writeInt(msg.getWindowId());
		buf.writeString(msg.getAction());
		buf.writeString(msg.getMessage());
		buf.writeItemStack(msg.getScroll(), false);
	}

	public static RecipeTableMessage decode(PacketBuffer buf){
		return new RecipeTableMessage(
			buf.readInt()
			, buf.readString(32767)
			, buf.readString(32767)
			, buf.readItemStack());
	}

	/* Get the container and update data */
	public static void handle(RecipeTableMessage msg, Supplier<NetworkEvent.Context> ctx){
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
						RecipeTableMessage.handleInitRecipeScroll(player, container, msg.getScroll());
						break;
				}
			}
		}
		ctx.get().setPacketHandled(true);
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

	public ItemStack getScroll(){
		return this.scroll;
	}

	private static void handleInitRecipeScroll(ServerPlayerEntity player, RecipeTableContainer container, ItemStack scroll){
		if(!player.abilities.isCreativeMode && player.experienceLevel < RecipeTableMessage.LEVEL_REQUIRED)
			return;

		CookingPattern pattern = CookingPatterns.list.get(container.getIndex());
		player.getCapability(Capabilities.PATTERN_EXP_CAPABILITY, null).ifPresent((capability) -> {
			int rarity = 0;
			int level = capability.getExpLevel(pattern);
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

	        if(level == 99) rarity = 3;

	        if(scroll.getItem() instanceof RecipeScroll){
	        	/* Give a random float 0.9 ~ 1.1 to randomize ingredient amount in need */
	        	RecipeScroll.initStack(scroll, rarity, serverWorld.rand.nextFloat() * 0.2f + 0.9f);
	        	player.inventory.setItemStack(scroll);
	        	player.updateHeldItem();
	        	player.addExperienceLevel(RecipeTableMessage.LEVEL_REQUIRED * -1);
	        	container.setScrollTaken();
	        }
		});

	}
}