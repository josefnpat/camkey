package com.camkey.tween;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoneTweenTest {
    private static final Tween TWEEN = new NoneTween();

    @Test
    void reportsName() {
        assertEquals(NoneTween.NAME, TWEEN.name());
    }

    @Test
    void holdsAtStartBeforeSegmentEnd() {
        assertEquals(0.0F, TWEEN.apply(0.0F));
        assertEquals(0.0F, TWEEN.apply(0.5F));
        assertEquals(0.0F, TWEEN.apply(0.999F));
    }

    @Test
    void jumpsToEndAtSegmentEnd() {
        assertEquals(1.0F, TWEEN.apply(1.0F));
        assertEquals(1.0F, TWEEN.apply(2.0F));
    }
}
