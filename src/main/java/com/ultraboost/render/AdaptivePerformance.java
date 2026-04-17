package com.ultraboost.render;

public class AdaptivePerformance {

    public static int getEntityLimit(int fps) {
        if (fps < 40) return 30;
        if (fps < 60) return 60;
        return 100;
    }
}
