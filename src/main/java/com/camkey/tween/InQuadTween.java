package com.camkey.tween;

public final class InQuadTween implements Tween {
    public static final String NAME = "in-quad";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public float apply(float progress) {
        return progress * progress;
    }
}
