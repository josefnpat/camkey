package com.camkey.save;

import com.camkey.keyframe.Keyframe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class KeyframeFile {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private int[] version = new int[0];
    private List<KeyframeEntry> keyframes = new ArrayList<>();

    public static KeyframeFile of(int[] version, List<Keyframe> keyframes) {
        KeyframeFile file = new KeyframeFile();
        file.version = version;
        file.keyframes = keyframes.stream()
            .map(KeyframeEntry::of)
            .collect(Collectors.toCollection(ArrayList::new));
        return file;
    }

    public static KeyframeFile read(Path path) {
        try (Reader reader = Files.newBufferedReader(path)) {
            KeyframeFile file = GSON.fromJson(reader, KeyframeFile.class);
            if (file == null) {
                throw new IllegalStateException("Empty keyframe file at " + path);
            }
            return file;
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
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

    public int[] version() {
        return version;
    }

    public List<Keyframe> toKeyframes() {
        return keyframes.stream()
            .map(KeyframeEntry::toKeyframe)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
