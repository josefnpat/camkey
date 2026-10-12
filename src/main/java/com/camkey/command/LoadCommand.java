package com.camkey.command;

import com.camkey.CamKey;
import com.camkey.CamKeyPaths;
import com.camkey.CamKeyState;
import com.camkey.save.KeyframeFile;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public final class LoadCommand implements CamKeyCommand {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String FILENAME = "filename";
    private static final int FAILURE = 0;

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("load")
            .executes(context -> load(context.getSource(), CamKeyPaths.DEFAULT_SAVE_NAME))
            .then(Commands.argument(FILENAME, StringArgumentType.word())
                .executes(context -> load(context.getSource(), StringArgumentType.getString(context, FILENAME))));
    }

    private static int load(CommandSourceStack source, String filename) {
        Path path = CamKeyPaths.saveFile(filename);
        int result;
        if (!Files.exists(path)) {
            source.sendFailure(Component.literal("No such save file: " + path.getFileName()));
            result = FAILURE;
        } else {
            result = loadFile(source, path);
        }
        return result;
    }

    private static int loadFile(CommandSourceStack source, Path path) {
        int result;
        try {
            KeyframeFile file = KeyframeFile.read(path);
            warnIfVersionMismatch(file.version());
            CamKeyState.get().keyframes().replaceAll(file.toKeyframes());
            result = Command.SINGLE_SUCCESS;
        } catch (UncheckedIOException | JsonSyntaxException | IllegalStateException exception) {
            source.sendFailure(Component.literal("Failed to load: " + exception.getMessage()));
            result = FAILURE;
        }
        return result;
    }

    private static void warnIfVersionMismatch(int[] version) {
        if (!Arrays.equals(version, CamKey.getVersionParts())) {
            LOGGER.warn(
                "camkey save version {} does not match mod version {}",
                Arrays.toString(version),
                Arrays.toString(CamKey.getVersionParts())
            );
        }
    }
}
