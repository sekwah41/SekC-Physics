package com.sekwah.sekcphysics;

import com.sekwah.sekcphysics.client.cliententity.RagdollEntity;
import com.sekwah.sekcphysics.client.render.RenderRagdoll;
import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollGenerator;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SekCPhysics implements ClientModInitializer {

    public static final Logger logger = LogManager.getLogger("SekC Physics");

    public static final boolean isDeObf = Entity.class.getName().equals("net.minecraft.entity.Entity");

    public static EntityType<RagdollEntity> RAGDOLL;

    public static FabricEntityTypeBuilder<RagdollEntity> RAGDOLL_TYPE_BUILDER = FabricEntityTypeBuilder.create(EntityCategory.MISC);

    public static Ragdolls ragdolls = new Ragdolls();

    @Override
    public void onInitializeClient() {

        RAGDOLL_TYPE_BUILDER.size(new EntitySize(0.15f, 0.15f, true));

        RAGDOLL = RAGDOLL_TYPE_BUILDER.build();

        RAGDOLL.getDefaultSize();

        logger.info("Generating ragdolls from JSON");

        new RagdollGenerator().loadRagdolls();

        // Trackers
        //logger.info("Registering ragdoll entity");
        //Registry.register(Registry.ENTITY_TYPE, "sekcphysics:ragdoll", RAGDOLL);

        //EntityTrackingRegistry.INSTANCE.register(RAGDOLL, 64, 1, false);

        // Renderers
        EntityRendererRegistry.INSTANCE.register(RagdollEntity.class, (dispatcher, context) -> new RenderRagdoll(dispatcher, new BipedEntityModel()));
    }
}
