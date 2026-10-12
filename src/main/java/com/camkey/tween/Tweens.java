package com.camkey.tween;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Tweens {
    public static final String DEFAULT = LinearTween.NAME;

    private static final List<Tween> ALL = List.of(
        new NoneTween(),
        new LinearTween()
    );

    private static final Map<String, Tween> BY_NAME = ALL.stream()
        .collect(Collectors.toUnmodifiableMap(Tween::name, Function.identity()));

    private Tweens() {
    }

    public static List<Tween> all() {
        return ALL;
    }

    public static List<String> names() {
        return ALL.stream().map(Tween::name).toList();
    }

    public static Tween find(String name) {
        return BY_NAME.get(name);
    }
}
