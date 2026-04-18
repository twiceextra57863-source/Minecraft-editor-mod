package com.ultraboost.render;

public class CameraOptimizer {

    private static float lastYaw = 0;
    private static float lastPitch = 0;

    public static float smooth(float current, float last) {
        return last + (current - last) * 0.5f; // smooth factor
    }

    public static float smoothYaw(float yaw) {
        lastYaw = smooth(yaw, lastYaw);
        return lastYaw;
    }

    public static float smoothPitch(float pitch) {
        lastPitch = smooth(pitch, lastPitch);
        return lastPitch;
    }
}
