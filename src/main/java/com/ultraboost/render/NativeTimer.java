package com.ultraboost.render;

import com.ultraboost.vulkan.VulkanBridge;

public class NativeTimer {

    public static long now() {
        try {
            return VulkanBridge.getHighPrecisionTime();
        } catch (Throwable t) {
            return System.nanoTime();
        }
    }
}
