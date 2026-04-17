package com.ultraboost.vulkan;

public class VulkanBridge {

    static {
        try {
            System.loadLibrary("ultraboost_vulkan");
            System.out.println("[UltraBoost] Native library loaded");
        } catch (UnsatisfiedLinkError e) {
            System.out.println("[UltraBoost] Failed to load native library");
        }
    }

    public static native void initVulkan();
    public static native void renderFrame();
}
