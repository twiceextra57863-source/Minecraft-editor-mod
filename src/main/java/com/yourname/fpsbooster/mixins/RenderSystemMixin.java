package com.yourname.fpsbooster.mixins;

import net.minecraft.client.render.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {
    
    @ModifyArg(method = "limitDisplayFPS", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;sleep(J)V"), index = 0)
    private static long removeFPSCap(long sleepTime) {
        // Remove FPS cap completely
        return 0;
    }
}
