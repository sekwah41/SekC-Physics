package com.sekwah.sekcphysics;

import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimdev.rift.listener.MinecraftStartListener;

// To see available listeners https://github.com/DimensionalDevelopment/Rift/tree/master/src/main/java/org/dimdev/rift/listener
@SuppressWarnings("unused")
public class SekCPhysics implements MinecraftStartListener {

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
}
