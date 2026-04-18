package com.ultraboost;

import net.fabricmc.api.ModInitializer;

import com.ultraboost.vulkan.VulkanBridge;
import com.ultraboost.render.ChunkCleaner;
import com.ultraboost.system.CrashHandler;
import com.ultraboost.system.SafeExecutor;

public class UltraBoostMod implements ModInitializer {

    private static final String MOD_NAME = "UltraBoost";

    @Override
    public void onInitialize() {

        System.out.println("[" + MOD_NAME + "] Initializing...");

        // 🛡️ Global Crash Protection
        try {
            CrashHandler.init();
        } catch (Throwable t) {
            System.out.println("[" + MOD_NAME + "] CrashHandler failed");
        }

        // 🔥 Vulkan Init (Safe)
        SafeExecutor.run("VulkanInit", VulkanBridge::initSafe);

        // ⚡ Start Background Systems
        startCleanerThread();

        System.out.println("[" + MOD_NAME + "] Loaded Successfully!");
    }

    /**
     * 🔄 Background Optimization Thread
     */
    private void startCleanerThread() {

        Thread cleanerThread = new Thread(() -> {

            while (true) {
                try {
                    // 🧠 Chunk cleanup (memory + performance)
                    ChunkCleaner.tick();

                    // reduce CPU usage
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    System.out.println("[" + MOD_NAME + "] Cleaner stopped");
                    break;

                } catch (Throwable t) {
                    System.out.println("[" + MOD_NAME + "] Cleaner error: " + t.getMessage());
                }
            }

        });

        cleanerThread.setName("UltraBoost-Cleaner");
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }
}
