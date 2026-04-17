package com.videoeditor.mixin.core; // Yeh line check karein!

import net.minecraft.client.render.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BufferBuilder.class)
public class BufferBuilderMixin {
    @Inject(method = "ensureCapacity", at = @At("HEAD"), cancellable = true)
    private void onEnsureCapacity(int estimate, CallbackInfo ci) {
        // Vulkan-style direct allocation logic yahan rahegi
    }
}
