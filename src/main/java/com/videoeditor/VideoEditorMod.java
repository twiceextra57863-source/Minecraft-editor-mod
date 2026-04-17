package com.videoeditor;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoEditorMod implements ClientModInitializer {
    public static final String MOD_ID = "minecraft-video-editor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Video Editor Smoothness Mod Loaded!");
        // Yahan hum internal engine tweaks ko initialize karte hain
    }
}
