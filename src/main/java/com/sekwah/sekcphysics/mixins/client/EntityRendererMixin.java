package com.sekwah.sekcphysics.mixins.client;

import com.sekwah.sekcphysics.accessors.EntityTextureAccessor;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin implements EntityTextureAccessor {

    @Shadow
    protected abstract Identifier getTexture(Entity var1);

    @Override
    public Identifier getBaseTexture(Entity entity) {
        return this.getTexture(entity);
    }
}
