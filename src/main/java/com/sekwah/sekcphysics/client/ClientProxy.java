package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.cliententity.renderfactory.RenderFac;
import com.sekwah.sekcphysics.generic.CommonProxy;
import com.sekwah.sekcphysics.ragdoll.RagdollGenerator;
import com.sekwah.sekcphysics.ragdoll.vanilla.VanillaRagdolls;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by sekwah on 03/10/2015.
 */
public class ClientProxy extends CommonProxy {

    public boolean isClient(){
        return true;
    }

    public void addEvents() {
        MinecraftForge.EVENT_BUS.register(new EventHook());
    }

    public void generateRagdolls() {
        RagdollGenerator.loadRagdolls();
        VanillaRagdolls.register();

    }

    public void ragdollRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(EntityRagdoll.class, new RenderFac());
    }

}
