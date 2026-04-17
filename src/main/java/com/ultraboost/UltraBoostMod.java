package com.ultraboost;

import net.fabricmc.api.ModInitializer;
import com.ultraboost.vulkan.VulkanBridge;

public class UltraBoostMod implements ModInitializer {

    @Override
    public void onInitialize() {
        System.out.println("[UltraBoost] Mod Loaded!");

        try {
            VulkanBridge.initVulkan();
        } catch (Throwable t) {
            System.out.println("[UltraBoost] Vulkan init failed: " + t.getMessage());
        }
    }
}
