package com.camkey.tween;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinearTweenTest {
    private static final Tween TWEEN = new LinearTween();

    @Test
    void reportsName() {
        assertEquals(LinearTween.NAME, TWEEN.name());
    }

    @Test
    void returnsProgressUnchanged() {
        assertEquals(0.0F, TWEEN.apply(0.0F));
        assertEquals(0.25F, TWEEN.apply(0.25F));
        assertEquals(0.5F, TWEEN.apply(0.5F));
        assertEquals(1.0F, TWEEN.apply(1.0F));
    }
}
