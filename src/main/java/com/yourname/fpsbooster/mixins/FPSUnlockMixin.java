package com.yourname.fpsbooster.mixins;

import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RenderTickCounter.class)
public class FPSUnlockMixin {
    
    @ModifyVariable(method = "beginRenderTick", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private long unlockFPS(long timeDelta) {
        // Reduce frame time for higher FPS
        // 2.5ms = 400 FPS
        return Math.min(timeDelta, 2500000L);
    }
}
