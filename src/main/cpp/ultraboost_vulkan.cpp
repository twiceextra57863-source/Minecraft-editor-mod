#include <jni.h>
#include <android/log.h>

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "UltraBoost", __VA_ARGS__)

extern "C" {

JNIEXPORT void JNICALL
Java_com_ultraboost_vulkan_VulkanBridge_initVulkan(JNIEnv* env, jclass clazz) {
    LOGI("Vulkan Init Called");
}

JNIEXPORT void JNICALL
Java_com_ultraboost_vulkan_VulkanBridge_renderFrame(JNIEnv* env, jclass clazz) {
    LOGI("Rendering Frame via Native Layer");
}

}
