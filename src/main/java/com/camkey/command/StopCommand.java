package com.camkey.command;

import com.camkey.CamKeyState;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public final class StopCommand implements CamKeyCommand {
    @Override
    public LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("stop")
            .executes(context -> stop());
    }

    private static int stop() {
        CamKeyState.get().playback().stop();
        return Command.SINGLE_SUCCESS;
    }
}
