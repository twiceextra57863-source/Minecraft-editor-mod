package com.ultraboost.vulkan;

public class VulkanBridge {

    private static boolean loaded = false;

    static {
        try {
            System.loadLibrary("ultraboost_vulkan");
            loaded = true;
            System.out.println("[UltraBoost] Native library loaded");
        } catch (UnsatisfiedLinkError e) {
            loaded = false;
            System.out.println("[UltraBoost] Native library NOT loaded");
        }
    }

    public static void initSafe() {
        if (!loaded) return;

        try {
            initVulkan();
        } catch (Throwable t) {
            System.out.println("[UltraBoost] Vulkan init failed");
        }
    }

    public static void renderSafe() {
        if (!loaded) return;

        try {
            renderFrame();
        } catch (Throwable ignored) {}
    }

    public static long getTime() {
        if (!loaded) return System.nanoTime();

        try {
            return getHighPrecisionTime();
        } catch (Throwable t) {
            return System.nanoTime();
        }
    }

    // 🔻 Native methods
    public static native void initVulkan();
    public static native void renderFrame();
    public static native long getHighPrecisionTime();
}
