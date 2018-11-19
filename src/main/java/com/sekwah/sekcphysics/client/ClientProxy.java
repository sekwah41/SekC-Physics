package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.client.cliententity.render.RenderRagdoll;
import com.sekwah.sekcphysics.client.cliententity.render.renderfactory.RenderFac;
import com.sekwah.sekcphysics.commands.CommandReloadRagdolls;
import com.sekwah.sekcphysics.generic.CommonProxy;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollGenerator;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * Created by sekwah on 03/10/2015.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public void init() {
        super.init();
        MinecraftForge.EVENT_BUS.register(new EventHook());

        ClientCommandHandler.instance.registerCommand(new CommandReloadRagdolls());
    }

    @Override
    public void postInit() {
        super.init();
        new RagdollGenerator().loadRagdolls();
        //VanillaRagdolls.register();
    }

    @Override
    public void preInit() {
        super.preInit();
        RenderingRegistry.registerEntityRenderingHandler(EntityRagdoll.class, new RenderFac(RenderRagdoll.class));
    }

}
