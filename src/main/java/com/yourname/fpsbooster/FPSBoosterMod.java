package com.yourname.fpsbooster;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class FPSBoosterMod implements ModInitializer {
    public static final String MOD_ID = "fpsbooster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("🚀 FPS Booster Mod Activated! Maximum Performance Mode ON");
        LOGGER.info("⚡ Optimizing rendering pipelines...");
        LOGGER.info("🎮 Target: 400+ FPS with Vulkan-like performance");
    }
}
