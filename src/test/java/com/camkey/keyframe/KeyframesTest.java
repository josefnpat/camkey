package com.camkey.keyframe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyframesTest {
    @Test
    void addAppendsInOrder() {
        Keyframes keyframes = new Keyframes();
        keyframes.add(keyframe("a"));
        keyframes.add(keyframe("b"));
        assertEquals(List.of("a", "b"), keyframes.names());
    }

    @Test
    void allReturnsKeyframesInOrder() {
        Keyframes keyframes = new Keyframes();
        keyframes.add(keyframe("a"));
        keyframes.add(keyframe("b"));
        assertEquals(List.of("a", "b"), keyframes.all().stream().map(Keyframe::name).toList());
    }

    @Test
    void findReturnsKeyframeOrNull() {
        Keyframes keyframes = new Keyframes();
        keyframes.add(keyframe("a"));
        assertEquals("a", keyframes.find("a").name());
        assertNull(keyframes.find("missing"));
        assertTrue(keyframes.contains("a"));
        assertFalse(keyframes.contains("missing"));
    }

    @Test
    void removeDeletesByName() {
        Keyframes keyframes = new Keyframes();
        keyframes.add(keyframe("a"));
        assertTrue(keyframes.remove("a"));
        assertFalse(keyframes.remove("a"));
        assertTrue(keyframes.names().isEmpty());
    }

    @Test
    void renameReplacesInPlace() {
        Keyframes keyframes = new Keyframes();
        keyframes.add(keyframe("a"));
        keyframes.add(keyframe("b"));
        keyframes.rename("a", "c");
        assertEquals(List.of("c", "b"), keyframes.names());
        assertEquals("c", keyframes.find("c").name());
    }

    @Test
    void moveAfterTargetName() {
        Keyframes keyframes = new Keyframes();
        keyframes.add(keyframe("a"));
        keyframes.add(keyframe("b"));
        keyframes.add(keyframe("c"));
        keyframes.moveAfter("a", "c");
        assertEquals(List.of("b", "c", "a"), keyframes.names());
    }

    @Test
    void moveToStartAndEnd() {
        Keyframes keyframes = new Keyframes();
        keyframes.add(keyframe("a"));
        keyframes.add(keyframe("b"));
        keyframes.add(keyframe("c"));
        keyframes.moveAfter("c", Keyframes.START);
        assertEquals(List.of("c", "a", "b"), keyframes.names());
        keyframes.moveAfter("c", Keyframes.END);
        assertEquals(List.of("a", "b", "c"), keyframes.names());
    }

    @Test
    void moveAfterImmediatePredecessorKeepsOrder() {
        Keyframes keyframes = new Keyframes();
        keyframes.add(keyframe("a"));
        keyframes.add(keyframe("b"));
        keyframes.add(keyframe("c"));
        keyframes.moveAfter("b", "a");
        assertEquals(List.of("a", "b", "c"), keyframes.names());
    }

    @Test
    void namesIsAnUnmodifiableSnapshot() {
        Keyframes keyframes = new Keyframes();
        keyframes.add(keyframe("a"));
        List<String> names = keyframes.names();
        keyframes.add(keyframe("b"));
        assertEquals(List.of("a"), names);
    }

    @Test
    void replaceAllSwapsContent() {
        Keyframes keyframes = new Keyframes();
        keyframes.add(keyframe("a"));
        keyframes.replaceAll(List.of(keyframe("x"), keyframe("y")));
        assertEquals(List.of("x", "y"), keyframes.names());
    }

    private static Keyframe keyframe(String name) {
        return new Keyframe(name, 1.0, 0.0, 64.0, 0.0, 0.0F, 0.0F, 0.0F, "linear");
    }
}
