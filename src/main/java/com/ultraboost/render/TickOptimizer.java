package com.ultraboost.render;

public class TickOptimizer {

    private static int tickCounter = 0;

    public static boolean shouldSkip() {
        tickCounter++;
        return tickCounter % 2 == 0; // skip every 2nd tick
    }
}
