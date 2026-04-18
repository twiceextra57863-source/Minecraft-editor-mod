package com.ultraboost.system;

import java.util.HashSet;
import java.util.Set;

public class FeatureManager {

    private static final Set<String> disabled = new HashSet<>();

    public static boolean isEnabled(String feature) {
        return !disabled.contains(feature);
    }

    public static void disable(String feature) {
        disabled.add(feature);
        System.out.println("[UltraBoost] Disabled feature: " + feature);
    }
}
