package com.camkey;

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
        event.setRoll(CamKeyState.get().cameraRoll());
    }
}
