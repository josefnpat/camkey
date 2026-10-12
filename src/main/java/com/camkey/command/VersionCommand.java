package com.camkey.command;

import com.camkey.CamKey;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public final class VersionCommand implements CamKeyCommand {
    @Override
    public LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("version")
            .executes(context -> showVersion(context.getSource()));
    }

    private static int showVersion(CommandSourceStack source) {
        source.sendSuccess(
            () -> Component.literal(CamKey.getVersion()).withStyle(ChatFormatting.YELLOW),
            false
        );
        return Command.SINGLE_SUCCESS;
    }
}
