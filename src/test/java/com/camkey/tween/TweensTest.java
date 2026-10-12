package com.camkey.tween;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TweensTest {
    private static final int SAMPLES = 100;
    @Test
    void listsTweenNamesInRegistrationOrder() {
        assertEquals(
            List.of(
                NoneTween.NAME,
                LinearTween.NAME,
                InQuadTween.NAME,
                OutQuadTween.NAME,
                InOutQuadTween.NAME
            ),
            Tweens.names()
        );
    }

    @Test
    void allMatchesNames() {
        List<String> fromAll = Tweens.all().stream().map(Tween::name).toList();
        assertEquals(Tweens.names(), fromAll);
    }

    @Test
    void findsRegisteredTweenByName() {
        assertEquals(NoneTween.NAME, Tweens.find(NoneTween.NAME).name());
        assertEquals(LinearTween.NAME, Tweens.find(LinearTween.NAME).name());
    }

    @Test
    void findsEveryRegisteredTweenByName() {
        for (Tween tween : Tweens.all()) {
            assertEquals(tween.name(), Tweens.find(tween.name()).name());
        }
    }

    @Test
    void everyTweenIsMonotonicAndWithinUnitRange() {
        for (Tween tween : Tweens.all()) {
            assertEquals(0.0F, tween.apply(0.0F), tween.name());
            assertEquals(1.0F, tween.apply(1.0F), tween.name());
            float previous = tween.apply(0.0F);
            for (int step = 1; step <= SAMPLES; step++) {
                float eased = tween.apply((float) step / SAMPLES);
                assertTrue(eased >= previous, tween.name() + " decreased at step " + step);
                assertTrue(eased >= 0.0F, tween.name() + " below 0 at step " + step);
                assertTrue(eased <= 1.0F, tween.name() + " above 1 at step " + step);
                previous = eased;
            }
        }
    }

    @Test
    void returnsNullForUnknownTween() {
        assertNull(Tweens.find("does-not-exist"));
    }

    @Test
    void defaultIsRegisteredTween() {
        assertNotNull(Tweens.find(Tweens.DEFAULT));
        assertEquals(Tweens.DEFAULT, Tweens.find(Tweens.DEFAULT).name());
    }
}
