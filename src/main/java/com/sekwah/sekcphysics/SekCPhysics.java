package com.sekwah.sekcphysics;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.cliententity.render.RenderRagdoll;
import com.sekwah.sekcphysics.generic.CommonProxy;
import com.sekwah.sekcphysics.network.UsageReport;
import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import com.sekwah.sekcphysics.ragdoll.vanilla.VanillaRagdolls;
import com.sekwah.sekcphysics.settings.ModSettings;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by sekwah on 31/07/2015.
 * Atm the mod will just be client side which will stop the server which will probably be quite slow doing all the calculations
 *  also the players have complete controll over them, after all at least the ragdolls are supposed to only be for visual
 *   effects :3
 *
 *   Dont need to use proxy but do just in case you start adding server side too stuff for non visual things.
 *   Also can add something to add warnings to the server console(itll also stop crashes on a server if someone doesnt pay
 *   attention)
 *
 *   Also potentially add physics to the cloak, even if its as a giant square, but maybe split to small blocks and do it like
 *   real cloth
 */
@Mod(modid = SekCPhysics.modid, name = "SekC Physics", version = SekCPhysics.version)
public class SekCPhysics {

    public static final String modid = "sekcphysics";
    public static final Logger LOGGER = LogManager.getLogger("SekC Physics");

    public static final String version = "0.0.1";

    public static UsageReport usageReport;

    public static Ragdolls ragdolls = new Ragdolls();

    @SidedProxy(clientSide = "com.sekwah.sekcphysics.client.ClientProxy", serverSide = "com.sekwah.sekcphysics.generic.CommonProxy")
    public static CommonProxy proxy;



    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if(FMLCommonHandler.instance().getSide().isServer()){
            LOGGER.error("The mod so far contains only visual features, there is no point having it installed on anything other " +
                    "than a client for now.");
        }

        if(proxy.isClient()){
            usageReport = new UsageReport(true);
            usageReport.startUsageReport();
        }

        proxy.addEvents();


        EntityRegistry.registerModEntity(EntityRagdoll.class, "Ragdoll", 1, this, 64, 1, true);

        VanillaRagdolls.register();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        RenderingRegistry.registerEntityRenderingHandler(EntityRagdoll.class, new RenderRagdoll());

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        ModSettings.preInit(event);

    }



}
