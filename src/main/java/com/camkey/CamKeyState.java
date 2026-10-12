package com.camkey;

import com.camkey.keyframe.Keyframes;
import com.camkey.playback.Playback;

public final class CamKeyState {
    private static final CamKeyState INSTANCE = new CamKeyState();

    private static final float DEFAULT_CAMERA_ROLL = 0.0F;

    private final CamKeyConfig config = CamKeyConfig.load();

    private final Keyframes keyframes = new Keyframes();

    private final Playback playback = new Playback();

    private float cameraRoll = DEFAULT_CAMERA_ROLL;

    private CamKeyState() {
    }

    public static CamKeyState get() {
        return INSTANCE;
    }

    public String defaultTween() {
        return config.tweenDefault();
    }

    public void setDefaultTween(String tween) {
        config.setTweenDefault(tween);
        config.save();
    }

    public float cameraRoll() {
        return cameraRoll;
    }

    public void setCameraRoll(float cameraRoll) {
        this.cameraRoll = cameraRoll;
    }

    public Keyframes keyframes() {
        return keyframes;
    }

    public Playback playback() {
        return playback;
    }
}
