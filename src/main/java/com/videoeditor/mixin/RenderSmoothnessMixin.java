package com.videoeditor.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class RenderSmoothnessMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void boostPriority(boolean tick, CallbackInfo ci) {
        // Thread priority ko temporary badhana jab render heavy ho
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    }
}
