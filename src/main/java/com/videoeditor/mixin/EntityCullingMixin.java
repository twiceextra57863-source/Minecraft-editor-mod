package com.videoeditor.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityCullingMixin<T extends Entity> {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        // Agar entity camera ke peeche hai ya bahut door hai, toh render skip karo
        if (entity != client.player && !client.worldRenderer.getEntitiesDebugString().contains(entity.toString())) {
            if (!entity.shouldRender(client.gameRenderer.getCamera().getPos().x, 
                                   client.gameRenderer.getCamera().getPos().y, 
                                   client.gameRenderer.getCamera().getPos().z)) {
                ci.cancel(); 
            }
        }
    }
}
