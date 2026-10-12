package com.camkey;

public final class CamKeyState {
    private static final CamKeyState INSTANCE = new CamKeyState();

    private static final float DEFAULT_CAMERA_ROLL = 0.0F;

    private final CamKeyConfig config = CamKeyConfig.load();

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
}
