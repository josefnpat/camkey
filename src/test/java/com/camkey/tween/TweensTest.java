package com.camkey.tween;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TweensTest {
    @Test
    void listsTweenNamesInRegistrationOrder() {
        assertEquals(List.of(NoneTween.NAME, LinearTween.NAME), Tweens.names());
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
    void returnsNullForUnknownTween() {
        assertNull(Tweens.find("does-not-exist"));
    }

    @Test
    void defaultIsRegisteredTween() {
        assertNotNull(Tweens.find(Tweens.DEFAULT));
        assertEquals(Tweens.DEFAULT, Tweens.find(Tweens.DEFAULT).name());
    }
}
