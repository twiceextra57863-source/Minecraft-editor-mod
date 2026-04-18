package com.ultraboost.render;

public class PvPCamera {

    public static float smooth(float current, float last) {
        return last + (current - last) * 0.7f;
    }
}
