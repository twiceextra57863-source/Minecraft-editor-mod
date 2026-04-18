package com.ultraboost.render;

public class CombatOptimizer {

    private static long lastCombat = 0;

    public static void triggerCombat() {
        lastCombat = System.currentTimeMillis();
    }

    public static boolean isInCombat() {
        return System.currentTimeMillis() - lastCombat < 3000;
    }
}
