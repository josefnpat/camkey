package com.camkey;

public final class CamKeyState {
    private static final CamKeyState INSTANCE = new CamKeyState();

    private final CamKeyConfig config = CamKeyConfig.load();

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
}
