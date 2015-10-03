package com.sekwah.sekcphysics.ragdoll.vanilla;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.BipedRagdoll;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by sekawh on 8/7/2015.
 */
public class VanillaRagdolls {


    public static void add() {

    }

    public static void register() {
        SekCPhysics.ragdolls.registerRagdoll(EntityZombie.class, ZombieRagdoll.class);
        SekCPhysics.ragdolls.registerRagdoll(EntityPlayer.class, BipedRagdoll.class);
    }
}
