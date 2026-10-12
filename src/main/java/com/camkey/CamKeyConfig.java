package com.camkey;

import com.camkey.tween.Tweens;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public final class CamKeyConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final String MOD_FOLDER = "mods";
    private static final String CAMKEY_FOLDER = "camkey";
    private static final String CONFIG_FILE = "config.json";

    private int[] version = new int[0];
    @SerializedName("tween-default")
    private String tweenDefault = Tweens.DEFAULT;

    public CamKeyConfig() {
    }

    public CamKeyConfig(int[] version, String tweenDefault) {
        this.version = version;
        this.tweenDefault = tweenDefault;
    }

    public static CamKeyConfig load() {
        Path path = path();
        CamKeyConfig config;
        if (Files.exists(path)) {
            config = read(path);
        } else {
            config = new CamKeyConfig(CamKey.getVersionParts(), Tweens.DEFAULT);
            config.save(path);
        }
        config.warnIfVersionMismatch();
        return config;
    }

    public static CamKeyConfig read(Path path) {
        try (Reader reader = Files.newBufferedReader(path)) {
            CamKeyConfig config = GSON.fromJson(reader, CamKeyConfig.class);
            if (config == null) {
                throw new IllegalStateException("Empty camkey config at " + path);
            }
            return config;
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static Path path() {
        return FMLPaths.GAMEDIR.get()
            .resolve(MOD_FOLDER)
            .resolve(CAMKEY_FOLDER)
            .resolve(CONFIG_FILE);
    }

    public int[] version() {
        return version;
    }

    public String tweenDefault() {
        if (tweenDefault == null) {
            tweenDefault = Tweens.DEFAULT;
        }
        return tweenDefault;
    }

    public void setTweenDefault(String tweenDefault) {
        this.tweenDefault = tweenDefault;
    }

    public void save() {
        save(path());
    }

    public void save(Path path) {
        try {
            Files.createDirectories(path.getParent());
            try (Writer writer = Files.newBufferedWriter(path)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private void warnIfVersionMismatch() {
        if (!Arrays.equals(version, CamKey.getVersionParts())) {
            LOGGER.warn(
                "camkey config version {} does not match mod version {}",
                Arrays.toString(version),
                Arrays.toString(CamKey.getVersionParts())
            );
        }
    }
}
