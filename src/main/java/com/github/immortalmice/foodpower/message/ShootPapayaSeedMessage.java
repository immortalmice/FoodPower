package com.github.immortalmice.foodpower.message;

import java.util.function.Supplier;

import com.github.immortalmice.foodpower.baseclass.MessageBase;
import com.github.immortalmice.foodpower.entity.PapayaSeedEntity;
import com.github.immortalmice.foodpower.food.Meal;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.OtherItems;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

public class ShootPapayaSeedMessage extends MessageBase<ShootPapayaSeedMessage>{
	private static float[] probability = {1.0f, 0.8f, 0.5f};

	public static void encode(ShootPapayaSeedMessage msg, PacketBuffer buf){
		return;
	}

	public static ShootPapayaSeedMessage decode(PacketBuffer buf){
		return new ShootPapayaSeedMessage();
	}

	public static void handle(ShootPapayaSeedMessage msg, Supplier<NetworkEvent.Context> ctx){
		ServerPlayerEntity player = ctx.get().getSender();
		ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
		if(stack.getItem() instanceof Meal){
			ListNBT nbt = Meal.getIngredientsListNBT(stack);
			if(!Meal.getIngredients(nbt).contains(Ingredients.Items.PAPAYA)) return;

			int level = Meal.getIngredientLevel(nbt, Ingredients.Items.PAPAYA);
			PapayaSeedEntity papayaSeedEntity = new PapayaSeedEntity(player.world, player);
			papayaSeedEntity.setItem(new ItemStack(OtherItems.Items.PAPAYA_SEED));
			papayaSeedEntity.setIngredientLevel(level);
			papayaSeedEntity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			player.world.addEntity(papayaSeedEntity);

			if(!player.isCreative()
				&& player.world.rand.nextFloat() < ShootPapayaSeedMessage.probability[level - 1]){
				
				stack.shrink(1);
			}
		}
	}
}