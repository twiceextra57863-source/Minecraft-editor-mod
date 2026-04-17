package com.videoeditor;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoEditorMod implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("fps-boost");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Mobile Optimization Mod Initialized for 1.21.4!");
    }
}
