package com.sekwah.sekcphysics.ragdoll;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollData;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alastair on 24/01/2016.
 */
public class RagdollGenerator {

    public static void generateRagdollsFrom(String modid) {
        // TODO check for the mod id and if not found then report false. If found generate ragdolls.
        try{
            Reader fileIn = new InputStreamReader(SekCPhysics.class.getResourceAsStream("/assets/sekcphysics/ragdolldata/" + modid + ".json"));
            Gson jsonFile = new Gson();
            JsonObject ragdollFileJson = jsonFile.fromJson(fileIn, JsonObject.class);
            Set<Map.Entry<String, JsonElement>> entityEnteries = ragdollFileJson.entrySet();
            ProgressManager.ProgressBar bar = ProgressManager.push("Constructing", entityEnteries.size());
            for(Map.Entry<String, JsonElement> entry : entityEnteries){
                bar.step(entry.getKey());

                RagdollData ragdollData = new RagdollData();
                ragdollData = addRagdollSkeletonPointData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                ragdollData = addRagdollConstraintData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                ragdollData = addRagdollTrackerData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);

                SekCPhysics.ragdolls.registerRagdoll(entry.getKey(), ragdollData);
            }
            ProgressManager.pop(bar);
            SekCPhysics.logger.info("Data loaded for: " + modid);
        }
        catch(NullPointerException | UnsupportedOperationException e){
            SekCPhysics.logger.info("No ragdoll data found for: " + modid);
        }
    }

    private static RagdollData addRagdollSkeletonPointData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                           JsonObject ragdollFileJson) throws UnsupportedOperationException {
        if(ragdollJsonData.has("inherit")){
            String inherit = ragdollJsonData.get("inherit").getAsString();
            if(ragdollFileJson.has(inherit)){
                ragdollData = addRagdollSkeletonPointData(ragdollFileJson.get(inherit).getAsJsonObject(), ragdollData,
                        ragdollFileJson);
            }
            else{
                SekCPhysics.logger.debug("Inherit data not found");
            }
        }
        return ragdollData;
    }

    private static RagdollData addRagdollConstraintData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                           JsonObject ragdollFileJson) throws UnsupportedOperationException {
        if(ragdollJsonData.has("inherit")){
            String inherit = ragdollJsonData.get("inherit").getAsString();
            if(ragdollFileJson.has(inherit)){
                ragdollData = addRagdollConstraintData(ragdollFileJson.get(inherit).getAsJsonObject(), ragdollData,
                        ragdollFileJson);
            }
            else{
                SekCPhysics.logger.debug("Inherit data not found");
            }
        }
        return ragdollData;
    }

    private static RagdollData addRagdollTrackerData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                             JsonObject ragdollFileJson) throws UnsupportedOperationException {
        if(ragdollJsonData.has("inherit")){
            String inherit = ragdollJsonData.get("inherit").getAsString();
            if(ragdollFileJson.has(inherit)){
                System.out.println(inherit);
                ragdollData = addRagdollTrackerData(ragdollFileJson.get(inherit).getAsJsonObject(), ragdollData,
                        ragdollFileJson);
            }
            else{
                SekCPhysics.logger.debug("Inherit data not found");
            }
        }
        return ragdollData;
    }

    public static void loadRagdolls() {
        SekCPhysics.logger.debug("Loading ragdolls and checking for supported mods");
        List<ModContainer> modlist = Loader.instance().getActiveModList();
        ProgressManager.ProgressBar bar = ProgressManager.push("SekCPhysics", modlist.size());
        for(ModContainer mod : modlist){
            bar.step("Processing " + mod.getModId());
            generateRagdollsFrom(mod.getModId());
        }
        ProgressManager.pop(bar);
    }
}
