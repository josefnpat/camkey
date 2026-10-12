package com.camkey.save;

import com.camkey.keyframe.Keyframe;
import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyframeFileTest {
    @Test
    void writesExpectedJsonShape(@TempDir Path directory) throws IOException {
        Path file = directory.resolve("save.json");
        KeyframeFile.of(new int[]{1, 0, 0}, List.of(keyframe("a"))).save(file);
        String json = Files.readString(file);
        assertTrue(json.contains("\"version\""));
        assertTrue(json.contains("\"keyframes\""));
        assertTrue(json.contains("\"name\""));
        assertTrue(json.contains("\"time\""));
        assertTrue(json.contains("\"position\""));
        assertTrue(json.contains("\"rotation\""));
        assertTrue(json.contains("\"tween\""));
    }

    @Test
    void roundTripsKeyframesThroughJson(@TempDir Path directory) {
        Path file = directory.resolve("save.json");
        List<Keyframe> keyframes = List.of(
            new Keyframe("100_tree", 1.5, 0.5, 64.0, -15.2, 15.0F, 90.0F, 30.0F, "linear"),
            new Keyframe("200_house", 3.5, 10.5, 70.0, -1.0, 0.0F, 180.0F, 0.0F, "none")
        );
        KeyframeFile.of(new int[]{1, 0, 0}, keyframes).save(file);

        KeyframeFile loaded = KeyframeFile.read(file);
        assertArrayEquals(new int[]{1, 0, 0}, loaded.version());

        List<Keyframe> roundTripped = loaded.toKeyframes();
        assertEquals(keyframes.size(), roundTripped.size());
        for (int index = 0; index < keyframes.size(); index++) {
            assertKeyframe(keyframes.get(index), roundTripped.get(index));
        }
    }

    @Test
    void rejectsEmptyFile(@TempDir Path directory) throws IOException {
        Path file = directory.resolve("save.json");
        Files.writeString(file, "");
        assertThrows(IllegalStateException.class, () -> KeyframeFile.read(file));
    }

    @Test
    void rejectsMalformedJson(@TempDir Path directory) throws IOException {
        Path file = directory.resolve("save.json");
        Files.writeString(file, "{ not json");
        assertThrows(JsonSyntaxException.class, () -> KeyframeFile.read(file));
    }

    @Test
    void missingKeyframesFieldYieldsNoKeyframes(@TempDir Path directory) throws IOException {
        Path file = directory.resolve("save.json");
        Files.writeString(file, "{\"version\": [1, 0, 0]}");
        KeyframeFile loaded = KeyframeFile.read(file);
        assertArrayEquals(new int[]{1, 0, 0}, loaded.version());
        assertTrue(loaded.toKeyframes().isEmpty());
    }

    @Test
    void createsMissingParentDirectories(@TempDir Path directory) {
        Path file = directory.resolve("nested").resolve("save").resolve("default.json");
        KeyframeFile.of(new int[]{1, 0, 0}, List.of()).save(file);
        assertTrue(Files.exists(file));
    }

    private static void assertKeyframe(Keyframe expected, Keyframe actual) {
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.time(), actual.time());
        assertEquals(expected.x(), actual.x());
        assertEquals(expected.y(), actual.y());
        assertEquals(expected.z(), actual.z());
        assertEquals(expected.pitch(), actual.pitch());
        assertEquals(expected.yaw(), actual.yaw());
        assertEquals(expected.roll(), actual.roll());
        assertEquals(expected.tween(), actual.tween());
    }

    private static Keyframe keyframe(String name) {
        return new Keyframe(name, 1.0, 0.0, 64.0, 0.0, 0.0F, 0.0F, 0.0F, "linear");
    }
}
