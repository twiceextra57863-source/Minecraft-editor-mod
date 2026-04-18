package com.ultraboost.render;

public class FPSBooster {

    public static int getTargetFPS(boolean combat) {
        return combat ? 144 : 90;
    }
}
