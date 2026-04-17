package com.ultraboost;

import net.fabricmc.api.ModInitializer;

import com.ultraboost.vulkan.VulkanBridge;
import com.ultraboost.render.ChunkCleaner;

public class UltraBoostMod implements ModInitializer {

    private static final String MOD_NAME = "UltraBoost";

    @Override
    public void onInitialize() {

        System.out.println("[" + MOD_NAME + "] Initializing...");

        // 🔥 Vulkan Init (Safe)
        try {
            VulkanBridge.initVulkan();
            System.out.println("[" + MOD_NAME + "] Vulkan initialized");
        } catch (Throwable t) {
            System.out.println("[" + MOD_NAME + "] Vulkan init failed: " + t.getMessage());
        }

        // ⚡ Start Background Optimization Thread
        startCleanerThread();

        System.out.println("[" + MOD_NAME + "] Loaded Successfully!");
    }

    /**
     * 🔄 Chunk Cache Cleaner Thread
     */
    private void startCleanerThread() {

        Thread cleanerThread = new Thread(() -> {

            while (true) {
                try {
                    // Run cleaner logic
                    ChunkCleaner.tick();

                    // Sleep to reduce CPU usage
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    System.out.println("[" + MOD_NAME + "] Cleaner thread interrupted");
                    break;
                } catch (Throwable t) {
                    System.out.println("[" + MOD_NAME + "] Cleaner error: " + t.getMessage());
                }
            }

        });

        cleanerThread.setName("UltraBoost-Cleaner");
        cleanerThread.setDaemon(true); // stops with game
        cleanerThread.start();
    }
}
