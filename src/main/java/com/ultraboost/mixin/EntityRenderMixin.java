package com.ultraboost.mixin;

import com.ultraboost.render.EntityOptimizer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(Entity entity, double x, double y, double z, float yaw, float tickDelta, CallbackInfo ci) {

        double distance = entity.squaredDistanceTo(entity.getCameraPosVec(tickDelta));

        if (!EntityOptimizer.shouldRender(distance)) {
            ci.cancel(); // skip rendering
        }
    }
}
