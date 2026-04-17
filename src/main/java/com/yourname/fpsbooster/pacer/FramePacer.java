package com.yourname.fpsbooster.pacer;

import net.minecraft.client.MinecraftClient;
import java.util.concurrent.atomic.AtomicLong;

public class FramePacer {
    private static final long TARGET_FRAME_TIME = 2_500_000L; // 400 FPS in nanoseconds
    private static final AtomicLong lastFrameTime = new AtomicLong();
    
    // Vulkan-style frame pacing for buttery smoothness
    public static void paceFrame() {
        long now = System.nanoTime();
        long elapsed = now - lastFrameTime.get();
        
        if (elapsed < TARGET_FRAME_TIME) {
            long sleepTime = (TARGET_FRAME_TIME - elapsed) / 1_000_000;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        lastFrameTime.set(System.nanoTime());
    }
}
