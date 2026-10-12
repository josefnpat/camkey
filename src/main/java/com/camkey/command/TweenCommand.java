package com.camkey.command;

import com.camkey.CamKeyState;
import com.camkey.tween.Tween;
import com.camkey.tween.Tweens;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.stream.Collectors;

public final class TweenCommand implements CamKeyCommand {
    @Override
    public LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("tween")
            .then(Commands.literal("list")
                .executes(context -> list(context.getSource())))
            .then(Commands.literal("default")
                .executes(context -> showDefault(context.getSource()))
                .then(Commands.argument("tween", TweenArgumentType.tween())
                    .executes(TweenCommand::setDefault)));
    }

    private static int list(CommandSourceStack source) {
        String names = Tweens.all().stream()
            .map(Tween::name)
            .collect(Collectors.joining(","));
        source.sendSuccess(() -> Component.literal(names), false);
        return Command.SINGLE_SUCCESS;
    }

    private static int showDefault(CommandSourceStack source) {
        String tween = CamKeyState.get().defaultTween();
        source.sendSuccess(() -> Component.literal(tween), false);
        return Command.SINGLE_SUCCESS;
    }

    private static int setDefault(CommandContext<CommandSourceStack> context) {
        Tween tween = TweenArgumentType.getTween(context, "tween");
        CamKeyState.get().setDefaultTween(tween.name());
        return Command.SINGLE_SUCCESS;
    }
}
