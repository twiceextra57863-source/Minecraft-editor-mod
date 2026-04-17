package com.yourname.fpsbooster.mixins;

import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.lwjgl.glfw.GLFW;

@Mixin(Window.class)
public abstract class WindowMixin {
    
    @Shadow private boolean vsync;
    
    @Inject(method = "setVsync", at = @At("HEAD"), cancellable = true)
    private void forceDisableVsync(boolean vsync, CallbackInfo ci) {
        // Force disable VSync for maximum FPS
        this.vsync = false;
        ci.cancel();
    }
    
    @Inject(method = "swapBuffers", at = @At("HEAD"))
    private void onSwapBuffers(CallbackInfo ci) {
        // Remove buffer swapping delay
        GLFW.glfwSwapInterval(0); // Disable VSync completely
    }
}
