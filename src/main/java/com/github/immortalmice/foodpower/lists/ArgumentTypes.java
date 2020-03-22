package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;

import com.github.immortalmice.foodpower.customclass.cooking.CookingPattern;

public class ArgumentTypes{
	public static class PatternArgument implements ArgumentType<String>{
		private StringArgumentType instance;
		public PatternArgument(){
			this.instance = StringArgumentType.word();
		}

		public static String getPattern(final CommandContext<?> context, final String name){
			return StringArgumentType.getString(context, name);
		}

		@Override
    	public String parse(final StringReader reader) throws CommandSyntaxException{
    		return this.instance.parse(reader);
    	}

    	@Override
    	public Collection<String> getExamples(){
    		return this.instance.getExamples();
    	}

    	@Override
		public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder){
			List<String> suggests = new ArrayList<String>();
			for(CookingPattern pattern : CookingPatterns.list){
				suggests.add(pattern.getName());
			}
			return ISuggestionProvider.suggest(suggests, builder);
		}
	}
}