package com.camkey.tween;

public final class LinearTween implements Tween {
    public static final String NAME = "linear";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public float apply(float progress) {
        return progress;
    }
}
