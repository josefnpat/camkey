package com.camkey.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public interface CamKeyCommand {
    LiteralArgumentBuilder<CommandSourceStack> build();
}
