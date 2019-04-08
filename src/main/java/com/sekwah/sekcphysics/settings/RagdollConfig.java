package com.sekwah.sekcphysics.settings;

import com.sekwah.sekcphysics.SekCPhysics;
import net.minecraftforge.common.config.Config;

@Config(modid = SekCPhysics.MODID, name = SekCPhysics.MODID)
public class RagdollConfig {

    @Config.Comment("If there are more the older ragdolls will be deleted. If set lower than 0 there is no limit")
    @Config.RangeInt(min = -1)
    @Config.Name("Max Ragdolls")
    public static int maxRagdolls = -1;

    @Config.Comment("How many seconds ragdolls last. -1 is forever.")
    @Config.RangeInt(min = -1)
    @Config.Name("Ragdoll Life")
    public static int ragdollLife = 30;

}
