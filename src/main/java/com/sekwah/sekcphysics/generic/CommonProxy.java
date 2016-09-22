package com.sekwah.sekcphysics.generic;

import com.sekwah.sekcphysics.ragdoll.RagdollGenerator;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by sekwah on 03/10/2015.
 */
public class CommonProxy {

    public boolean isClient(){
        return false;
    }


    public void addEvents() {
        MinecraftForge.EVENT_BUS.register(new EventHook());
    }

    public void generateRagdolls() {

    }

    public void ragdollRenderer() {
    }

}
