package com.camkey.keyframe;

public final class Keyframe {
    private final String name;
    private final double time;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;
    private final float roll;
    private final String tween;

    public Keyframe(
        String name,
        double time,
        double x,
        double y,
        double z,
        float pitch,
        float yaw,
        float roll,
        String tween
    ) {
        this.name = name;
        this.time = time;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.tween = tween;
    }

    public String name() {
        return name;
    }

    public double time() {
        return time;
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

    public String tween() {
        return tween;
    }

    public Keyframe withName(String newName) {
        return new Keyframe(newName, time, x, y, z, pitch, yaw, roll, tween);
    }
}
