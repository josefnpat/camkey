package com.camkey.command;

import com.camkey.CamKeyState;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public final class RollCommand implements CamKeyCommand {
    private static final String ANGLE = "angle";

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("roll")
            .executes(context -> showRoll(context.getSource()))
            .then(Commands.argument(ANGLE, FloatArgumentType.floatArg())
                .executes(RollCommand::setRoll));
    }

    private static int showRoll(CommandSourceStack source) {
        float roll = CamKeyState.get().cameraRoll();
        source.sendSuccess(() -> Component.literal(Float.toString(roll)), false);
        return Command.SINGLE_SUCCESS;
    }

    private static int setRoll(CommandContext<CommandSourceStack> context) {
        float roll = FloatArgumentType.getFloat(context, ANGLE);
        CamKeyState.get().setCameraRoll(roll);
        return Command.SINGLE_SUCCESS;
    }
}
