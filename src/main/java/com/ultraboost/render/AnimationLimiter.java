package com.ultraboost.render;

public class AnimationLimiter {

    public static boolean shouldAnimate(double distance) {
        return distance < 40;
    }
}
