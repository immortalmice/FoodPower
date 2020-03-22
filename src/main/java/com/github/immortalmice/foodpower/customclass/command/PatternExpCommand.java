package com.github.immortalmice.foodpower.customclass.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.Commands;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.ArgumentTypes.PatternArgument;

public class PatternExpCommand{
	private static final LiteralArgumentBuilder<CommandSource> COMMAND = Commands.literal(FoodPower.MODID)
		.requires((param) -> { return param.hasPermissionLevel(2); })
		.then(Commands.literal("patternExp")
			.then(Commands.argument("target", EntityArgument.players())
				.then(Commands.literal("show").executes((context) -> {
					for(ServerPlayerEntity player : EntityArgument.getPlayers(context, "target")){
						player.getCapability(Capabilities.EXP_CAPABILITY, null).ifPresent((capability) -> {
							context.getSource().sendFeedback(new StringTextComponent("Player : " + player.getName().getUnformattedComponentText()), true);
							context.getSource().sendFeedback(new StringTextComponent(capability.getFullPatternExpToString()), true);
						});
					}
					return Command.SINGLE_SUCCESS;
				}))
				.then(Commands.literal("set")
					.then(Commands.argument("pattern", new PatternArgument())
						.then(Commands.argument("value", IntegerArgumentType.integer(0, 99)).executes((context) -> {
							for(ServerPlayerEntity player : EntityArgument.getPlayers(context, "target")){
								player.getCapability(Capabilities.EXP_CAPABILITY, null).ifPresent((capability) -> {
									capability.setPatternExp(PatternArgument.getPattern(context, "pattern"), IntegerArgumentType.getInteger(context, "value"));
								});
							}
							context.getSource().sendFeedback(new StringTextComponent("Success"), false);
							return Command.SINGLE_SUCCESS;
						}))
					)
				)
			)
		); 

	public static void register(CommandDispatcher<CommandSource> dispatcher){
		dispatcher.register(PatternExpCommand.COMMAND);
	}
}