package com.sekwah.sekcphysics.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RagdollConfig {

    public static final String CATEGORY_CLIENT = "client";

    public static ForgeConfigSpec RAGDOLL_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Integer> MAX_RAGDOLLS;

    public static ForgeConfigSpec.ConfigValue<Integer> RAGDOLL_LIFE;

    static {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("Server side variable allowing transparent skins to be set.").push(CATEGORY_CLIENT);

        MAX_RAGDOLLS = CLIENT_BUILDER.comment("How many should be the max number able to exist at once? -1 = unlimited")
                .define("maxRagdolls ", -1);

        RAGDOLL_LIFE = CLIENT_BUILDER.comment("How many")
                .define("ragdollLife ", 30);

        CLIENT_BUILDER.pop();

        RAGDOLL_CONFIG = CLIENT_BUILDER.build();
    }

    /*@SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        // add things when needed
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
        // add things when needed
    }*/
}
