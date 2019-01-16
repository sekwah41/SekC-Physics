package com.sekwah.sekcphysics;

import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.client.render.RenderRagdoll;
import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollGenerator;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.fabricmc.fabric.entity.EntityTrackingRegistry;
import net.fabricmc.fabric.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SekCPhysics implements ClientModInitializer {

    public static final Logger logger = LogManager.getLogger("SekC Physics");

    public static final boolean isDeObf = Entity.class.getName().equals("net.minecraft.entity.Entity");

    public static EntityType<EntityRagdoll> RAGDOLL;

    public static FabricEntityTypeBuilder<EntityRagdoll> RAGDOLL_TYPE_BUILDER = FabricEntityTypeBuilder.create(EntityRagdoll.class, EntityRagdoll::new);

    public static Ragdolls ragdolls = new Ragdolls();

    @Override
    public void onInitializeClient() {
        logger.info("Generating ragdolls from JSON");

        new RagdollGenerator().loadRagdolls();

        // Trackers
        logger.info("Registering ragdoll entity");
        EntityTrackingRegistry.INSTANCE.register(
                RAGDOLL_TYPE_BUILDER.build("sekcphysics:ragdoll"), 64, 1, false);

        // Renderers
        EntityRendererRegistry.INSTANCE.register(EntityRagdoll.class, new RenderRagdoll.Factory());
    }
}
