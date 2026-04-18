package com.ultraboost.render;

public class InputLatencyReducer {

    private static long lastClick = 0;

    public static boolean allowClick() {
        long now = System.nanoTime();

        // allow faster clicks (~5ms gap)
        if (now - lastClick > 5_000_000) {
            lastClick = now;
            return true;
        }

        return false;
    }
}
