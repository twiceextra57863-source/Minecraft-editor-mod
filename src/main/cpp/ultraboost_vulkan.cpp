#include <jni.h>
#include <android/log.h>
#include <chrono>

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "UltraBoost", __VA_ARGS__)

using namespace std::chrono;

static long lastFrameTime = 0;

extern "C" {

/**
 * 🔥 Vulkan init (currently stub, future upgrade possible)
 */
JNIEXPORT void JNICALL
Java_com_ultraboost_vulkan_VulkanBridge_initVulkan(JNIEnv*, jclass) {
    LOGI("UltraBoost Vulkan Init (stub)");
}

/**
 * ⚡ Native render timing (FPS stabilizer)
 */
JNIEXPORT void JNICALL
Java_com_ultraboost_vulkan_VulkanBridge_renderFrame(JNIEnv*, jclass) {

    long now = duration_cast<nanoseconds>(
        high_resolution_clock::now().time_since_epoch()
    ).count();

    // ~120 FPS cap for smoothness
    if (now - lastFrameTime < 8'000'000) {
        return;
    }

    lastFrameTime = now;

    LOGI("UltraBoost Native Frame Tick");
}

/**
 * 🧠 High precision timer (used in PvP)
 */
JNIEXPORT jlong JNICALL
Java_com_ultraboost_vulkan_VulkanBridge_getHighPrecisionTime(JNIEnv*, jclass) {

    return duration_cast<nanoseconds>(
        high_resolution_clock::now().time_since_epoch()
    ).count();
}

}
