package com.sekwah.sekcphysics.handler;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityHandler {

    public static final EntityHandler instance = new EntityHandler();

    public static ResourceLocation ragdollResLoc;


    public void registerEntities() {

        ragdollResLoc = new ResourceLocation(SekCPhysics.modid,"ragdoll");

        EntityRegistry.registerModEntity(ragdollResLoc, EntityRagdoll.class, "Ragdoll", 1, SekCPhysics.instance, 64, 1, true);

    }

}
