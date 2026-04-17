package com.ultraboost.mixin;

import com.ultraboost.render.RenderOptimizer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderMixin {

    public boolean shouldRender(Entity entity, double distance) {
        return RenderOptimizer.shouldRenderEntity(distance);
    }
}
