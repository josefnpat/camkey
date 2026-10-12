package com.camkey;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;

@Mod(CamKey.MOD_ID)
public final class CamKey {
    public static final String MOD_ID = "camkey";

    public CamKey(IEventBus modEventBus) {
    }

    public static String getVersion() {
        return ModList.get()
            .getModContainerById(MOD_ID)
            .orElseThrow()
            .getModInfo()
            .getVersion()
            .toString();
    }
}
