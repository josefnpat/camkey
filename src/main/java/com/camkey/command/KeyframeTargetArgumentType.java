package com.camkey.command;

import com.camkey.CamKeyState;
import com.camkey.keyframe.Keyframes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class KeyframeTargetArgumentType implements ArgumentType<String> {
    private static final KeyframeTargetArgumentType INSTANCE = new KeyframeTargetArgumentType();

    private KeyframeTargetArgumentType() {
    }

    public static KeyframeTargetArgumentType target() {
        return INSTANCE;
    }

    public static String getTarget(CommandContext<CommandSourceStack> context, String name) {
        return context.getArgument(name, String.class);
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        int start = reader.getCursor();
        while (reader.canRead() && isAllowed(reader.peek())) {
            reader.skip();
        }
        if (reader.getCursor() == start) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(reader);
        }
        return reader.getString().substring(start, reader.getCursor());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
        CommandContext<S> context,
        SuggestionsBuilder builder
    ) {
        return SharedSuggestionProvider.suggest(suggestions(), builder);
    }

    @Override
    public Collection<String> getExamples() {
        return suggestions();
    }

    private static List<String> suggestions() {
        List<String> targets = new ArrayList<>();
        targets.add(Keyframes.START);
        targets.add(Keyframes.END);
        targets.addAll(CamKeyState.get().keyframes().names());
        return targets;
    }

    private static boolean isAllowed(char character) {
        return !Character.isWhitespace(character) && character != ',';
    }
}
