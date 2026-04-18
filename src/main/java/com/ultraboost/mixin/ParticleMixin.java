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
     * Reduce heavy particles (crit, explosion, etc.)
     */
    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V",
            at = @At("HEAD"),
            cancellable = true)
    private void onAddParticle(Particle particle, CallbackInfo ci) {

        try {
            // 🔥 Random reduction (balanced, not full remove)
            if (Math.random() > 0.4) { // 60% particles removed
                ci.cancel();
                return;
            }

            // ⚡ Extra protection: very short-lived particles skip
            if (particle.getMaxAge() < 5) {
                ci.cancel();
            }

        } catch (Throwable ignored) {
            // Safety: kabhi crash nahi hone dena
        }
    }
}
