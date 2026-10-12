package com.camkey.tween;

public final class NoneTween implements Tween {
    public static final String NAME = "none";

    private static final float NO_PROGRESS = 0.0F;
    private static final float FULL_PROGRESS = 1.0F;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public float apply(float progress) {
        return progress >= FULL_PROGRESS ? FULL_PROGRESS : NO_PROGRESS;
    }
}
