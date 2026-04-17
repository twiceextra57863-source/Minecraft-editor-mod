package com.ultraboost.mixin;

import net.minecraft.client.particle.ParticleManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleMixin {

    @Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
    private void onParticle(CallbackInfo ci) {

        if (Math.random() > 0.4) { // 60% particles remove
            ci.cancel();
        }
    }
}
