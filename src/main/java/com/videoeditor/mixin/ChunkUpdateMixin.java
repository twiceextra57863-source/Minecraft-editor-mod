package com.videoeditor.mixin;

import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ChunkBuilder.class)
public class ChunkUpdateMixin {

    /**
     * @author Sahil_ZX
     * @reason Overwriting for high-speed mobile frame-pacing
     */
    @Overwrite
    public int getQueuedTaskCount() {
        // Task count ko artificially manage karna taaki 
        // CPU threads bottleneck na banein
        return 1; 
    }
}
