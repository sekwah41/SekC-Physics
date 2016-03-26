package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.SekCPhysics;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.io.*;
import java.util.List;

/**
 * Created by Alastair on 24/01/2016.
 */
public class RagdollGenerator {

    public static void generateRagdollsFrom(String modid) {
        // TODO check for the mod id and if not found then report false. If found generate ragdolls.
        try{
            Reader fileIn = new InputStreamReader(SekCPhysics.class.getResourceAsStream("/assets/sekcphysics/mod/" + modid + ".json"));

        }
        catch(NullPointerException e){
            SekCPhysics.logger.info("No ragdoll data found for: " + modid);
        }
    }

    /**
     * Finds the data for the mobs in the json data and generates it on startup.
     * TODO Try to find how to make a progress bar on the loading or make one appear like the chickenbones core
     */
    public static void loadRagdolls() {
        generateRagdollsFrom("vanilla");
        List<ModContainer> modlist = Loader.instance().getActiveModList();
        for(ModContainer mod : modlist){
            generateRagdollsFrom(mod.getModId());
        }
    }
}
