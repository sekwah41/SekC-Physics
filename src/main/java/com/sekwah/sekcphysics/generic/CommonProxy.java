package com.sekwah.sekcphysics.generic;

import com.sekwah.sekcphysics.handler.EntityHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by sekwah on 03/10/2015.
 */
public class CommonProxy {

    public boolean isClient() {
        return false;
    }

    public void init() {
        MinecraftForge.EVENT_BUS.register(new EventHook());

        EntityHandler.instance.registerEntities();
    }

    public void postInit() {
    }

    public void preInit() {
    }
}
