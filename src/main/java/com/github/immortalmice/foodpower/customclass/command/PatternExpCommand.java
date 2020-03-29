package com.github.immortalmice.foodpower.customclass.command;

import java.util.Map;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.CookingPatterns;

public class PatternExpCommand{
	/* Command = /<"foodpower"> <"patternExp"> <target> <"show"||"set"||"reset"> <pattern> <value> */
	private static final LiteralArgumentBuilder<CommandSource> COMMAND = Commands.literal(FoodPower.MODID)
		.requires((param) -> { return param.hasPermissionLevel(2); })
		.then(Commands.literal("patternExp")
			.then(Commands.argument("target", EntityArgument.players())
				.then(Commands.literal("show").executes((context) -> {
					for(ServerPlayerEntity player : EntityArgument.getPlayers(context, "target")){
						player.getCapability(Capabilities.EXP_CAPABILITY, null).ifPresent((capability) -> {
							context.getSource().sendFeedback(new StringTextComponent("Player : " + player.getName().getUnformattedComponentText()), false);
							context.getSource().sendFeedback(new StringTextComponent(PatternExpCommand.parsePatternExpOutput(capability.getAllPatternExpLevel())), false);
						});
					}
					return Command.SINGLE_SUCCESS;
				}))
				.then(Commands.literal("set")
					.then(Commands.argument("pattern", StringArgumentType.word()).suggests((context, builder) -> {
						return ISuggestionProvider.suggest(CookingPatterns.getPatternNames(), builder);
					})
						.then(Commands.argument("value", IntegerArgumentType.integer(0, 99)).executes((context) -> {
							for(ServerPlayerEntity player : EntityArgument.getPlayers(context, "target")){
								player.getCapability(Capabilities.EXP_CAPABILITY, null).ifPresent((capability) -> {
									capability.setPatternExpLevel(CookingPatterns.getPatternByName(StringArgumentType.getString(context, "pattern"))
										, IntegerArgumentType.getInteger(context, "value"));
								});
							}
							context.getSource().sendFeedback(new StringTextComponent("Success"), false);
							return Command.SINGLE_SUCCESS;
						}))
					)
				)
				.then(Commands.literal("reset").executes((context) -> {
					for(ServerPlayerEntity player : EntityArgument.getPlayers(context, "target")){
						player.getCapability(Capabilities.EXP_CAPABILITY, null).ifPresent((capability) -> {
							for(CookingPattern key : capability.getAllPatternExpLevel().keySet()){
								capability.setPatternExpLevel(key, 0);
							}
							context.getSource().sendFeedback(new StringTextComponent("Success Set All PatternExp To 0."), false);
						});
					}
					return Command.SINGLE_SUCCESS;
				}))
			)
		); 

	public static void register(CommandDispatcher<CommandSource> dispatcher){
		dispatcher.register(PatternExpCommand.COMMAND);
	}

	private static String parsePatternExpOutput(Map<CookingPattern, Integer> map){
		String str = "";
		for(CookingPattern key : map.keySet()){
			str += key.getName() + " : Lv." + map.get(key) + "\n";
		}
		return str;
	}
}