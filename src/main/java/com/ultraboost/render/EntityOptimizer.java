package com.ultraboost.render;

public class EntityOptimizer {

    public static boolean shouldRender(double distance) {
        return distance < 80; // skip far entities
    }
}
