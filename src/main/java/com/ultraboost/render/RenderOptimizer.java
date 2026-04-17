package com.ultraboost.render;

public class RenderOptimizer {

    private static long lastFrameTime = 0;

    public static boolean shouldSkipFrame() {
        long now = System.nanoTime();
        long targetTime = 1_000_000_000 / 120; // 120 FPS cap

        if (now - lastFrameTime < targetTime) {
            return true;
        }

        lastFrameTime = now;
        return false;
    }

    public static boolean shouldRenderEntity(double distance) {
        return distance < 64; // far entities skip
    }
}
