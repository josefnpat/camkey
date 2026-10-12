package com.camkey.tween;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InOutQuadTweenTest {
    private static final Tween TWEEN = new InOutQuadTween();

    @Test
    void reportsName() {
        assertEquals(InOutQuadTween.NAME, TWEEN.name());
    }

    @Test
    void easesInThenOut() {
        assertEquals(0.0F, TWEEN.apply(0.0F));
        assertEquals(0.125F, TWEEN.apply(0.25F));
        assertEquals(0.5F, TWEEN.apply(0.5F));
        assertEquals(0.875F, TWEEN.apply(0.75F));
        assertEquals(1.0F, TWEEN.apply(1.0F));
    }

    @Test
    void mirrorsAroundMidpoint() {
        assertEquals(TWEEN.apply(0.25F), 1.0F - TWEEN.apply(0.75F));
    }
}
