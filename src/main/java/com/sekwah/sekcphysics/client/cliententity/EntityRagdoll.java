package com.sekwah.sekcphysics.client.cliententity;

import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class EntityRagdoll extends EntityLiving {

    public BaseRagdoll ragdoll;

    public int ragdollLife = 600;

    protected EntityRagdoll(EntityType<?> entityType, World world) {
        super(entityType, world);
    }
}
