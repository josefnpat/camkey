package com.camkey;

import com.camkey.tween.Tweens;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CamKeyConfigTest {
    private static final int[] VERSION = {1, 0, 0};

    @Test
    void writesConfigWithExpectedKeys(@TempDir Path directory) throws IOException {
        Path file = directory.resolve("config.json");
        CamKeyConfig config = new CamKeyConfig(VERSION, "linear");
        config.save(file);
        String json = Files.readString(file);
        assertTrue(json.contains("\"version\""));
        assertTrue(json.contains("\"tween-default\""));
        assertTrue(json.contains("\"linear\""));
    }

    @Test
    void roundTripsThroughJson(@TempDir Path directory) {
        Path file = directory.resolve("config.json");
        CamKeyConfig config = new CamKeyConfig(VERSION, "none");
        config.save(file);
        CamKeyConfig loaded = CamKeyConfig.read(file);
        assertArrayEquals(VERSION, loaded.version());
        assertEquals("none", loaded.tweenDefault());
    }

    @Test
    void createsMissingParentDirectories(@TempDir Path directory) {
        Path file = directory.resolve("nested").resolve("camkey").resolve("config.json");
        CamKeyConfig config = new CamKeyConfig(VERSION, "linear");
        config.save(file);
        assertTrue(Files.exists(file));
    }

    @Test
    void defaultsToRegisteredTweenWhenFieldMissing(@TempDir Path directory) throws IOException {
        Path file = directory.resolve("config.json");
        Files.writeString(file, "{\"version\": [1, 0, 0]}");
        CamKeyConfig loaded = CamKeyConfig.read(file);
        assertEquals(Tweens.DEFAULT, loaded.tweenDefault());
    }

    @Test
    void fallsBackToDefaultWhenTweenIsNull(@TempDir Path directory) throws IOException {
        Path file = directory.resolve("config.json");
        Files.writeString(file, "{\"version\": [1, 0, 0], \"tween-default\": null}");
        CamKeyConfig loaded = CamKeyConfig.read(file);
        assertEquals(Tweens.DEFAULT, loaded.tweenDefault());
    }

    @Test
    void rejectsEmptyConfig(@TempDir Path directory) throws IOException {
        Path file = directory.resolve("config.json");
        Files.writeString(file, "");
        assertThrows(IllegalStateException.class, () -> CamKeyConfig.read(file));
    }

    @Test
    void setTweenDefaultUpdatesValue() {
        CamKeyConfig config = new CamKeyConfig(VERSION, "linear");
        config.setTweenDefault("none");
        assertEquals("none", config.tweenDefault());
    }
}
