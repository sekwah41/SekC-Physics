package com.sekwah.sekcphysics;

import com.sekwah.sekcphysics.config.RagdollConfig;
import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import com.sekwah.sekcphysics.ragdoll.ragdolls.vanilla.VanillaRagdolls;
import com.sekwah.sekcphysics.util.Reflection;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Created by sekwah on 31/07/2015.
 * Atm the mod will just be client side which will stop the server which will probably be quite slow doing all the calculations
 *  also the players have complete controll over them, after all at least the ragdolls are supposed to only be for visual
 *   effects :3
 *
 *   Dont need to use proxy but do just in case you start adding server side too stuff for non visual things.
 *   Also can add something to add warnings to the server console(itll also stop crashes on a server if someone doesnt pay
 *   attention)
 *
 *   http://wiki.lwjgl.org/wiki/OpenCL_in_LWJGL.html
 *
 *   lwjgl has opencl built in, take a look for future reference :D
 *
 *   Also take a look at maybe making the ragdolls not based on entities and soley their own thing to stop strange interactions.
 *  (For now use entities)
 *
 *   Also potentially add physics to the cloak, even if its as a giant square, but maybe split to small blocks and do it like
 *   real cloth
 *
 *   Look at hooks https://github.com/elucent/Albedo/blob/master/src/main/java/elucent/albedo/asm/ASMTransformer.java
 */
@Mod(SekCPhysics.MODID)
public class SekCPhysics {

    public static final String MODID = "sekcphysics";

    public static final Logger LOGGER = LogManager.getLogger("SekC Physics");

    public static Reflection REFLECTION = new Reflection();

    public static Ragdolls RAGDOLLS = new Ragdolls();

    public SekCPhysics() {

        ModLoadingContext loadingContext = ModLoadingContext.get();
        loadingContext.registerConfig(ModConfig.Type.COMMON, RagdollConfig.RAGDOLL_CONFIG);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setup);

    }

    private void clientSetup(final FMLClientSetupEvent event) {

        VanillaRagdolls.register();

    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {

    }

}
