package com.camkey.keyframe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyframeTest {
    private static final Keyframe KEYFRAME = new Keyframe(
        "a", 2.5, 1.0, 64.0, -3.0, 10.0F, 20.0F, 30.0F, "none"
    );

    @Test
    void exposesAllFields() {
        assertEquals("a", KEYFRAME.name());
        assertEquals(2.5, KEYFRAME.time());
        assertEquals(1.0, KEYFRAME.x());
        assertEquals(64.0, KEYFRAME.y());
        assertEquals(-3.0, KEYFRAME.z());
        assertEquals(10.0F, KEYFRAME.pitch());
        assertEquals(20.0F, KEYFRAME.yaw());
        assertEquals(30.0F, KEYFRAME.roll());
        assertEquals("none", KEYFRAME.tween());
    }

    @Test
    void withNamePreservesOtherFields() {
        Keyframe renamed = KEYFRAME.withName("b");
        assertEquals("b", renamed.name());
        assertEquals(KEYFRAME.time(), renamed.time());
        assertEquals(KEYFRAME.x(), renamed.x());
        assertEquals(KEYFRAME.y(), renamed.y());
        assertEquals(KEYFRAME.z(), renamed.z());
        assertEquals(KEYFRAME.pitch(), renamed.pitch());
        assertEquals(KEYFRAME.yaw(), renamed.yaw());
        assertEquals(KEYFRAME.roll(), renamed.roll());
        assertEquals(KEYFRAME.tween(), renamed.tween());
    }
}
