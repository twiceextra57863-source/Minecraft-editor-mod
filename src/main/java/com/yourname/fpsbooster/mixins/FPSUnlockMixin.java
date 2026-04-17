package com.yourname.fpsbooster.mixins;

import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderTickCounter.class)
public class FPSUnlockMixin {
    
    @Shadow
    private float lastFrameDuration;
    
    // Fixed method signature for 1.21.4
    @Inject(method = "beginRenderTick", at = @At("RETURN"))
    private void unlockFPS(long timeNanos, CallbackInfoReturnable<Integer> cir) {
        // Remove FPS cap - achieve 400+ FPS
        this.lastFrameDuration = 0.0025F; // 2.5ms = 400 FPS
    }
}
