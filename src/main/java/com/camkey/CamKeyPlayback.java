package com.camkey;

import com.camkey.playback.Playback;
import com.camkey.playback.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = CamKey.MOD_ID, value = Dist.CLIENT)
public final class CamKeyPlayback {
    private CamKeyPlayback() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Playback playback = CamKeyState.get().playback();
        if (playback.playing()) {
            playback.tick(Playback.TICK_SECONDS);
            applyTransform(playback.transform());
        }
    }

    public static void applyTransform(Transform transform) {
        LocalPlayer player = Minecraft.getInstance().player;
        player.setPos(
            transform.x(),
            transform.y() - player.getEyeHeight(),
            transform.z()
        );
        player.setYRot(transform.yaw());
        player.setXRot(transform.pitch());
    }
}
