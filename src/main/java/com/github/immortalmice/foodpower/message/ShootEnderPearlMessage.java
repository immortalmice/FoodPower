package com.github.immortalmice.foodpower.message;

import java.util.function.Supplier;

import com.github.immortalmice.foodpower.baseclass.MessageBase;
import com.github.immortalmice.foodpower.food.Meal;
import com.github.immortalmice.foodpower.lists.Ingredients;

import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

public class ShootEnderPearlMessage extends MessageBase<ShootEnderPearlMessage>{
	private static float[] probability = {1.0f, 1.0f, 0.5f};
	
	public static void encode(ShootEnderPearlMessage msg, PacketBuffer buf){
		return;
	}

	public static ShootEnderPearlMessage decode(PacketBuffer buf){
		return new ShootEnderPearlMessage();
	}

	public static void handle(ShootEnderPearlMessage msg, Supplier<NetworkEvent.Context> ctx){
		ServerPlayerEntity player = ctx.get().getSender();
		ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
		if(stack.getItem() instanceof Meal){
			ListNBT nbt = Meal.getIngredientsListNBT(stack);
			if(!Meal.getIngredients(nbt).contains(Ingredients.Items.FERMENTED_ENDEREYE)) return;

			int level = Meal.getIngredientLevel(nbt, Ingredients.Items.FERMENTED_ENDEREYE);
			if(level < 2) return;
			
			EnderPearlEntity enderPearlEntity = new EnderPearlEntity(player.world, player);
			enderPearlEntity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			player.world.addEntity(enderPearlEntity);

			if(!player.isCreative()
				&& player.world.rand.nextFloat() < ShootEnderPearlMessage.probability[level - 1]){
				
				stack.shrink(1);
			}
		}
	}
}
