package com.github.immortalmice.foodpower.handlers;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;

import com.github.immortalmice.foodpower.customclass.command.PatternExpCommand;

public class CommandHandler{
	public static void registAllCommand(CommandDispatcher<CommandSource> dispatcher){
		PatternExpCommand.register(dispatcher);
	}
}