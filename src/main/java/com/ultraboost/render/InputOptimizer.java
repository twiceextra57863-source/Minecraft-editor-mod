package com.ultraboost.render;

public class InputOptimizer {

    public static boolean allowFastInput(long lastTime) {
        return System.nanoTime() - lastTime > 5_000_000; // faster input
    }
}
