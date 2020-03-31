package com.github.immortalmice.foodpower.customclass.command;

import java.util.List;
import java.util.Map;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.capability.classes.FPFlavorExpCapability;
import com.github.immortalmice.foodpower.customclass.flavor.FlavorType;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.FlavorTypes;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class FlavorExpCommand{
	/* Command = /<"foodpower"> <"flavorExp"> <target> <"show"||"set"||"reset"> <flavor> <value> */
	private static final LiteralArgumentBuilder<CommandSource> COMMAND = Commands.literal(FoodPower.MODID)
		.requires((param) -> { return param.hasPermissionLevel(2); })
		.then(Commands.literal("flavorExp")
			.then(Commands.argument("target", EntityArgument.players())
				.then(Commands.literal("show").executes((context) -> {
					for(ServerPlayerEntity player : EntityArgument.getPlayers(context, "target")){
						player.getCapability(Capabilities.FLAVOR_EXP_CAPABILITY, null).ifPresent((capability) -> {
							context.getSource().sendFeedback(new StringTextComponent("Player : " + player.getName().getUnformattedComponentText()), false);
							context.getSource().sendFeedback(new StringTextComponent(FlavorExpCommand.parseFlavorExpOutput(capability.getAllExpLevel())), false);
						});
					}
					return Command.SINGLE_SUCCESS;
				}))
				.then(Commands.literal("set")
					.then(Commands.argument("pattern", StringArgumentType.word()).suggests((context, builder) -> {
						List<String> names = FlavorTypes.getFlavorNames();
						names.remove("none");
						return ISuggestionProvider.suggest(names, builder);
					})
						.then(Commands.argument("value", IntegerArgumentType.integer(FPFlavorExpCapability.MIN_LEVEL, FPFlavorExpCapability.MAX_LEVEL)).executes((context) -> {
							for(ServerPlayerEntity player : EntityArgument.getPlayers(context, "target")){
								player.getCapability(Capabilities.FLAVOR_EXP_CAPABILITY, null).ifPresent((capability) -> {
									capability.setExpLevel(FlavorTypes.getFlavorByName(StringArgumentType.getString(context, "pattern"))
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
						player.getCapability(Capabilities.FLAVOR_EXP_CAPABILITY, null).ifPresent((capability) -> {
							for(FlavorType key : capability.getAllExpLevel().keySet()){
								capability.setExpLevel(key, 0);
							}
							context.getSource().sendFeedback(new StringTextComponent("Success Set All PatternExp To 0."), false);
						});
					}
					return Command.SINGLE_SUCCESS;
				}))
			)
		);

	public static void register(CommandDispatcher<CommandSource> dispatcher){
		dispatcher.register(FlavorExpCommand.COMMAND);
	}

	private static String parseFlavorExpOutput(Map<FlavorType, Integer> map){
		String str = "";
		for(FlavorType key : map.keySet()){
			str += key.getName() + " : Lv." + map.get(key) + "\n";
		}
		return str;
	}
}