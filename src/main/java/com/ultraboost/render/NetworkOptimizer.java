package com.ultraboost.render;

public class NetworkOptimizer {

    private static long lastPacket = 0;

    public static boolean allowPacket() {
        long now = System.currentTimeMillis();

        if (now - lastPacket < 50) {
            return false;
        }

        lastPacket = now;
        return true;
    }
}
