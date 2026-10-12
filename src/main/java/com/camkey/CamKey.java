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

    public static int[] getVersionParts() {
        return parseVersion(getVersion());
    }

    static int[] parseVersion(String version) {
        String[] parts = version.split("\\.");
        int[] parsed = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            parsed[i] = Integer.parseInt(parts[i]);
        }
        return parsed;
    }
}
