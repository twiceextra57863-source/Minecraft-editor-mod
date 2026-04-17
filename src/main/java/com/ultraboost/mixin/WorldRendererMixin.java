package com.ultraboost.mixin;

import com.ultraboost.render.RenderOptimizer;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(CallbackInfo ci) {

        if (RenderOptimizer.shouldSkipFrame()) {
            ci.cancel(); // skip frame → FPS boost
        }
    }
}
