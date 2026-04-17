package com.videoeditor.mixin.entity;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Entity.class)
public abstract class EntityActionSmoothnessMixin {
    
    /**
     * @author Sahil_ZX
     * @reason Player actions aur movements ko interpolated frames mein convert karna
     */
    @Overwrite
    public boolean isLogicalSideForUpdatingMovement() {
        // Client side par movement update ko priority dena 
        // taaki crowd mein players "laggy" na dikhein
        return true; 
    }
}
