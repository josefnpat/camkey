package com.camkey;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@EventBusSubscriber(modid = CamKey.MOD_ID, value = Dist.CLIENT)
public final class CamKeyCommands {
    private CamKeyCommands() {
    }

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        register(event.getDispatcher());
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal(CamKey.MOD_ID)
                .then(Commands.literal("version").executes(context -> showVersion(context.getSource())))
        );
    }

    private static int showVersion(CommandSourceStack source) {
        source.sendSuccess(
            () -> Component.literal(CamKey.getVersion()).withStyle(ChatFormatting.YELLOW),
            false
        );
        return 1;
    }
}
