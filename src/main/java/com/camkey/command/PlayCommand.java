package com.camkey.command;

import com.camkey.CamKeyPlayback;
import com.camkey.CamKeyState;
import com.camkey.keyframe.Keyframes;
import com.camkey.playback.Playback;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public final class PlayCommand implements CamKeyCommand {
    private static final String TARGET = "target";
    private static final String TIME = "time";
    private static final double MIN_TIME = 0.0;
    private static final int FAILURE = 0;

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("play")
            .executes(context -> play(context.getSource(), Keyframes.START, Playback.NO_LIMIT))
            .then(Commands.argument(TARGET, KeyframeTargetArgumentType.target())
                .executes(context -> play(context.getSource(), target(context), Playback.NO_LIMIT))
                .then(Commands.argument(TIME, DoubleArgumentType.doubleArg(MIN_TIME))
                    .executes(context -> play(
                        context.getSource(),
                        target(context),
                        DoubleArgumentType.getDouble(context, TIME)
                    ))));
    }

    private static String target(CommandContext<CommandSourceStack> context) {
        return KeyframeTargetArgumentType.getTarget(context, TARGET);
    }

    private static int play(CommandSourceStack source, String target, double limit) {
        Keyframes keyframes = CamKeyState.get().keyframes();
        int startIndex = startIndex(keyframes, target);
        int result;
        if (keyframes.names().isEmpty()) {
            result = fail(source, "No Keyframes");
        } else if (startIndex < 0) {
            result = fail(source, "No such keyframe: " + target);
        } else if (startIndex >= keyframes.names().size() - 1) {
            result = fail(source, "Nothing to play after " + target);
        } else {
            CamKeyState.get().playback().start(keyframes.all(), startIndex, limit);
            CamKeyPlayback.applyTransform(CamKeyState.get().playback().transform());
            result = Command.SINGLE_SUCCESS;
        }
        return result;
    }

    private static int startIndex(Keyframes keyframes, String target) {
        int index;
        if (Keyframes.START.equals(target)) {
            index = 0;
        } else {
            index = keyframes.names().indexOf(target);
        }
        return index;
    }

    private static int fail(CommandSourceStack source, String message) {
        source.sendFailure(Component.literal(message));
        return FAILURE;
    }
}
