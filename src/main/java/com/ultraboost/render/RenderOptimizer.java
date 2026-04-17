package com.ultraboost.render;

public class RenderOptimizer {

    private static long lastFrame = 0;
    private static final long TARGET = 1_000_000_000 / 120; // 120 FPS stable

    public static boolean shouldRender() {
        long now = System.nanoTime();

        if (now - lastFrame < TARGET) {
            return false;
        }

        lastFrame = now;
        return true;
    }
}
