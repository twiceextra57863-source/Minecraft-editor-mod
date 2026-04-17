package com.ultraboost.mixin;

import net.minecraft.client.render.WorldRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class ChunkMixin {

    private static long lastUpdate = 0;

    @Inject(method = "scheduleBlockRenders", at = @At("HEAD"), cancellable = true)
    private void limitUpdates(CallbackInfo ci) {

        long now = System.currentTimeMillis();

        if (now - lastUpdate < 50) {
            ci.cancel(); // limit updates
        }

        lastUpdate = now;
    }
}
