package com.ultraboost.render;

import net.minecraft.entity.player.PlayerEntity;

public class PlayerOptimizer {

    public static boolean shouldRender(PlayerEntity player, double distance) {

        // nearest players always render
        if (distance < 30) return true;

        // medium distance players (50% chance)
        if (distance < 80) {
            return Math.random() > 0.5;
        }

        // far players skip
        return false;
    }
}
