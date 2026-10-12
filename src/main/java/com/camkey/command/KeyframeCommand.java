package com.camkey.command;

import com.camkey.CamKeyState;
import com.camkey.keyframe.Keyframe;
import com.camkey.keyframe.Keyframes;
import com.camkey.tween.Tweens;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Locale;

public final class KeyframeCommand implements CamKeyCommand {
    private static final String NAME = "name";
    private static final String NEW_NAME = "new-name";
    private static final String TARGET = "target-name";
    private static final String TIME = "time";
    private static final String TWEEN = "tween";
    private static final String NO_KEYFRAMES = "No Keyframes";
    private static final String SHOW_FORMAT = "%.1f [%.1f,%.1f,%.1f] @ [%.1f,%.1f,%.1f]";
    private static final double MIN_TIME = 0.0;
    private static final int FAILURE = 0;

    private static final SuggestionProvider<CommandSourceStack> EXISTING_NAMES =
        (context, builder) -> SharedSuggestionProvider.suggest(CamKeyState.get().keyframes().names(), builder);

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("keyframe")
            .then(Commands.literal("list")
                .executes(context -> list(context.getSource())))
            .then(Commands.literal("show")
                .then(Commands.argument(NAME, StringArgumentType.word())
                    .suggests(EXISTING_NAMES)
                    .executes(KeyframeCommand::show)))
            .then(Commands.literal("move")
                .then(Commands.argument(NAME, StringArgumentType.word())
                    .suggests(EXISTING_NAMES)
                    .then(Commands.argument(TARGET, KeyframeTargetArgumentType.target())
                        .executes(KeyframeCommand::move))))
            .then(Commands.literal("add")
                .then(Commands.argument(NAME, StringArgumentType.word())
                    .then(Commands.argument(TIME, DoubleArgumentType.doubleArg())
                        .executes(KeyframeCommand::addWithDefaultTween)
                        .then(Commands.argument(TWEEN, TweenArgumentType.tween())
                            .executes(KeyframeCommand::addWithTween)))))
            .then(Commands.literal("remove")
                .then(Commands.argument(NAME, StringArgumentType.word())
                    .suggests(EXISTING_NAMES)
                    .executes(KeyframeCommand::remove)))
            .then(Commands.literal("rename")
                .then(Commands.argument(NAME, StringArgumentType.word())
                    .suggests(EXISTING_NAMES)
                    .then(Commands.argument(NEW_NAME, StringArgumentType.word())
                        .executes(KeyframeCommand::rename))))
            .then(Commands.literal("load")
                .then(Commands.argument(NAME, StringArgumentType.word())
                    .suggests(EXISTING_NAMES)
                    .executes(KeyframeCommand::load)));
    }

    private static int list(CommandSourceStack source) {
        List<String> names = CamKeyState.get().keyframes().names();
        String message = names.isEmpty() ? NO_KEYFRAMES : String.join(",", names);
        source.sendSuccess(() -> Component.literal(message), false);
        return Command.SINGLE_SUCCESS;
    }

    private static int show(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String name = StringArgumentType.getString(context, NAME);
        Keyframe keyframe = CamKeyState.get().keyframes().find(name);
        int result;
        if (keyframe == null) {
            result = fail(source, "No such keyframe: " + name);
        } else {
            source.sendSuccess(() -> Component.literal(formatTransform(keyframe)), false);
            result = Command.SINGLE_SUCCESS;
        }
        return result;
    }

    private static int move(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        Keyframes keyframes = CamKeyState.get().keyframes();
        String name = StringArgumentType.getString(context, NAME);
        String target = KeyframeTargetArgumentType.getTarget(context, TARGET);
        int result;
        if (!keyframes.contains(name)) {
            result = fail(source, "No such keyframe: " + name);
        } else if (!isValidTarget(keyframes, target)) {
            result = fail(source, "No such target: " + target);
        } else if (target.equals(name)) {
            result = Command.SINGLE_SUCCESS;
        } else {
            keyframes.moveAfter(name, target);
            result = Command.SINGLE_SUCCESS;
        }
        return result;
    }

    private static int addWithDefaultTween(CommandContext<CommandSourceStack> context) {
        return add(context, CamKeyState.get().defaultTween());
    }

    private static int addWithTween(CommandContext<CommandSourceStack> context) {
        return add(context, TweenArgumentType.getTween(context, TWEEN).name());
    }

    private static int add(CommandContext<CommandSourceStack> context, String tween) {
        CommandSourceStack source = context.getSource();
        Keyframes keyframes = CamKeyState.get().keyframes();
        String name = StringArgumentType.getString(context, NAME);
        double time = DoubleArgumentType.getDouble(context, TIME);
        int result;
        if (keyframes.contains(name)) {
            result = fail(source, "Keyframe already exists: " + name);
        } else if (time <= MIN_TIME) {
            result = fail(source, "Time must be greater than 0");
        } else if (Tweens.find(tween) == null) {
            result = fail(source, "Unsupported tween: " + tween);
        } else {
            LocalPlayer player = Minecraft.getInstance().player;
            keyframes.add(new Keyframe(
                name,
                time,
                player.getX(),
                player.getEyeY(),
                player.getZ(),
                player.getXRot(),
                player.getYRot(),
                CamKeyState.get().cameraRoll(),
                tween
            ));
            result = Command.SINGLE_SUCCESS;
        }
        return result;
    }

    private static int remove(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String name = StringArgumentType.getString(context, NAME);
        int result;
        if (CamKeyState.get().keyframes().remove(name)) {
            result = Command.SINGLE_SUCCESS;
        } else {
            result = fail(source, "No such keyframe: " + name);
        }
        return result;
    }

    private static int rename(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        Keyframes keyframes = CamKeyState.get().keyframes();
        String name = StringArgumentType.getString(context, NAME);
        String newName = StringArgumentType.getString(context, NEW_NAME);
        int result;
        if (!keyframes.contains(name)) {
            result = fail(source, "No such keyframe: " + name);
        } else if (keyframes.contains(newName)) {
            result = fail(source, "Keyframe already exists: " + newName);
        } else {
            keyframes.rename(name, newName);
            result = Command.SINGLE_SUCCESS;
        }
        return result;
    }

    private static int load(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String name = StringArgumentType.getString(context, NAME);
        Keyframe keyframe = CamKeyState.get().keyframes().find(name);
        int result;
        if (keyframe == null) {
            result = fail(source, "No such keyframe: " + name);
        } else {
            LocalPlayer player = Minecraft.getInstance().player;
            player.absMoveTo(
                keyframe.x(),
                keyframe.y() - player.getEyeHeight(),
                keyframe.z(),
                keyframe.yaw(),
                keyframe.pitch()
            );
            CamKeyState.get().setCameraRoll(keyframe.roll());
            result = Command.SINGLE_SUCCESS;
        }
        return result;
    }

    private static boolean isValidTarget(Keyframes keyframes, String target) {
        return Keyframes.START.equals(target) || Keyframes.END.equals(target) || keyframes.contains(target);
    }

    static String formatTransform(Keyframe keyframe) {
        return String.format(
            Locale.ROOT,
            SHOW_FORMAT,
            keyframe.time(),
            keyframe.x(),
            keyframe.y(),
            keyframe.z(),
            keyframe.pitch(),
            keyframe.yaw(),
            keyframe.roll()
        );
    }

    private static int fail(CommandSourceStack source, String message) {
        source.sendFailure(Component.literal(message));
        return FAILURE;
    }
}
