package com.camkey.command;

import com.camkey.CamKey;
import com.camkey.CamKeyPaths;
import com.camkey.CamKeyState;
import com.camkey.save.KeyframeFile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.io.UncheckedIOException;

public final class SaveCommand implements CamKeyCommand {
    private static final String FILENAME = "filename";
    private static final int FAILURE = 0;

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("save")
            .executes(context -> save(context.getSource(), CamKeyPaths.DEFAULT_SAVE_NAME))
            .then(Commands.argument(FILENAME, StringArgumentType.word())
                .executes(context -> save(context.getSource(), StringArgumentType.getString(context, FILENAME))));
    }

    private static int save(CommandSourceStack source, String filename) {
        int result;
        try {
            KeyframeFile file = KeyframeFile.of(CamKey.getVersionParts(), CamKeyState.get().keyframes().all());
            file.save(CamKeyPaths.saveFile(filename));
            result = Command.SINGLE_SUCCESS;
        } catch (UncheckedIOException exception) {
            source.sendFailure(Component.literal("Failed to save: " + exception.getMessage()));
            result = FAILURE;
        }
        return result;
    }
}
