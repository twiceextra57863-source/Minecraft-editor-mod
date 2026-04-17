package com.ultraboost.render;

public class ChunkCleaner {

    private static long lastClear = 0;

    public static void tick() {
        long now = System.currentTimeMillis();

        if (now - lastClear > 5000) {
            ChunkCache.clear(); // reset cache every 5 sec
            lastClear = now;
        }
    }
}
