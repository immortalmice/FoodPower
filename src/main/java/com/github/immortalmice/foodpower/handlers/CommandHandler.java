package com.github.immortalmice.foodpower.handlers;

import com.github.immortalmice.foodpower.command.FlavorExpCommand;
import com.github.immortalmice.foodpower.command.PatternExpCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;

public class CommandHandler{
	public static void registAllCommand(CommandDispatcher<CommandSource> dispatcher){
		PatternExpCommand.register(dispatcher);
		FlavorExpCommand.register(dispatcher);
	}
}