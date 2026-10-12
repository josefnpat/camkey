package com.camkey.playback;

import com.camkey.keyframe.Keyframe;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class Interpolation {
    private static final float DEGREES_TO_RADIANS = (float) (Math.PI / 180.0);
    private static final float RADIANS_TO_DEGREES = (float) (180.0 / Math.PI);
    private static final float HALF_TURN = (float) Math.PI;

    private Interpolation() {
    }

    public static Transform interpolate(Keyframe from, Keyframe to, float progress) {
        Quaternionf rotation = toQuaternion(from).slerp(toQuaternion(to), progress);
        Vector3f euler = rotation.getEulerAnglesYXZ(new Vector3f());
        return new Transform(
            lerp(from.x(), to.x(), progress),
            lerp(from.y(), to.y(), progress),
            lerp(from.z(), to.z(), progress),
            -euler.x * RADIANS_TO_DEGREES,
            (HALF_TURN - euler.y) * RADIANS_TO_DEGREES,
            -euler.z * RADIANS_TO_DEGREES
        );
    }

    private static double lerp(double from, double to, float progress) {
        return from + (to - from) * progress;
    }

    private static Quaternionf toQuaternion(Keyframe keyframe) {
        return new Quaternionf().rotationYXZ(
            HALF_TURN - keyframe.yaw() * DEGREES_TO_RADIANS,
            -keyframe.pitch() * DEGREES_TO_RADIANS,
            -keyframe.roll() * DEGREES_TO_RADIANS
        );
    }
}
