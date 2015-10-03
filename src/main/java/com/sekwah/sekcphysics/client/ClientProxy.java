package com.sekwah.sekcphysics.client;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by sekwah on 03/10/2015.
 */
public class ClientProxy {

    public boolean isClient(){
        return true;
    }

    public void addEvents() {
        MinecraftForge.EVENT_BUS.register(new EventHook());
        FMLCommonHandler.instance().bus().register(new EventHook());
    }

}
