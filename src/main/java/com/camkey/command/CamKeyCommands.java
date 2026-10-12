package com.camkey.command;

import com.camkey.CamKey;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

import java.util.List;

@EventBusSubscriber(modid = CamKey.MOD_ID, value = Dist.CLIENT)
public final class CamKeyCommands {
    private static final List<CamKeyCommand> COMMANDS = List.of(
        new VersionCommand(),
        new TweenCommand(),
        new KeyframeCommand(),
        new RollCommand(),
        new PlayCommand(),
        new StopCommand(),
        new SaveCommand(),
        new LoadCommand()
    );

    private CamKeyCommands() {
    }

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(CamKey.MOD_ID);
        for (CamKeyCommand command : COMMANDS) {
            root.then(command.build());
        }
        event.getDispatcher().register(root);
    }
}
