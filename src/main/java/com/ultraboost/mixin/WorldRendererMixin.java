package com.ultraboost.mixin;

import com.ultraboost.render.RenderOptimizer;
import com.ultraboost.render.BatchManager;
import com.ultraboost.vulkan.VulkanBridge;

import net.minecraft.client.render.WorldRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    /**
     * Before render starts
     */
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRenderStart(CallbackInfo ci) {

        // 🔥 Smart Frame Control (skip extra frames)
        try {
            if (!RenderOptimizer.shouldRender()) {
                ci.cancel(); // skip this frame
                return;
            }
        } catch (Throwable t) {
            // safe fail
        }

        // ⚡ Vulkan Native Call (safe)
        try {
            VulkanBridge.renderFrame();
        } catch (Throwable ignored) {
            // agar native fail ho toh game crash nahi karega
        }
    }

    /**
     * After render ends
     */
    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderEnd(CallbackInfo ci) {

        // 🚀 Execute batched rendering tasks
        try {
            BatchManager.flush();
        } catch (Throwable ignored) {
        }
    }
}
