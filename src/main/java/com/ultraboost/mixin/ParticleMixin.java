package com.ultraboost.mixin;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.Particle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleMixin {

    /**
     * 🔥 PvP Particle Optimization
     */
    @Inject(
        method = "addParticle(Lnet/minecraft/client/particle/Particle;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onAddParticle(Particle particle, CallbackInfo ci) {

        try {

            // ⚡ Remove most useless particles (balanced)
            if (Math.random() > 0.35) { // ~65% removed
                ci.cancel();
                return;
            }

            // 🔥 Remove very short-lived particles (lag creators)
            if (particle.getMaxAge() < 5) {
                ci.cancel();
                return;
            }

        } catch (Throwable ignored) {
            // Safety: never crash
        }
    }
}
