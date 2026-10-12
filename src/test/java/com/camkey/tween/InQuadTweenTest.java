package com.camkey.tween;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InQuadTweenTest {
    private static final Tween TWEEN = new InQuadTween();

    @Test
    void reportsName() {
        assertEquals(InQuadTween.NAME, TWEEN.name());
    }

    @Test
    void easesIn() {
        assertEquals(0.0F, TWEEN.apply(0.0F));
        assertEquals(0.25F, TWEEN.apply(0.5F));
        assertEquals(1.0F, TWEEN.apply(1.0F));
    }
}
