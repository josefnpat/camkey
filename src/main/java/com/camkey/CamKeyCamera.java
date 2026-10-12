package com.camkey;

import com.camkey.playback.Playback;
import com.camkey.playback.Transform;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = CamKey.MOD_ID, value = Dist.CLIENT)
public final class CamKeyCamera {
    private CamKeyCamera() {
    }

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Playback playback = CamKeyState.get().playback();
        if (playback.playing()) {
            Transform transform = playback.transform(event.getPartialTick() * Playback.TICK_SECONDS);
            event.setPitch(transform.pitch());
            event.setYaw(transform.yaw());
            event.setRoll(transform.roll());
        } else {
            event.setRoll(CamKeyState.get().cameraRoll());
        }
    }
}
