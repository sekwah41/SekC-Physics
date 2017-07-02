package com.sekwah.sekcphysics.ragdoll;

import com.google.gson.*;
import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollInvalidDataException;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollData;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ProgressManager;
import org.apache.logging.log4j.Level;

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
        try {
            Reader fileIn = new InputStreamReader(SekCPhysics.class.getResourceAsStream("/assets/sekcphysics/ragdolldata/" + modid + ".json"));
            Gson jsonFile = new Gson();
            JsonObject ragdollFileJson = jsonFile.fromJson(fileIn, JsonObject.class);
            Set<Map.Entry<String, JsonElement>> entityEnteries = ragdollFileJson.entrySet();
            ProgressManager.ProgressBar bar = ProgressManager.push("Constructing", entityEnteries.size());
            for(Map.Entry<String, JsonElement> entry : entityEnteries) {
                bar.step(entry.getKey());
                try {
                    RagdollData ragdollData = new RagdollData();
                    ragdollData = addRagdollSkeletonPointData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    ragdollData = addRagdollConstraintData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    ragdollData = addRagdollTrackerData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);

                    SekCPhysics.ragdolls.registerRagdoll(entry.getKey(), ragdollData);
                }
                catch(RagdollInvalidDataException | IllegalStateException | UnsupportedOperationException e) {
                    SekCPhysics.logger.error("Invalid data for: " + entry.getKey());
                    SekCPhysics.logger.error("Error message: " + e.getMessage());
                    SekCPhysics.logger.catching(Level.ERROR, e);
                }
            }
            ProgressManager.pop(bar);
            SekCPhysics.logger.info("Data loaded for: " + modid);
        }
        catch(JsonSyntaxException e) {
            SekCPhysics.logger.info("Error with data for: " + modid);
        }
        catch(JsonIOException | NullPointerException | UnsupportedOperationException e) {
            SekCPhysics.logger.info("No ragdoll data found for: " + modid);
        }
    }

    private static RagdollData addRagdollSkeletonPointData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                           JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        if(ragdollJsonData.has("inherit")) {
            JsonElement inherit = ragdollFileJson.get(ragdollJsonData.get("inherit").getAsString());
            if(inherit != null) {
                ragdollData = addRagdollSkeletonPointData(inherit.getAsJsonObject(), ragdollData,
                        ragdollFileJson);
            }
        }

        JsonObject skeletonPoints = ragdollJsonData.getAsJsonObject("skeletonPoints");
        if(skeletonPoints != null) {
            Set<Map.Entry<String, JsonElement>> pointNames = skeletonPoints.entrySet();
            for(Map.Entry<String, JsonElement> pointName : pointNames) {
                JsonArray pointPosArray = skeletonPoints.get(pointName.getKey()).getAsJsonArray();
                ragdollData.setSkeletonPoint(pointName.getKey(), pointPosArray.get(0).getAsDouble(),
                        pointPosArray.get(1).getAsDouble(), pointPosArray.get(2).getAsDouble());
            }
        }
        else{
            throw new RagdollInvalidDataException("No skeleton points");
        }

        return ragdollData;
    }

    private static RagdollData addRagdollConstraintData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                           JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        if(ragdollJsonData.has("inherit")) {
            JsonElement inherit = ragdollFileJson.get(ragdollJsonData.get("inherit").getAsString());
            if(inherit != null) {
                ragdollData = addRagdollConstraintData(inherit.getAsJsonObject(), ragdollData,
                        ragdollFileJson);
            }



        }

        JsonArray constraints = ragdollJsonData.getAsJsonArray("constraints");
        if(constraints != null) {
            for(JsonElement constraintEle : constraints) {
                JsonArray constraint = constraintEle.getAsJsonArray();
                ragdollData.addConstraint(constraint.get(0).getAsString(), constraint.get(1).getAsString());
            }
        }
        else{
            throw new RagdollInvalidDataException("No constraints");
        }

        return ragdollData;
    }

    private static RagdollData addRagdollTrackerData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                             JsonObject ragdollFileJson) throws UnsupportedOperationException {
        if(ragdollJsonData.has("inherit")) {
            JsonElement inherit = ragdollFileJson.get(ragdollJsonData.get("inherit").getAsString());
            if(inherit != null) {
                ragdollData = addRagdollTrackerData(inherit.getAsJsonObject(), ragdollData,
                        ragdollFileJson);
            }

        }
        return ragdollData;
    }

    public static void loadRagdolls() {
        SekCPhysics.logger.debug("Loading ragdolls and checking for supported mods");
        List<ModContainer> modlist = Loader.instance().getActiveModList();
        ProgressManager.ProgressBar bar = ProgressManager.push("SekCPhysics", modlist.size());
        for(ModContainer mod : modlist) {
            bar.step("Processing " + mod.getModId());
            generateRagdollsFrom(mod.getModId());
        }
        ProgressManager.pop(bar);
    }
}
