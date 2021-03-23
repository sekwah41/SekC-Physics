package com.sekwah.sekcphysics.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class RagdollConfig {

    public static final String CATEGORY_CLIENT = "client";

    public static ForgeConfigSpec RAGDOLL_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Integer> CONFIG_MAX_RAGDOLLS;
    public static int MAX_RAGDOLLS;

    public static ForgeConfigSpec.ConfigValue<Integer> CONFIG_RAGDOLL_LIFE;
    public static int RAGDOLL_LIFE;

    static {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("Server side variable allowing transparent skins to be set.").push(CATEGORY_CLIENT);

        CONFIG_MAX_RAGDOLLS = CLIENT_BUILDER.comment("How many should be the max number able to exist at once? -1 = unlimited")
                .define("maxRagdolls", -1);

        CONFIG_RAGDOLL_LIFE = CLIENT_BUILDER.comment("How many")
                .define("ragdollLife", 30);

        CLIENT_BUILDER.pop();

        RAGDOLL_CONFIG = CLIENT_BUILDER.build();
    }

    public static void loadVariables() {
        MAX_RAGDOLLS = CONFIG_MAX_RAGDOLLS.get();
        RAGDOLL_LIFE = CONFIG_MAX_RAGDOLLS.get();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        loadVariables();
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
        loadVariables();
    }
}
