package com.camkey.save;

import com.camkey.keyframe.Keyframe;

final class KeyframeEntry {
    private String name;
    private double time;
    private double[] position;
    private float[] rotation;
    private String tween;

    static KeyframeEntry of(Keyframe keyframe) {
        KeyframeEntry entry = new KeyframeEntry();
        entry.name = keyframe.name();
        entry.time = keyframe.time();
        entry.position = new double[]{keyframe.x(), keyframe.y(), keyframe.z()};
        entry.rotation = new float[]{keyframe.pitch(), keyframe.yaw(), keyframe.roll()};
        entry.tween = keyframe.tween();
        return entry;
    }

    Keyframe toKeyframe() {
        return new Keyframe(
            name,
            time,
            position[0],
            position[1],
            position[2],
            rotation[0],
            rotation[1],
            rotation[2],
            tween
        );
    }
}
