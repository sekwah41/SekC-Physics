package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.cliententity.render.RenderRagdoll;
import com.sekwah.sekcphysics.generic.CommonProxy;
import com.sekwah.sekcphysics.network.UsageReport;
import com.sekwah.sekcphysics.ragdoll.RagdollGenerator;
import com.sekwah.sekcphysics.ragdoll.vanilla.VanillaRagdolls;
import cpw.mods.fml.client.registry.RenderingRegistry;
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

    public void generateRagdolls() {
        RagdollGenerator.loadRagdolls();
        VanillaRagdolls.register();

        RenderingRegistry.registerEntityRenderingHandler(EntityRagdoll.class, new RenderRagdoll());
    }

}
