package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.cliententity.render.RenderRagdoll;
import com.sekwah.sekcphysics.cliententity.render.renderfactory.RenderFac;
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

    public boolean isClient() {
        return true;
    }

    public void init() {
        super.init();
        MinecraftForge.EVENT_BUS.register(new EventHook());


    }

    public void postInit() {
        super.init();
        RagdollGenerator.loadRagdolls();
        //VanillaRagdolls.register();
    }

    public void preInit() {
        super.preInit();
        RenderingRegistry.registerEntityRenderingHandler(EntityRagdoll.class, new RenderFac(RenderRagdoll.class));
    }

}
