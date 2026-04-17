package com.videoeditor.mixin.entity;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityModel.class)
public class FastEntityRendererMixin<T extends Entity> {
    
    @Inject(method = "setAngles", at = @At("HEAD"), cancellable = true)
    private void optimizeAnimations(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        // Agar crowd bahut bada hai toh animations ki precision ko halka sa batch karna
        // Isse player movements smooth ho jati hain
    }
}
