package com.camkey.command;

import com.camkey.tween.Tween;
import com.camkey.tween.Tweens;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public final class TweenArgumentType implements ArgumentType<Tween> {
    private static final DynamicCommandExceptionType UNKNOWN_TWEEN = new DynamicCommandExceptionType(
        name -> Component.literal("Unknown tween: " + name)
    );

    private static final TweenArgumentType INSTANCE = new TweenArgumentType();

    private TweenArgumentType() {
    }

    public static TweenArgumentType tween() {
        return INSTANCE;
    }

    public static Tween getTween(CommandContext<CommandSourceStack> context, String name) {
        return context.getArgument(name, Tween.class);
    }

    @Override
    public Tween parse(StringReader reader) throws CommandSyntaxException {
        String name = reader.readUnquotedString();
        Tween tween = Tweens.find(name);
        if (tween == null) {
            throw UNKNOWN_TWEEN.createWithContext(reader, name);
        }
        return tween;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
        CommandContext<S> context,
        SuggestionsBuilder builder
    ) {
        return SharedSuggestionProvider.suggest(Tweens.names(), builder);
    }

    @Override
    public Collection<String> getExamples() {
        return Tweens.names();
    }
}
