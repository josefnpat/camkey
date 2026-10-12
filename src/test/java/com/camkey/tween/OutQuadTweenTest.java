package com.camkey.tween;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OutQuadTweenTest {
    private static final Tween TWEEN = new OutQuadTween();

    @Test
    void reportsName() {
        assertEquals(OutQuadTween.NAME, TWEEN.name());
    }

    @Test
    void easesOut() {
        assertEquals(0.0F, TWEEN.apply(0.0F));
        assertEquals(0.75F, TWEEN.apply(0.5F));
        assertEquals(1.0F, TWEEN.apply(1.0F));
    }
}
