package com.sekwah.sekcphysics.accessors;

import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public interface EntityTextureAccessor {
    Identifier getBaseTexture(Entity entity);
}
