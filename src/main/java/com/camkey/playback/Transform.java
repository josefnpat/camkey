package com.camkey.playback;

import com.camkey.keyframe.Keyframe;

public final class Transform {
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;
    private final float roll;

    public Transform(double x, double y, double z, float pitch, float yaw, float roll) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public static Transform of(Keyframe keyframe) {
        return new Transform(
            keyframe.x(),
            keyframe.y(),
            keyframe.z(),
            keyframe.pitch(),
            keyframe.yaw(),
            keyframe.roll()
        );
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public float pitch() {
        return pitch;
    }

    public float yaw() {
        return yaw;
    }

    public float roll() {
        return roll;
    }
}
