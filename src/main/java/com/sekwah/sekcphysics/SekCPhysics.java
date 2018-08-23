package com.sekwah.sekcphysics;

import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollGenerator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimdev.rift.listener.EntityTypeAdder;
import org.dimdev.rift.listener.MinecraftStartListener;
import org.dimdev.rift.listener.client.EntityRendererAdder;
import org.dimdev.riftloader.listener.InitializationListener;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

/**
 * To see available listeners https://github.com/DimensionalDevelopment/Rift/tree/master/src/main/java/org/dimdev/rift/listener
 * or
 * {@link org.dimdev.rift.listener}
  */
@SuppressWarnings("unused")
public class SekCPhysics implements MinecraftStartListener, EntityTypeAdder, EntityRendererAdder {

    public static final Logger logger = LogManager.getLogger("SekC Physics");

    /**
     * Make false for building (probably add into the gradle)
     */
    public static final boolean isDeObf = true;

    public static Ragdolls ragdolls = new Ragdolls();

    @Override
    public void onMinecraftStart() {
        logger.info("Generating ragdolls from JSON");

        new RagdollGenerator().loadRagdolls();
    }

    @Override
    public void registerEntityTypes() {
        EntityType.register("sekcphysics:ragdoll", EntityType.Builder.create())
    }

    @Override
    public void addEntityRenderers(Map<Class<? extends Entity>, Render<? extends Entity>> entityRenderMap, RenderManager renderManager) {
        
    }
}
