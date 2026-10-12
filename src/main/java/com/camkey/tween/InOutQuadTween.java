package com.camkey.tween;

public final class InOutQuadTween implements Tween {
    public static final String NAME = "in-out-quad";

    private static final float MIDPOINT = 0.5F;
    private static final float FULL_PROGRESS = 1.0F;
    private static final float QUADRATIC_FACTOR = 2.0F;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public float apply(float progress) {
        float eased;
        if (progress < MIDPOINT) {
            eased = QUADRATIC_FACTOR * progress * progress;
        } else {
            float inverted = QUADRATIC_FACTOR - QUADRATIC_FACTOR * progress;
            eased = FULL_PROGRESS - inverted * inverted / QUADRATIC_FACTOR;
        }
        return eased;
    }
}
