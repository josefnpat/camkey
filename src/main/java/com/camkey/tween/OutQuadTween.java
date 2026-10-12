package com.camkey.tween;

public final class OutQuadTween implements Tween {
    public static final String NAME = "out-quad";

    private static final float FULL_PROGRESS = 1.0F;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public float apply(float progress) {
        float inverted = FULL_PROGRESS - progress;
        return FULL_PROGRESS - inverted * inverted;
    }
}
