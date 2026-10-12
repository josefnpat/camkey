package com.camkey.playback;

import com.camkey.keyframe.Keyframe;
import com.camkey.tween.Tweens;

import java.util.List;

public final class Playback {
    public static final double NO_LIMIT = -1.0;
    public static final double TICK_SECONDS = 1.0 / 20.0;

    private List<Keyframe> keyframes = List.of();
    private int startIndex;
    private double duration;
    private double limit = NO_LIMIT;
    private double elapsed;
    private boolean playing;

    public boolean playing() {
        return playing;
    }

    public void start(List<Keyframe> keyframes, int startIndex, double limit) {
        this.keyframes = List.copyOf(keyframes);
        this.startIndex = startIndex;
        this.duration = durationFrom(keyframes, startIndex);
        this.limit = limit;
        this.elapsed = 0.0;
        this.playing = duration > 0.0;
    }

    public void stop() {
        playing = false;
    }

    public void tick(double seconds) {
        if (playing) {
            elapsed += seconds;
            if (expired()) {
                playing = false;
            }
        }
    }

    public Transform transform() {
        return transform(0.0);
    }

    public Transform transform(double extraSeconds) {
        double time = elapsed + extraSeconds;
        int last = keyframes.size() - 1;
        int index = startIndex;
        double remaining = time;
        while (index < last && remaining >= keyframes.get(index + 1).time()) {
            remaining -= keyframes.get(index + 1).time();
            index++;
        }
        Transform result;
        if (index >= last) {
            result = Transform.of(keyframes.get(last));
        } else {
            Keyframe target = keyframes.get(index + 1);
            float progress = (float) (remaining / target.time());
            float eased = Tweens.find(target.tween()).apply(progress);
            result = Interpolation.interpolate(keyframes.get(index), target, eased);
        }
        return result;
    }

    private boolean expired() {
        boolean pastLimit = limit != NO_LIMIT && elapsed >= limit;
        return elapsed >= duration || pastLimit;
    }

    private static double durationFrom(List<Keyframe> keyframes, int startIndex) {
        double total = 0.0;
        for (int index = startIndex + 1; index < keyframes.size(); index++) {
            total += keyframes.get(index).time();
        }
        return total;
    }
}
