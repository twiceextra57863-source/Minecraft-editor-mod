package com.ultraboost.mixin;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.Particle;

import com.ultraboost.system.SafeExecutor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleMixin {

    /**
     * 🔥 PvP + FPS Optimized Particle Handler (Crash Safe)
     */
    @Inject(
        method = "addParticle(Lnet/minecraft/client/particle/Particle;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onAddParticle(Particle particle, CallbackInfo ci) {

        SafeExecutor.run("ParticleMixin", () -> {

            // 🔥 Reduce heavy particles (balanced)
            if (Math.random() > 0.35) { // ~65% remove
                ci.cancel();
                return;
            }

            // ⚡ Remove useless short particles
            if (particle.getMaxAge() < 5) {
                ci.cancel();
            }
        });
    }
}
