package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.generic.CommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by sekwah on 03/10/2015.
 */
public class ClientProxy extends CommonProxy {

    public boolean isClient(){
        return true;
    }

    public void addEvents() {
        MinecraftForge.EVENT_BUS.register(new EventHook());
        FMLCommonHandler.instance().bus().register(new EventHook());
    }

}
