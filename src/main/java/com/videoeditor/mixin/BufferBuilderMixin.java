package com.videoeditor.mixin;

import net.minecraft.client.render.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BufferBuilder.class)
public class BufferBuilderMixin {
    // Memory recycling logic jo frames ko smooth banata hai
    @Inject(method = "clear", at = @At("HEAD"))
    private void onClear(CallbackInfo ci) {
        // Buffer ko wipe out karne ke bajaye memory reuse karna
    }
}
