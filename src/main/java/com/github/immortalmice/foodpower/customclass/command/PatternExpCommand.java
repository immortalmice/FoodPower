package com.github.immortalmice.foodpower.customclass.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.command.CommandSource;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.lists.Capabilities;

public class PatternExpCommand{
	private static final LiteralArgumentBuilder<CommandSource> COMMAND = Commands.literal(FoodPower.MODID)
		.requires((param) -> { return param.hasPermissionLevel(2); })
		.then(Commands.literal("patternExp")
			.then(Commands.literal("show").executes((source) -> {
				source.getSource().getEntity().getCapability(Capabilities.EXP_CAPABILITY).ifPresent((capability) -> {
					source.getSource().sendFeedback(new StringTextComponent(capability.getFullPatternExpToString()), false);
				});
				return 1;
			}))); 

	public static void register(CommandDispatcher<CommandSource> dispatcher){
		dispatcher.register(PatternExpCommand.COMMAND);
	}
}