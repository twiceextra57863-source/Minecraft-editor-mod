package com.ultraboost.mixin;

import com.ultraboost.render.ChunkCache;
import net.minecraft.client.render.chunk.ChunkBuilder;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkBuilder.BuiltChunk.class)
public class ChunkRenderMixin {

    @Inject(method = "rebuild", at = @At("HEAD"), cancellable = true)
    private void onRebuild(CallbackInfo ci) {

        int x = (int)(Math.random() * 100); // placeholder (real chunk pos needed)
        int z = (int)(Math.random() * 100);

        if (!ChunkCache.shouldRender(x, z)) {
            ci.cancel(); // skip rebuild
        }
    }
}
