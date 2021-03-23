package com.sekwah.sekcphysics;

import com.sekwah.sekcphysics.config.RagdollConfig;
import net.minecraftforge.event.RegisterCommandsEvent;
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

@Mod(SekCPhysics.MODID)
public class SekCPhysics {

    public static final String MODID = "sekcphysics";

    public static final Logger LOGGER = LogManager.getLogger("SekC Physics");

    public SekCPhysics() {

        ModLoadingContext loadingContext = ModLoadingContext.get();
        loadingContext.registerConfig(ModConfig.Type.COMMON, RagdollConfig.RAGDOLL_CONFIG);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setup);

    }

    private void clientSetup(final FMLClientSetupEvent event) {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {

    }

}
