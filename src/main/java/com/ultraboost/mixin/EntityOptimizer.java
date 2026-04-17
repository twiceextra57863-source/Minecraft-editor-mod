package com.ultraboost.render;

public class EntityOptimizer {

    public static boolean shouldRender(double distance) {
        return distance < 100; // tune value (60–120 best)
    }
}
