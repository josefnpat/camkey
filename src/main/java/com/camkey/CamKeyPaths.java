package com.camkey;

import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class CamKeyPaths {
    public static final String DEFAULT_SAVE_NAME = "default";

    private static final String MOD_FOLDER = "mods";
    private static final String CAMKEY_FOLDER = "camkey";
    private static final String CONFIG_FILE = "config.json";
    private static final String SAVE_FOLDER = "save";
    private static final String JSON_EXTENSION = ".json";

    private CamKeyPaths() {
    }

    public static Path configFile() {
        return base().resolve(CONFIG_FILE);
    }

    public static Path saveFile(String filename) {
        return base().resolve(SAVE_FOLDER).resolve(saveFileName(filename));
    }

    public static String saveFileName(String filename) {
        return filename.endsWith(JSON_EXTENSION) ? filename : filename + JSON_EXTENSION;
    }

    private static Path base() {
        return FMLPaths.GAMEDIR.get().resolve(MOD_FOLDER).resolve(CAMKEY_FOLDER);
    }
}
