package com.videoeditor.mixin;

import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraSmoothnessMixin {
    @Inject(method = "update", at = @At("RETURN"))
    private void postUpdate(CallbackInfo ci) {
        // Camera movement interpolation ko force karna
    }
}
